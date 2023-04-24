package com.example.heartratesensorworker.listener;

import org.example.core.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class EmergencyListener {
    @Value("${emergency.topic.name}")
    private String topicName;
    KafkaTemplate<String, User> kafkaTemplate;
    private CountDownLatch heartRateLatch;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmergencyListener.class);

    public EmergencyListener(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.heartRateLatch = new CountDownLatch(1);
    }

    @KafkaListener(topics = "${user.topic.name}", containerFactory = "kafkaListenerContainerFactory")
    public void listener(User user) {
        System.out.println("test: " + user);
        //LOGGER.info(String.format("WARNING HR TOO HIGH ! Notification sent"));
        checkEmergency(user);
        this.heartRateLatch.countDown();
    }

    private void checkEmergency(User user) {
        int hr = user.getHeartRate();
        LOGGER.info(user.getFirstname() + ", " + user.getWeight() + "kg, " + user.getAge() + " y, " + hr + " bpm");
        if (user.getGender() == 'M') {
            if(user.getAge() < 26) {
                if(user.getWeight() < 60) {
                    if(hr > 180 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 177 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 174 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else {
                    if(hr > 172 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                }
            } else if(user.getAge() < 51) {
                if(user.getWeight() < 60) {
                    if(hr > 168 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 165 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 163 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else {
                    if(hr > 161 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                }
            } else if(user.getAge() < 76) {
                if(user.getWeight() < 60) {
                    if(hr > 159 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 156 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 153 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else {
                    if(hr > 150 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                }
            } else {
                if (user.getWeight() < 60) {
                    if (hr > 151 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if (user.getWeight() < 80) {
                    if (hr > 148 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if (user.getWeight() < 100) {
                    if (hr > 145 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else {
                    if (hr > 143 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                }
            }
        } else {
            if(user.getAge() < 26) {
                if(user.getWeight() < 60) {
                    if(hr > 174 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 172 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 169 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else {
                    if(hr > 167 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                }
            } else if(user.getAge() < 51) {
                if(user.getWeight() < 60) {
                    if(hr > 165 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 162 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 160 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else {
                    if(hr > 158 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                }
            } else if(user.getAge() < 76) {
                if(user.getWeight() < 60) {
                    if(hr > 155 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 80) {
                    if(hr > 152 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if(user.getWeight() < 100) {
                    if(hr > 150 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else {
                    if(hr > 147 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                }
            } else {
                if (user.getWeight() < 60) {
                    if (hr > 148 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if (user.getWeight() < 80) {
                    if (hr > 145 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else if (user.getWeight() < 100) {
                    if (hr > 142 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                } else {
                    if (hr > 140 || hr < 40) {
                        kafkaTemplate.send(topicName, user);
                    }
                }
            }
        }
    }
}
