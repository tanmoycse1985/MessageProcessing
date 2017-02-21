package com.jpmorgan;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jpmorgan.jms.MessageClient;

/**
 * Starting point of Message Processing Application
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 */
public class App {

	private static App app;

	public static void main(String[] args) {
		app = new App();
		app.launchApplication();
	}
	/**
	 * Launching the application.
	 */
	private void launchApplication() {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ActiveMQConnectionFactory activeMQConnectionFactory = (ActiveMQConnectionFactory) context
				.getBean("activeMQConnectionFactory");
		MessageClient client = (MessageClient) context.getBean("messageClient");
		try {
			client.startMessageApplication(activeMQConnectionFactory);
		} catch (JMSException | InterruptedException e) {
			System.out.println("Exception occured :: " + e);
			System.exit(0);
		}
	}

}
