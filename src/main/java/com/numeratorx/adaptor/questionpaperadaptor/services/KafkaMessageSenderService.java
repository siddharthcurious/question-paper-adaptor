package com.numeratorx.adaptor.questionpaperadaptor.services;

import com.numeratorx.adaptor.questionpaperadaptor.models.QuestionsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageSenderService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topicName, QuestionsSet msg) {
        kafkaTemplate.send(topicName, msg);
    }
}
