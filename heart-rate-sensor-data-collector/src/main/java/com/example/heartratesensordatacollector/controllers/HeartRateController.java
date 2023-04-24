package com.example.heartratesensordatacollector.controllers;

import com.example.heartratesensordatacollector.producers.HeartRateProducer;
import org.example.core.models.HeartRate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sensor/hr")
public class HeartRateController {

    private HeartRateProducer heartRateProducer;

    public HeartRateController(HeartRateProducer heartRateProducer) {
        this.heartRateProducer = heartRateProducer;
    }

    @PostMapping
    public ResponseEntity<String> sendHeartRate(@RequestBody HeartRate heartRate){
        heartRateProducer.sendMessage(heartRate);
        return ResponseEntity.ok("HeartRate sent to RabbitMQ ...");
    }
}