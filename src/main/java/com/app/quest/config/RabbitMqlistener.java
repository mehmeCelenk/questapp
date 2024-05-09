package com.app.quest.config;

import com.app.quest.event.UserCreateEvent;
import com.app.quest.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqlistener {
    private final EmailService emailService;

    public RabbitMqlistener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "firstStepQueue")
    public void receiveMessage(UserCreateEvent userCreateEvent) {
        String userEmail = userCreateEvent.getEmail();
        String subject = "QuestApp  Kaydınız Başarılı";
        String text = "Merhaba, kaydınız başarıyla tamamlandı.";
        emailService.sendEmail(userEmail, subject, text);
    }
}
