package com.fordevs.config;

import com.fordevs.postgresql.entity.InputStudent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;


/*@Service*/
@Component
public class ProducerService {

    KafkaConfig kafkaConfig = new KafkaConfig();


    KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaConfig.producerConfigs().getConfigurationProperties());

    /*@Autowired
    public ProducerService(KafkaTemplate<InputStudent, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }*/
    /*String topic = "student_topic";*/
    //Producer producer = new KafkaProducer<>(props);

    public void sendMessage(String topic, String message) {
        producer.send(new ProducerRecord<>(topic, message));
        producer.close();
    }
}
