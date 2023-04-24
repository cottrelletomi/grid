package com.example.notificationchannelmanager.controllers;

import com.example.notificationchannelmanager.repositories.UserRedisRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.core.models.User;
import org.example.core.models.UserRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    private RabbitTemplate rabbitTemplate;

    private KafkaConsumer<String, User> kafkaConsumer;

    @Value("${user.url}")
    private String USER_URL;

    private RestTemplate restTemplate;

    private UserRedisRepository userRedisRepository;

    private String bootstrapServers = "localhost:9092";

    @Value("${spring.kafka.group-id}")
    private String groupId;

    private Map<String, Object> properties;

    public NotificationController(RabbitTemplate rabbitTemplate, KafkaConsumer<String, User> kafkaConsumer, RestTemplateBuilder restTemplateBuilder, UserRedisRepository userRedisRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.kafkaConsumer = kafkaConsumer;
        this.kafkaConsumer.subscribe(Collections.singletonList("notification.topic"));
        this.restTemplate = restTemplateBuilder.build();
        this.userRedisRepository = userRedisRepository;

        this.properties = new HashMap<>();
        this.properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        this.properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        this.properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        this.properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        this.properties.put(JsonDeserializer.TRUSTED_PACKAGES, "org.example.core.models");
    }

//    @GetMapping("/{id}")
//    public User notification(@PathVariable("id") int id) {
//        LOGGER.info(String.format("Check notification -> notification_coach_%d", id));
//        User user = (User) rabbitTemplate.receiveAndConvert("notification_coach_" + id);
//        return user;
//    }

    @GetMapping("/{partition}")
    public ResponseEntity<User> getLastMessage(@PathVariable int partition) {
        ConsumerRecords<String, User> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));

        if (consumerRecords.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ConsumerRecord<String, User> lastRecord = consumerRecords.iterator().next();
        return ResponseEntity.ok(lastRecord.value());
    }

    /*@GetMapping("/{id}")
    public User notification(@PathVariable int id) {
        // Assigner la partition au consommateur Kafka (id = partition)
        KafkaConsumer<String, User> consumer = new KafkaConsumer<>(properties);
        consumer.assign(Collections.singletonList(new TopicPartition("notification.topic", id)));

        // Rechercher l'offset du dernier message dans la partition
        consumer.seekToEnd(Collections.singletonList(new TopicPartition("notification.topic", id)));
        long lastOffset = consumer.position(new TopicPartition("notification.topic", id));

        // Lire le dernier message de la partition
        consumer.seek(new TopicPartition("notification.topic", id), lastOffset - 1);
        ConsumerRecords<String, User> consumerRecords = consumer.poll(Duration.ofMillis(1000));

        if (consumerRecords.isEmpty()) {
            return null;
        }

        ConsumerRecord<String, User> lastRecord = consumerRecords.iterator().next();
        return lastRecord.value();
    }*/


    private User getUser(String email) {
        StringBuilder request = new StringBuilder(USER_URL).append("/search")
                .append("?email=").append(email);
        User user = restTemplate.getForObject(request.toString(), User.class);
        return user;
    }
    @GetMapping("/subscribe")
    public User subscribe(@RequestParam("email") String email) {
        LOGGER.info(String.format("Subscribe notification -> %s", email));
        System.out.println(email);
        User user = this.getUser(email);
        UserRedis u = new UserRedis(Integer.toString(user.getId()),
                user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getPassword(),
                user.getAge(), user.getWeight(),
                Character.toString(user.getGender()), user.getTraining_factor());
        userRedisRepository.save(u);

        System.out.println("Saved in redis >>>>>>> " + u);
        UserRedis res = userRedisRepository.findById(Integer.toString(user.getId())).get();
        System.out.println("Find in redis >>>>>>> " + res);

        return user;
    }
}