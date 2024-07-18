package com.compass.ecommnerce.events.listeners;

import com.compass.ecommnerce.entities.User;
import com.compass.ecommnerce.events.PasswordRecoveryEvent;
import com.compass.ecommnerce.events.exceptions.MailException;
import com.compass.ecommnerce.services.interfaces.IPasswordResetTokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class PasswordRecoveryEventListener implements ApplicationListener<PasswordRecoveryEvent> {
    private IPasswordResetTokenService passwordResetTokenService;
    private User user;
    private JavaMailSender mailSender;

    public PasswordRecoveryEventListener(IPasswordResetTokenService passwordResetTokenService, JavaMailSender mailSender) {
        this.passwordResetTokenService = passwordResetTokenService;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(PasswordRecoveryEvent event) {
        user = event.getUser();
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.saveResetPasswordToken(user, token);
        String url = "http://localhost:8080/password-reset?token=" + token;

        try {
            sendResetPasswordEmail(url);

        } catch (MessagingException | UnsupportedEncodingException exception){
            throw new MailException("Error sending recovery password mail, please try again later");
        }


    }

    public void sendResetPasswordEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Recupere sua senha";
        String senderName = "Ecommerce Project";
        String content = "<p> Olá "+ user.getEmail() + "</p>"
                + "<p><strong> Foi requisitado uma mudança de senha para a conta atrelada a este email no Ecommerce Project" +
                ".</strong></p>"
                +  "<p> Clique no link abaixo para recuperar sua senha</p>"
                + "<a href=\"" + url + "\">Recupere sua senha</a>"
                + "<p>&copy; Ecommerce Project</p>";
        emailMessage(subject, senderName, content, mailSender, user);
    }

    private static void emailMessage(String subject, String senderName, String mailContent, JavaMailSender mailSender,
                                     User user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("dev.email.test.java@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);



    }
}
