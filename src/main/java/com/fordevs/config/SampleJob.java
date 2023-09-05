package com.fordevs.config;

import com.fordevs.mysql.entity.OutputStudent;
import com.fordevs.postgresql.entity.InputStudent;
import com.fordevs.processor.ItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class SampleJob {
    @Autowired
    private ProducerService producerService;

    @Autowired
    private KafkaTemplate<Long, InputStudent> kafkaTemplate;



    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Autowired
    private ItemProcessor itemProcessor;


    @Autowired
    @Qualifier("datasource")
    private DataSource datasource;

    @Autowired
    @Qualifier("universitydatasource")
    private DataSource universitydatasource;

    @Autowired
    @Qualifier("postgresdatasource")
    private DataSource postgresdatasource;

    @Autowired
    @Qualifier("postgresqlEntityManagerFactory")
    private EntityManagerFactory postgresqlEntityManagerFactory;

    @Autowired
    @Qualifier("mysqlEntityManagerFactory")
    private EntityManagerFactory mysqlEntityManagerFactory;

    @Autowired
    private JpaTransactionManager jpaTransactionManager;

    @Autowired
    private KafkaProperties properties;
    @Bean
    ItemReader<? extends InputStudent> kafkaItemReader() {
        Properties props = new Properties();
        props.putAll(this.properties.buildConsumerProperties());
        return new KafkaItemReaderBuilder<Long, InputStudent>()
                .partitions(0).consumerProperties(props)
                .name("student_reader").saveState(true)
                .topic("student_topic").build();
    }

    @Bean
    KafkaItemWriter<Long, InputStudent> kafkaItemWriter() {
        return new KafkaItemWriterBuilder<Long, InputStudent>()
                .kafkaTemplate(kafkaTemplate)
                .itemKeyMapper(InputStudent::getId)
                .build();
    }

    @Bean
    public Job chunkJob() {
        try {
            return jobBuilderFactory.get("First Chunk Job")
                    .incrementer(new RunIdIncrementer())
                    .start(chunkStep())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Step chunkStep() throws Exception {
        return stepBuilderFactory.get("First Chunk Step")
                .<InputStudent, InputStudent>chunk(100)
                .reader(kafkaItemReader())
                .reader(jpaCursorPgsqlItemReader())
                //.processor(itemProcessor)
                .writer(kafkaItemWriter())
                //.faultTolerant()
                //.skip(Throwable.class)
                //.skip(NullPointerException.class)
                //.skipLimit(100)
                //.retryLimit(3)
                //.retry(Throwable.class)
                .transactionManager(jpaTransactionManager)
                .build();
    }

    public JpaCursorItemReader<InputStudent> jpaCursorPgsqlItemReader() {

        JpaCursorItemReader<InputStudent> jpaCursorItemReader =
                new JpaCursorItemReader<InputStudent>();

        jpaCursorItemReader.setEntityManagerFactory(postgresqlEntityManagerFactory);

        jpaCursorItemReader.setQueryString("From InputStudent");

        /*String kafkaTopic = "student_topic";
        String message = String.valueOf(jpaCursorItemReader);

        producerService.sendMessage(kafkaTopic, message);*/

        return jpaCursorItemReader;
    }

    public JpaItemWriter<OutputStudent> jpaItemWriter() {
        JpaItemWriter<OutputStudent> jpaItemWriter =
                new JpaItemWriter<OutputStudent>();

        jpaItemWriter.setEntityManagerFactory(mysqlEntityManagerFactory);

        return jpaItemWriter;
    }

    /*public KafkaItemWriter<String, OutputStudent> studentInfokafkaItemWriter() {
        KafkaItemWriter<String, OutputStudent> kafkaItemWriter = new KafkaItemWriter<>();
        kafkaItemWriter.setKafkaTemplate(studentKafkaTemplate);
        kafkaItemWriter.setItemKeyMapper(studentInfo -> String.valueOf(studentInfo.getId()));
        kafkaItemWriter.setDelete(Boolean.FALSE);
        try {
            kafkaItemWriter.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return kafkaItemWriter;
    }

    public KafkaItemReader kafkaItemReader() {
        KafkaItemReader kafkaItemReader = new KafkaItemReader();
        kafkaItemReader.setConsumerGroupId("student_group");
        kafkaItemReader.setTopic("student_topic");
        kafkaItemReader.setConsumerProperties(null);
        kafkaItemReader.setKeyMapper(new Converter() {
            @Override
            public Object convert(Object o) {
                return null;
            }
        });
        kafkaItemReader.setValueMapper(new Converter() {
            @Override
            public Object convert(Object o) {
                return null;
            }
        });
        return kafkaItemReader;
    }*/
}
