package ua.lpnu.denysoliinyk.cpuportal.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.lpnu.denysoliinyk.cpuportal.service.MailService;

@Log4j2
@Service
public class MailServiceImpl implements MailService {
    private final String username;
    private final String receiver;
    private final JavaMailSender javaMailSender;

    public MailServiceImpl(@Value("${spring.mail.username}") String username,
                           @Value("${spring.mail.receiver}") String receiver,
                           JavaMailSender javaMailSender) {
        this.username = username;
        this.receiver = receiver;
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendMail(String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(receiver);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        log.info("Exception mail message was sent");
        javaMailSender.send(mailMessage);
    }
}
