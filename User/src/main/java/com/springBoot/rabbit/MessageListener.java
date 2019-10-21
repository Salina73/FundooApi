package com.springBoot.rabbit;

import java.util.List;

public interface MessageListener {

	public void recieveSimpleMessage(List<String> msg);

}