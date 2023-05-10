package com.example.emergencynotificationagent.listener;

import com.example.emergencynotificationagent.repositories.UserRedisRepository;
import org.example.core.models.User;
import org.example.core.models.UserRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class CoachAvailListener {

    @Value("${notification.topic.name}")
    private String topicName;
    KafkaTemplate<String, User> kafkaTemplate;
    private CountDownLatch heartRateLatch;

    private UserRedisRepository userRedisRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CoachAvailListener.class);

    public CoachAvailListener(KafkaTemplate<String, User> kafkaTemplate, UserRedisRepository userRedisRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.userRedisRepository = userRedisRepository;
        this.heartRateLatch = new CountDownLatch(1);
    }

    @KafkaListener(topics = "${emergency.topic.name}", containerFactory = "kafkaListenerContainerFactory")
    public void listener(User user) {
        LOGGER.info(String.format("::CoachAvailListener:: Received message -> %s", user.toString()));
        coachMobileNotifying(user);
        this.heartRateLatch.countDown();
    }

    private void coachMobileNotifying(User user) {
        LOGGER.info(String.format("Coach mobile notifying"));
        List<UserRedis> coach = new ArrayList<>();

        Iterable<UserRedis> iterable = this.userRedisRepository.findAll();
        iterable.forEach(coach::add);

        if(!coach.isEmpty()) {
            LOGGER.info(String.format("%d coach(es) is(are) present in the gym", coach.size()));
            for(UserRedis c: coach) {
                System.out.println(c);
                kafkaTemplate.send(topicName, user);
                //kafkaTemplate.send(topicName, Integer.parseInt(c.getId()), c.getId(), user);
                //notificationProducer.sendMessage(user, "notification_coach_" + c.getId());
            }
        } else {
            LOGGER.info(String.format("No coach is present in the gym"));
        }
    }
}
