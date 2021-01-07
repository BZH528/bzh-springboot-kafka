package com.bzh.springbootkafka.service;

import com.bzh.springbootkafka.entity.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookConsumerService {

    @Value("${kafka.topic.my-topic}")
    private String mytopic;

    @Value("${kafka.topic.my-topic2}")
    private String mytopic2;

    private final Logger logger = LoggerFactory.getLogger(BookConsumerService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = {"${kafka.topic.my-topic}"}, groupId = "group1")
    public void consumeMessage(ConsumerRecord<String, String> bookConsumerRecord) {

        try {
            Book book = objectMapper.readValue(bookConsumerRecord.value(), Book.class);
            logger.info("消费者消费topic:{} partition:{}的消息 -> {}", bookConsumerRecord.topic(), bookConsumerRecord.partition(), book.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = {"${kafka.topic.my-topic2}"}, groupId = "group2" )
    public void consumeMessage2(Book book) {
        logger.info("消费者消费{}的消息 -> {}", mytopic2, book.toString());
    }
}
