package com.ecart.notificationservice.service;



import com.ecart.notificationservice.model.RegisterEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public static ObjectMapper objectMapper() {
        return objectMapper;
    }

    public void sendRegistrationEmail(RegisterEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject("Kayıt Başarılı - Hoşgeldiniz!");
        message.setText("Merhaba " + event.getFirstName() + " " + event.getLastName() +
                ",\n\nSisteme başarıyla kaydoldunuz.\n\nTeşekkürler.");

        mailSender.send(message);


    }
}
