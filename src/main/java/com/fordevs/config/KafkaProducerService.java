package com.fordevs.config;

import com.fordevs.postgresql.entity.InputStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<InputStudent, String> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<InputStudent, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send("student_topic", message);
    }
}
