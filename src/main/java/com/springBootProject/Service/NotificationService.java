package com.springBootProject.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class NotificationService {

    @Autowired
    private ReportService service;

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username")
    private String sender;
    @Value("${report.send.email.toList}")
    private String toEmails;

    public String sendDailyReport() throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(sender);
        helper.setTo(toEmails.split(","));
        helper.setSubject("List of orders_" + new Date().getTime());
        helper.setText("Hi User \n Please find the attachment for today's order recived");

        byte[] report = service.generateReport();

        ByteArrayResource  content = new ByteArrayResource(report);

       // FileSystemResource file = new FileSystemResource(new File());
        //helper.addAttachment(file.getFilename(), file);
        helper.addAttachment(new Date().getTime()+ "_orders.xlsx", content);
        javaMailSender.send(mimeMessage);

        return "Mail sent successfully with attaachement " ;


    }

}
