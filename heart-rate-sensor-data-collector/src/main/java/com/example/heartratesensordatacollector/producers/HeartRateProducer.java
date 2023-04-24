package com.example.heartratesensordatacollector.producers;

import org.example.core.models.HeartRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HeartRateProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${hr.topic.name}")
    private String topicName;

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartRateProducer.class);

    private RabbitTemplate rabbitTemplate;

    private KafkaTemplate<String, HeartRate> kafkaTemplate;

    public HeartRateProducer(KafkaTemplate<String, HeartRate> kafkaTemplate, RabbitTemplate rabbitTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(HeartRate heartRate) {
        LOGGER.info(String.format("HeartRate sent -> %s", heartRate.toString()));
        //rabbitTemplate.convertAndSend(exchange, routingKey, heartRate);
        kafkaTemplate.send(topicName, heartRate);
    }
}