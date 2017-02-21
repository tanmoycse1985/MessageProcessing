package com.jpmorgan.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;

import com.jpmorgan.domain.Sale;
import com.jpmorgan.service.SalesMessageProcessingService;
import com.jpmorgan.util.Util;

/**
 * Implementation of MessageListener
 *
 */
public class SalesMessageListener implements MessageListener {

	@Autowired
	private SalesMessageProcessingService salesMessageProcessingService;
	
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				String msgText = textMessage.getText();
				System.out.println("Message received: " + msgText);
				Util.validateSaleXML(msgText);
				Sale sale = salesMessageProcessingService.getSaleObjectFromXML(msgText);
				salesMessageProcessingService.processSaleRecord(sale);
			} catch (JMSException | JAXBException e) {
				System.out.println("Please provide correct input XML, Exception Caught : " + e);
				e.printStackTrace();
			}catch (Exception e) {
				System.out.println("Exception Caught : " + e);
				e.printStackTrace();
			}
		}

	}
}
