package com.example.heartratesensordatacollector.producers;

import org.example.core.models.HeartRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HeartRateProducer {

    @Value("${hr.topic.name}")
    private String topicName;

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartRateProducer.class);

    private KafkaTemplate<String, HeartRate> kafkaTemplate;

    public HeartRateProducer(KafkaTemplate<String, HeartRate> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(HeartRate heartRate) {
        LOGGER.info(String.format("HeartRate sent -> %s", heartRate.toString()));
        kafkaTemplate.send(topicName, heartRate);
    }
}