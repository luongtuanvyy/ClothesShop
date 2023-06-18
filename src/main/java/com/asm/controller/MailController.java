package com.asm.controller;

import com.asm.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MailController {

    @Autowired
    MailerService mailerService;

    @GetMapping("mail/send")
    public String SendMail() {
        return "admin/sendmail";
    }

    @PostMapping("mail/send")
    public String SendMail(Model model, @ModelAttribute("to") String to,@ModelAttribute("subject") String subject,@ModelAttribute("body") String body) throws MessagingException {
        mailerService.send(to , subject, body);
        model.addAttribute("message","Thành công rồi");
        return "admin/mail";
    }
}
