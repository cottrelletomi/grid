package com.example.heartratesensordatacollector;

import com.example.heartratesensordatacollector.producers.HeartRateProducer;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HeartRateSensorSubscriber implements MqttCallback {
    @Value("${mqtt.broker}")
    private String brokerUrl;

    @Value("${mqtt.username}")
    private String brokerUsername;

    @Value("${mqtt.password}")
    private String brokerPassword;

    @Value("${mqtt.topic}")
    private String topic;

    private MqttClient mqttClient;

    @Autowired
    private HeartRateProducer heartRateProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartRateSensorSubscriber.class);

    public void subscribe() throws MqttException {
        if (mqttClient == null || !mqttClient.isConnected()) {
            mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId(), new MemoryPersistence());
            mqttClient.setCallback(this);
            mqttClient.connect(getMqttConnectOptions());
        }

        mqttClient.subscribe(this.topic);
    }

    private MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(brokerUsername);
        options.setPassword(brokerPassword.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        return options;
    }

    @Override
    public void connectionLost(Throwable cause) {
        // handle reconnection logic
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload = message.toString();
        LOGGER.info(String.format("::HeartRateSensorSubscriber:: Received message -> %s", payload));
        if (topic.equals(this.topic)) {
            heartRateProducer.sendMessage(payload);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // handle delivery confirmation logic
    }
}
