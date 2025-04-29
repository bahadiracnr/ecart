package com.ecart.notificationservice.consumer;



import com.ecart.notificationservice.model.RegisterEvent;
import com.ecart.notificationservice.service.MailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RegisterEventConsumer {

    private final MailService mailService;

    public RegisterEventConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    @KafkaListener(topics = "register-events", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, String> record) {
        try {

            String jsonData = record.value();


            RegisterEvent event = MailService.objectMapper().readValue(jsonData, RegisterEvent.class);


            mailService.sendRegistrationEmail(event);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
