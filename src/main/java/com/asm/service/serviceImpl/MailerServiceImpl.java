package com.asm.service.serviceImpl;


import com.asm.dto.MailInfomation;
import com.asm.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailerServiceImpl implements MailerService {
    @Autowired
    JavaMailSender sender;
    List<MailInfomation> queue = new ArrayList<>();

    @Override
    public void send(MailInfomation mail) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), true);
        helper.setReplyTo(mail.getFrom());

        String[] cc = mail.getCc();
        if (cc != null && cc.length > 0) {
            helper.setCc(cc);
        }
        String[] bcc = mail.getBcc();
        if (bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }
        String[] attachments = mail.getAttachments();
        if (attachments != null && attachments.length > 0) {
            for (String path : attachments) {
                File file = new File(path);
                helper.addAttachment(file.getName(), file);
            }
        }
        sender.send(message);
    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
        this.send(new MailInfomation(to, subject, body));
    }

    @Override
    public void queue(MailInfomation mail) {
        queue.add(mail);
    }

    @Override
    public void queue(String to, String subject, String body) {
        queue(new MailInfomation(to, subject, body));
    }

    @Scheduled(fixedDelay = 5000)
    public void run() {
        while (!queue.isEmpty()) {
            MailInfomation mail = queue.remove(0);
            try {
                send(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}