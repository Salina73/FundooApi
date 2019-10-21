package com.springBoot.rabbit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MessageListenerImpl implements MessageListener 
{
	@Autowired
	public JavaMailSender emailSender;

	@Override
	public void recieveSimpleMessage(List<String> msg) {
		
		System.out.println("Message received as: "+msg.get(0));
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("salinabodale73@gmail.com");
		message.setTo(msg.get(1));
		message.setSubject(msg.get(2));
		message.setText(msg.get(3));
		emailSender.send(message);
	}

}
