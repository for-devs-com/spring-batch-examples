package com.fordevs.config;

public class Producer {

    private static ProducerService producerService;
    /*private KafkaTemplate<String, OutputStudent> kafkaTemplate;

        public void sendStudentData(OutputStudent student) {
            kafkaTemplate.send("student_topic", student);
        }
    */
    /*@Autowired
    ProducerService kafkaProducerService;*/

    public static void main(String[] args) {

        String message = "Hello, Testing Kafka!";
        String topic = "my-first-topic";

        producerService.sendMessage(topic, message);
    }

}
