package com.example.user.listener;

import com.example.user.services.UserService;
import org.example.core.models.HeartRate;
import org.example.core.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Service
public class UserListener {

    private UserService userService;
    @Value("${user.topic.name}")
    private String topicName;
    KafkaTemplate<String, User> kafkaTemplate;
    private CountDownLatch heartRateLatch;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserListener.class);

    public UserListener(UserService userService, KafkaTemplate<String, User> kafkaTemplate) {
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
        this.heartRateLatch = new CountDownLatch(1);
    }

    @KafkaListener(topics = "${hr.topic.name}", containerFactory = "kafkaListenerContainerFactory")
    public void listener(HeartRate heartRate) {
        System.out.println("Received heartRate message: " + heartRate);
        //LOGGER.info(String.format("WARNING HR TOO HIGH ! Notification sent"));
        Optional<User> user = userService.getUserByEmail(heartRate.getEmail());
        if(user.isPresent()) {
            user.get().setHeartRate(heartRate.getHeart_rate());
            kafkaTemplate.send(topicName, user.get());
        }
        this.heartRateLatch.countDown();
    }
}
