package com.indexcrm.integration;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailProvider {

    // ALTERAÇÃO: required = false permite que o sistema suba sem configuração de e-mail
    @Autowired(required = false) 
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:no-reply@indexcrm.com}")
    private String senderEmail;

    @Async
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        // Se não tiver configuração, só avisa no log e não quebra o sistema
        if (mailSender == null) {
            System.out.println("⚠️ AVISO: E-mail não enviado (SMTP não configurado).");
            System.out.println("Para: " + to + " | Assunto: " + subject);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
            System.out.println("✅ Email enviado com sucesso para: " + to);

        } catch (MessagingException e) {
            System.err.println("❌ Falha ao enviar email: " + e.getMessage());
        }
    }
}