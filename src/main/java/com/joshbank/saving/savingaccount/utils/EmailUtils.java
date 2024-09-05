package com.joshbank.saving.savingaccount.utils;

import com.joshbank.saving.savingaccount.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmailUtils {

    private EmailService emailService;

    @Autowired
    public EmailUtils(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendUserMail(User user){
        String subject = "Welcome to Our Service!";
        String text = "Hello " + user.getUsername() +
                       ",\n\nThank you for registering with us."
                          +"You Bank Credentials are"+"\nUsername: "+user.getEmail()+"\nPassword: "+user.getPassword();
        emailService.sendEmail(user.getEmail(), subject, text);
    }
}
