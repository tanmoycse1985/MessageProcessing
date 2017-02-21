package com.jpmorgan;

import java.util.concurrent.CountDownLatch;

import javax.jms.MessageListener;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jpmorgan.jms.MessageClient;
import com.jpmorgan.jms.SalesMessageListener;
import com.jpmorgan.jms.impl.MessageClientImpl;
import com.jpmorgan.service.SalesMessageProcessingService;
import com.jpmorgan.service.SalesMessageRecordingService;
import com.jpmorgan.service.SalesMessageStorageService;
import com.jpmorgan.service.impl.SalesMessageProcessingServiceImpl;
import com.jpmorgan.service.impl.SalesMessageRecordingServiceImpl;
import com.jpmorgan.service.impl.SalesMessageStorageServiceImpl;
import com.jpmorgan.util.Constants;

/**
 * This class acts as an configuration class.
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = Constants.PACKAGE_BASE)
@PropertySource(Constants.PROPERTY_SOURCE)
public class AppConfig {

	@Value("${activeMQ.connection.address}")
	String brokerURL;
	@Value("${activeMQ.sales.queue}")
	String salesQueue;
	@Value("${activeMQ.broker.user}")
	String activeMQUsername;
	@Value("${activeMQ.broker.password}")
	String activeMQPassword;

	@Bean
	public CountDownLatch countDownLatch() {
		return new CountDownLatch(1);
	}

	@Bean
	public MessageClient messageClient() {
		return new MessageClientImpl();
	}

	@Bean
	public SalesMessageRecordingService salesMessageRecordingService() {
		return new SalesMessageRecordingServiceImpl();
	}

	@Bean
	public SalesMessageStorageService salesMessageStorageService() {
		return new SalesMessageStorageServiceImpl();
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(activeMQUsername,
				activeMQPassword, brokerURL);
		activeMQConnectionFactory.setUseAsyncSend(true);
		return activeMQConnectionFactory;
	}

	@Bean
	public MessageListener messageListener() {
		return new SalesMessageListener();
	}

	@Bean
	public SalesMessageProcessingService salesMessageProcessingService() {
		return new SalesMessageProcessingServiceImpl(countDownLatch(), salesMessageStorageService(),
				salesMessageRecordingService());
	}

}
