package com.fordevs.config;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class Consumer {
    static ProducerService producerService;
    public Consumer(Properties props) {

    }

    /*@KafkaListener(topics = "student_topic")
    public void consume(OutputStudent student) {
        // Lógica para insertar el estudiante en MongoDB
    }*/

    public static void main(String[] args) {
        Properties props = new Properties();


        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Collections.singleton("my-topic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            records.forEach(record -> {
                System.out.println("Received message: " + record.value());
            });
        }
    }

}
