package com.ecart.auth_service.producer;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecart.auth_service.dto.RegisterEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RegisterEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "register-events";

    public RegisterEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendRegisterEvent(RegisterEvent event) {
        try {
            String eventAsJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, eventAsJson);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
