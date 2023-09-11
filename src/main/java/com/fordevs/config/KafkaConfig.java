package com.fordevs.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuración de Kafka para la producción de mensajes.
 *
 * @author Enoc.Velza | for-devs.com
 * @version 1.0
 */
@Configuration
public class KafkaConfig {

    /**
     * Dirección de los servidores de Kafka.
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Configura la fábrica del productor de Kafka.
     *
     * @return Una fábrica de productores de Kafka configurada.
     */
    @Bean
    public ProducerFactory<String, Object> producerConfigs() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // ... otras configuraciones

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Configura la plantilla de Kafka para la producción de mensajes.
     *
     * @return Una plantilla de Kafka configurada.
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerConfigs());
    }
}
