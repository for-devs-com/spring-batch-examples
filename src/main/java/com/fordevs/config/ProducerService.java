package com.fordevs.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * ProducerService se encarga de
 * interactuar con Kafka para enviar mensajes.
 *
 * @author Enoc.Velza | for-devs.com
 * @version 1.0
 */
@Service
public class ProducerService {

    /**
     * Configuración de Kafka.
     */
    @Autowired
    private KafkaConfig kafkaConfig;

    /**
     * Productor de Kafka.
     */
    private KafkaProducer<String, String> producer;

    /**
     * Método de inicialización que se ejecuta después de la inyección de dependencias.
     */
    @PostConstruct
    public void init() {
        producer = new KafkaProducer<>(
                kafkaConfig.producerConfigs().
                        getConfigurationProperties());
    }

    /**
     * Envía un mensaje a un tópico específico de Kafka.
     *
     * @param topic   El tópico al que se enviará el mensaje.
     * @param message El mensaje que se enviará.
     */
    public void sendMessage(String topic, String message) {
        try {
            producer.send(new ProducerRecord<>(topic, message));
        } catch (Exception e) {
            // Manejo de excepciones
        }
    }

    /**
     * Método que se ejecuta antes de que el bean sea destruido.
     * Cierra el productor de Kafka.
     */
    @PreDestroy
    public void close() {
        if (producer != null) {
            producer.close();
        }
    }
}
