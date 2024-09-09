package com.springBootProject.Service;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;

@Service

public class JobService {

    @Autowired
    private NotificationService service;


    @Scheduled(cron = "${cron_interval}",zone = "IST")
    public void process(){
        System.out.println("Job started on"  + new Date());

        try {
            service.sendDailyReport();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
