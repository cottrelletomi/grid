package com.example.heartratesensordatacollector.producers;

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

    private KafkaTemplate<String, String> kafkaTemplate;

    public HeartRateProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String heartRate) {
        LOGGER.info(String.format("::HeartRateProducer:: HeartRate sent -> %s", heartRate));
        kafkaTemplate.send(topicName, heartRate);
    }
}