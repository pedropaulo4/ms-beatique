package br.com.beautique.api.services.impl;

import br.com.beautique.api.configurations.RabbitMqTopicConfig;
import br.com.beautique.api.services.BrokerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BrokerServiceImpl implements BrokerService {

    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    private final RabbitMqTopicConfig rabbitMqTopicConfig;

    public BrokerServiceImpl(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, RabbitMqTopicConfig rabbitMqTopicConfig) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMqTopicConfig = rabbitMqTopicConfig;
    }

    @Override
    public void send(String type, Object data) {
        String routingKey = type + ".#";

        try{
            String jsonData = objectMapper.writeValueAsString(data);
            rabbitTemplate.convertAndSend(rabbitMqTopicConfig.exchangeName, routingKey, jsonData, message ->{
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            });
        }catch (Exception e) {
            throw new RuntimeException("Error serializing message");
        }

    }
}
