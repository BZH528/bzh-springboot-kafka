package com.bzh.springbootkafka.controller;

import com.bzh.springbootkafka.entity.Book;
import com.bzh.springbootkafka.service.BookProducerService;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Value("${kafka.topic.my-topic}")
    String mytopic;

    @Value("${kafka.topic.my-topic2}")
    String mytopic2;

    @Autowired
    private BookProducerService producerService;

    private AtomicLong atomicLong = new AtomicLong();

    /*BookController(BookProducerService producerService) {
        this.producerService = producerService;
    }*/

    @PostMapping
    public void sendMessageToKafkaTopic(@RequestParam("name") String name) {
        producerService.sendMessage(mytopic, new Book(atomicLong.addAndGet(1), name));
        producerService.sendMessage(mytopic2, new Book(atomicLong.addAndGet(1), name));
    }

}
