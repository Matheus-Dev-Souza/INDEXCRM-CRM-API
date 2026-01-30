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

    @Autowired
    private JavaMailSender mailSender;

    // Pega o e-mail do remetente configurado no application.properties
    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * Envia e-mail formatado em HTML de forma assíncrona.
     * @param to E-mail do destinatário
     * @param subject Assunto
     * @param htmlBody Conteúdo em HTML
     */
    @Async
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            // O 'true' indica que é multipart (aceita anexo/html)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = ativa HTML

            mailSender.send(message);

            System.out.println("✅ Email enviado com sucesso para: " + to);

        } catch (MessagingException e) {
            System.err.println("❌ Falha ao enviar email: " + e.getMessage());
            // Em produção, você pode não querer parar o sistema por falha no e-mail,
            // mas é bom logar o erro.
        }
    }
}