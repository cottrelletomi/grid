package com.example.emergencynotificationagent.consumers;

import org.example.core.models.HeartRate;
import org.example.core.models.User;
import com.example.emergencynotificationagent.producers.NotificationProducer;
import com.example.emergencynotificationagent.repositories.UserRedisRepository;
import com.example.emergencynotificationagent.repositories.UserRepository;
import org.example.core.models.UserRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmergencyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmergencyConsumer.class);
    private NotificationProducer notificationProducer;
    private UserRepository userRepository;
    private UserRedisRepository userRedisRepository;

    public EmergencyConsumer(NotificationProducer notificationProducer, UserRepository userRepository, UserRedisRepository userRedisRepository) {
        this.notificationProducer = notificationProducer;
        this.userRepository = userRepository;
        this.userRedisRepository = userRedisRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.emergency.name}"})
    public void consumeMessage(HeartRate heartRate) {
        LOGGER.info(String.format("Received message -> %s", heartRate.toString()));
        coachMobileNotifying(heartRate);
    }

    private void coachMobileNotifying(HeartRate heartRate) {
        LOGGER.info(String.format("Coach mobile notifying"));
        List<UserRedis> coach = new ArrayList<>();

        Iterable<UserRedis> iterable = this.userRedisRepository.findAll();
        iterable.forEach(coach::add);

        if(!coach.isEmpty()) {
            LOGGER.info(String.format("%d coach(es) is(are) present in the gym", coach.size()));
            Optional<User> user = this.userRepository.findUserByEmail(heartRate.getEmail());
            if (user.isPresent()) {
                for(UserRedis c: coach) {
                    notificationProducer.sendMessage(user.get(), "notification_coach_" + c.getId());
                }
            } else {
                LOGGER.info(String.format("Unknown user"));
            }
        } else {
            LOGGER.info(String.format("No coach is present in the gym"));
        }
    }
}
