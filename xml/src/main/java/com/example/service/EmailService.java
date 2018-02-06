package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Environment env;

	@Async
	public void sendMail(String email) throws MailException, InterruptedException {

		SimpleMailMessage mail = new SimpleMailMessage();
		
		System.out.println(email);
		mail.setTo("nevena5695@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));
		
		String subject = "Novi rad za recenziju";
		mail.setSubject(subject);
		String text = "Dodeljen vam je novi rad za recenziju";
		mail.setText(text);
		
		javaMailSender.send(mail);
		System.out.println("email poslat");

	}
}
