package com.redhat.btison.enmasse.producer.jms.sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducerServiceImpl implements MessageProducerService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${producer.destination}")
    private String destination;

    @Override
    public void sendText(String msg) {
        jmsTemplate.convertAndSend(destination, msg);
    }

}
