package com.bzh.springbootkafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class BookProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookProducerService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, Object o) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, o);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.error("生产者发送消息：{}失败，原因：{}", o.toString(), throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> sendResult) {
                LOGGER.info("生产者成功发送消息到" + topic + "->" + sendResult.getProducerRecord().value().toString());
            }
        });
    }
}
