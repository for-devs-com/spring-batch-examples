package com.fordevs.config;

import com.fordevs.mysql.entity.OutputStudent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Properties;

public class Producer {

    private static KafkaProducerService kafkaProducerService;
    /*private KafkaTemplate<String, OutputStudent> kafkaTemplate;

        public void sendStudentData(OutputStudent student) {
            kafkaTemplate.send("student_topic", student);
        }
    */
    /*@Autowired
    KafkaProducerService kafkaProducerService;*/

    public static void main(String[] args) {
        Properties props = new Properties();


        String message = "Hello, Testing Kafka!";
        ProducerRecord record = new ProducerRecord<>("my-topic", message);

        kafkaProducerService.sendMessage(String.valueOf(record));
        /*kafkaProducerService.close();*/
    }
}
