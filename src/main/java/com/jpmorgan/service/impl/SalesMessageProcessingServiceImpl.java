package com.jpmorgan.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Service;

import com.jpmorgan.domain.Sale;
import com.jpmorgan.service.SalesMessageProcessingService;
import com.jpmorgan.service.SalesMessageRecordingService;
import com.jpmorgan.service.SalesMessageStorageService;

/**
 * Implementation of @SalesMessageProcessingService class.
 */
@Service
public class SalesMessageProcessingServiceImpl implements SalesMessageProcessingService {

	private CountDownLatch countDownLatch;
	private SalesMessageStorageService salesMessageStorageService;
	private SalesMessageRecordingService salesMessageRecordingService;

	private List<Sale> reportSales = new ArrayList<>();
	private List<Sale> reportAdjustments = new ArrayList<>();

	public SalesMessageProcessingServiceImpl(CountDownLatch countDownLatch,
			SalesMessageStorageService salesMessageStorageService,
			SalesMessageRecordingService salesMessageRecordingService) {
		this.countDownLatch = countDownLatch;
		this.salesMessageStorageService = salesMessageStorageService;
		this.salesMessageRecordingService = salesMessageRecordingService;
	}

	@Override
	public Sale getSaleObjectFromXML(String msgText) throws JAXBException {
		return JAXB.unmarshal(new StringReader(msgText), Sale.class);
	}

	@Override
	public void processSaleRecord(Sale sale) {
		// *** All sales are getting recorded ****/
		String recordId = salesMessageStorageService.save(sale);
		System.out.println("Sale recorded - Record Id: " + recordId + ", Sale: " + sale);
		reportSales.add(sale);
		reportAdjustments.add(sale);
		int reportSalesSize = reportSales.size();
		int reportAdjustmentsSize = reportAdjustments.size();

		/**
		 * After every 10th message received, log a report detailing the number
		 * of sales of each product and their total value.
		 */
		if (reportSalesSize == 10) {
			salesMessageRecordingService.saveSalesDetails(reportSales);
			reportSales.clear();
		}

		/**
		 * After 50 messages, log that it is pausing, stop accepting new
		 * messages and log a report of the adjustments that have been made to
		 * each sale type while the application was running.
		 */
		if (reportAdjustmentsSize >= 50) {
			salesMessageRecordingService.saveAdjustmentsDetails(reportAdjustments);
			reportAdjustments.clear();

			if (countDownLatch != null) {
				System.out.println("Application is Stopping.......");
				countDownLatch.countDown();
			}
		}
	}

}
