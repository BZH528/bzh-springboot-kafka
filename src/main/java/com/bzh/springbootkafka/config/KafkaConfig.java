package com.bzh.springbootkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

/**
 * @author bizhihao
 * @date 2021-01-07
 */
@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.my-topic}")
    String mytopic;

    @Value("${kafka.topic.my-topic2}")
    String mytopic2;

    /**
     * JSON消息转换器
     *
     * @return
     */
    @Bean
    public RecordMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }

    /**
     * 通过注入一个NewTopic类型的Bean来创建topic，如果topic已存在，则会忽略
     */
    @Bean
    public NewTopic myTopic() {
        return new NewTopic(mytopic, 2, (short)1);
    }

    @Bean
    public NewTopic myTopic2() {
        return new NewTopic(mytopic2, 1, (short) 1);
    }


}
