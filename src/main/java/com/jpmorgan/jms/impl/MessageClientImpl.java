package com.jpmorgan.jms.impl;

import java.util.concurrent.CountDownLatch;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.jpmorgan.jms.MessageClient;

/**
 * Implementation of @MessageClient
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 *
 */

public class MessageClientImpl implements MessageClient {

	@Value("${activeMQ.sales.queue}")
	String salesQueue;

	@Autowired
	private CountDownLatch countDownLatch;
	@Autowired
	private MessageListener messageListener;

	@Override
	public void startMessageApplication(ActiveMQConnectionFactory activeMQConnectionFactory)
			throws JMSException, InterruptedException {

		Connection connection = null;
		Session session = null;
		try {
			connection = activeMQConnectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(salesQueue);
			MessageConsumer consumer = session.createConsumer(queue);
			consumer.setMessageListener(messageListener);
			connection.start();
			System.out.println("Application has started and is waiting for messages...");
			countDownLatch.await(); // Wait for the message task to complete
		} finally {
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

}
