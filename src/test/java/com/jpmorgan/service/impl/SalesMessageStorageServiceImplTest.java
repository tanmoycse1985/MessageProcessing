package com.jpmorgan.service.impl;

import com.jpmorgan.domain.Sale;
import com.jpmorgan.service.SalesMessageStorageService;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Unit Test for @SalesMessageStorageServiceImpl
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 * 
 */
public class SalesMessageStorageServiceImplTest {

	@Test
	public void testSave() throws Exception {

		Sale sale = new Sale();
		sale.setProduct("Product");
		sale.setValue(new BigDecimal(10.00));

		SalesMessageStorageService salesMessageStorageService = new SalesMessageStorageServiceImpl();
		String recordId = salesMessageStorageService.save(sale);

		Assert.assertNotNull(recordId);

		Sale saleRecord = salesMessageStorageService.getRecord(recordId);

		Assert.assertNotNull(saleRecord);
		Assert.assertEquals(saleRecord.getProduct(), sale.getProduct());
		Assert.assertEquals(saleRecord.getValue(), sale.getValue());
	}

}