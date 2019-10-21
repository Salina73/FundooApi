package com.springBoot.rabbit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqProducer 
{
	@Autowired
	private RabbitTemplate rabbitTemplate;


	public void sendSimpleMessage(String message, String emailId, String subject, String link) 
	{
		List<String> msg=new ArrayList<String>();
		msg.add(message);
		msg.add(emailId);
		msg.add(subject);
		msg.add(link);	
		rabbitTemplate.convertAndSend(RabbitProducerConfig.ROUTING_KEY, msg);
		System.out.println("Is listener returned ::: " + rabbitTemplate.isReturnListener());
	}

	
}
