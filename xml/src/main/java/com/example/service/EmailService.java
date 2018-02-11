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

	@Async
	public void sendMailGetReview(String email, String id, String idRevision)
			throws MailException, InterruptedException {

		SimpleMailMessage mail = new SimpleMailMessage();

		System.out.println(email);
		mail.setTo("nevena5695@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));

		String subject = "Pristigao Vam je novi rad za recenziranje";
		mail.setSubject(subject);
		String text = "Dodeljen Vam je novi rad za recenziranje";
		text += "\n Na sledecem linku mozete prihvatiti ili odbiti recenziranje: ";
		text += "\n http://localhost:4200/recenzent/naucniRadovi/" + id + "/revizije/" + idRevision;
		mail.setText(text);

		javaMailSender.send(mail);
		System.out.println("email poslat");

	}

	@Async
	public void sendMailThanksReviewer(String email) throws MailException, InterruptedException {

		SimpleMailMessage mail = new SimpleMailMessage();

		System.out.println(email);
		mail.setTo("nevena5695@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));

		String subject = "Zahvaljujemo Vam za recenziju";
		mail.setSubject(subject);

		javaMailSender.send(mail);
		System.out.println("email poslat");

	}

	@Async
	public void sendMailRejectRevision(String email, String name, String id, String status)
			throws MailException, InterruptedException {

		SimpleMailMessage mail = new SimpleMailMessage();

		System.out.println(email);
		mail.setTo("nevena5695@gmail.com");
		mail.setFrom(env.getProperty("spring.mail.username"));

		String subject = "Vas naucni rad je " + status.toLowerCase();
		mail.setSubject(subject);

		String text = "Naucni rad " + name + " je " + status.toLowerCase();
		text += "\n Na sledecem linku mozete pregledati recenzije: ";
		text += "\n http://localhost:4200/autor/naucniRadovi/" + id;
		mail.setText(text);

		javaMailSender.send(mail);
		System.out.println("email poslat");

	}
}
