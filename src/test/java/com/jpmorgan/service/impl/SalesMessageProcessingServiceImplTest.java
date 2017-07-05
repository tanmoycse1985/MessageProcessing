package com.jpmorgan.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

import com.jpmorgan.domain.AdjustmentOperationType;
import com.jpmorgan.domain.Sale;
import com.jpmorgan.service.SalesMessageProcessingService;

/**
 * Unit Test for @SalesMessageProcessingServiceImpl
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 * 
 */
public class SalesMessageProcessingServiceImplTest {
	
	SalesMessageStorageServiceImpl salesMessageStorageServiceImpl = mock(SalesMessageStorageServiceImpl.class);
	SalesMessageRecordingServiceImpl salesMessageRecordingServiceImpl = mock(SalesMessageRecordingServiceImpl.class);
	
	private final static CountDownLatch countDownLatch = new CountDownLatch(1);
	
	SalesMessageProcessingService salesMessageProcessingService = new SalesMessageProcessingServiceImpl(
			countDownLatch, salesMessageStorageServiceImpl, salesMessageRecordingServiceImpl);

	@Test
	public void testGetSaleObjectFromXMLForType1() throws Exception {

		String type1Message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<Sale>"
				+ "<Product>Orange</Product>" + "<Value>10.00</Value>" + "</Sale>";

		Sale saleType1 = salesMessageProcessingService.getSaleObjectFromXML(type1Message);

		Assert.assertEquals("Orange", saleType1.getProduct());
		Assert.assertEquals(new BigDecimal("10.00"), saleType1.getValue());
	}
	
	@Test
	public void testGetSaleObjectFromXMLForType2() throws Exception {

		String type2Message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<Sale>"
				+ "<Product>Apple</Product>" + "<Value>10.00</Value>" + "<Occurrences>2</Occurrences>" + "</Sale>";

		Sale saleType2 = salesMessageProcessingService.getSaleObjectFromXML(type2Message);
		
		Assert.assertEquals("Apple", saleType2.getProduct());
		Assert.assertEquals(new BigDecimal("10.00"), saleType2.getValue());
		Assert.assertEquals(2, saleType2.getOccurrences());
	}
	
	@Test
	public void testGetSaleObjectFromXMLForType3() throws Exception {

		String type3Message = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<Sale>"
				+ "<Product>Peach</Product>" + "<Value>10.00</Value>" + "<AdjustmentOperation>" + "<AdjustmentOperationType>"
				+ AdjustmentOperationType.ADD + "</AdjustmentOperationType>" + "<AdjustmentValue>20</AdjustmentValue>"
				+ "</AdjustmentOperation>" + "</Sale>";

		Sale saleType3 = salesMessageProcessingService.getSaleObjectFromXML(type3Message);

		Assert.assertEquals("Peach", saleType3.getProduct());
		Assert.assertEquals(new BigDecimal("10.00"), saleType3.getValue());
		Assert.assertEquals(AdjustmentOperationType.ADD,
				saleType3.getAdjustmentOperation().getAdjustmentOperationType());
		Assert.assertEquals(new BigDecimal("20"), saleType3.getAdjustmentOperation().getAdjustmentValue());
	}
	
	@Test
	public void testProcessSaleRecordFor10And50Sales() throws Exception {
		doNothing().when(salesMessageRecordingServiceImpl).saveSalesDetails(anyList());
		doNothing().when(salesMessageRecordingServiceImpl).saveAdjustmentsDetails(anyList());
		when(salesMessageStorageServiceImpl.save(any(Sale.class))).thenReturn("0");
		int counter = 0;
		for (int i = 0; i < 50; i++) {
			++counter;
			Sale sale = new Sale();
			sale.setProduct("Product" + i);
			sale.setValue(new BigDecimal(10.00).add(BigDecimal.valueOf(1.00)));
			salesMessageProcessingService.processSaleRecord(sale);
		}
		if(counter == 10)
		verify(salesMessageRecordingServiceImpl, times(1)).saveSalesDetails(anyList());
		if(counter == 50)
		verify(salesMessageRecordingServiceImpl, times(1)).saveAdjustmentsDetails(anyList());
	}	

}