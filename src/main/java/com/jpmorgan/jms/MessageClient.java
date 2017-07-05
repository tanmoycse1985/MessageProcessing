package com.jpmorgan.jms;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

public interface MessageClient {
	
	/**
	 * Starting the  message application
	 * 
	 * @param activeMQConnectionFactory
	 * @throws JMSException
	 * @throws InterruptedException
	 * 
	 */
	public void startMessageApplication(ActiveMQConnectionFactory activeMQConnectionFactory)
			throws JMSException, InterruptedException;

}

