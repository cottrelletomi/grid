package com.example.emergencynotificationagent.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RabbitConfig {

    /*@Value("${rabbitmq.queue.notification.name}")
    private String notificationQueue;*/

    @Value("${rabbitmq.queue.emergency.name}")
    private String emergencyQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    /*@Value("${rabbitmq.routing.notification.key}")
    private String routingNotificationKey;*/

    @Value("${rabbitmq.routing.emergency.key}")
    private String routingEmergencyKey;

    /*@Bean
    public Queue notificationQueue(){
        return new Queue(notificationQueue);
    }*/

    @Bean
    public Queue emergencyQueue(){
        return new Queue(emergencyQueue);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    //----------------------------------------------------------------------
     /*@Bean
    public Declarables queues(@Value("${routing.queues}") List<String> queues) {
        return new Declarables(queues.stream()
                .map(queue -> new Queue(queue))
                .collect(Collectors.toList()));
    }*/

    @Value("${rabbitmq.queue.notificationCoach1.name}")
    private String notificationCoach1Queue;

    @Value("${rabbitmq.queue.notificationCoach2.name}")
    private String notificationCoach2Queue;

    @Value("${rabbitmq.queue.notificationCoach3.name}")
    private String notificationCoach3Queue;

    @Bean
    public Queue notificationCoach1Queue(){
        return new Queue(notificationCoach1Queue);
    }

    @Bean
    public Queue notificationCoach2Queue(){
        return new Queue(notificationCoach2Queue);
    }

    @Bean
    public Queue notificationCoach3Queue(){
        return new Queue(notificationCoach3Queue);
    }

    @Bean
    public Declarables bindings(List<Queue> queues) {
        return new Declarables(queues.stream()
                .map(queue -> BindingBuilder.bind(queue).to(exchange()).with(queue.getName()))
                .collect(Collectors.toList()));
    }
    //----------------------------------------------------------------------
    // binding between queue and exchange using routing key
    /*@Bean
    public Binding notificationBinding(){
        return BindingBuilder
                .bind(notificationQueue())
                .to(exchange())
                .with(routingNotificationKey);
    }*/

    @Bean
    public Binding emergencyBinding(){
        return BindingBuilder
                .bind(emergencyQueue())
                .to(exchange())
                .with(routingEmergencyKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}