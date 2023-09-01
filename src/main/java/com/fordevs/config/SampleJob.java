package com.fordevs.config;

import com.fordevs.mysql.entity.OutputStudent;
import com.fordevs.postgresql.entity.InputStudent;
import com.fordevs.processor.ItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class SampleJob {

    private  KafkaTemplate<String, OutputStudent> studentKafkaTemplate ;

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

    @Bean
    public Job chunkJob() {
        return jobBuilderFactory.get(	"First Chunk Job")
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .build();
    }
    private Step chunkStep() {
        return stepBuilderFactory.get("First Chunk Step")
                .<InputStudent, OutputStudent>chunk(100)
                .reader(jpaCursorItemReader())
                .processor(itemProcessor)
                .writer(jpaItemWriter())
                //.faultTolerant()
                //.skip(Throwable.class)
                //.skip(NullPointerException.class)
                //.skipLimit(100)
                //.retryLimit(3)
                //.retry(Throwable.class)
                .transactionManager(jpaTransactionManager)
                .build();
    }

    public JpaCursorItemReader<InputStudent> jpaCursorItemReader() {
        JpaCursorItemReader<InputStudent> jpaCursorItemReader =
                new JpaCursorItemReader<InputStudent>();

        jpaCursorItemReader.setEntityManagerFactory(postgresqlEntityManagerFactory);

        jpaCursorItemReader.setQueryString("From InputStudent");

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
