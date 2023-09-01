package com.fordevs.config;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fordevs.mysql.entity.OutputStudent;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Collections;
import java.util.Properties;

public class Consumer {
    public Consumer(Properties props) {

    }

    /*@KafkaListener(topics = "student_topic")
    public void consume(OutputStudent student) {
        // Lógica para insertar el estudiante en MongoDB
    }*/

    public static void main(String[] args) {
        Properties props = new Properties();


        Consumer consumer = new Consumer(props);
        consumer.subscribe(Collections.singleton("my-topic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            records.forEach(record -> {
                System.out.println("Received message: " + record.value());
            });
        }
    }

}
