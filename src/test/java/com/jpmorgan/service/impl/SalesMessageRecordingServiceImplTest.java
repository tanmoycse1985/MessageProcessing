package com.jpmorgan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.internal.WhiteboxImpl;

import com.jpmorgan.domain.Sale;

/**
 * Unit Test for @SalesMessageRecordingServiceImpl
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 * 
 */

public class SalesMessageRecordingServiceImplTest {

	/**
	 * Creating test data.
	 * @return List<Sale>
	 */
	public List<Sale> getSampleSaleList() {

		List<Sale> sales = new ArrayList<>();

		Sale appleSale1 = new Sale();
		appleSale1.setProduct("Apple");
		appleSale1.setValue(new BigDecimal(12.22));

		Sale appleSale2 = new Sale();
		appleSale2.setProduct("Apple");
		appleSale2.setValue(new BigDecimal(24.00));

		Sale pearSale1 = new Sale();
		pearSale1.setProduct("Pear");
		pearSale1.setValue(new BigDecimal(60.00));
		pearSale1.setOccurrences(4);

		sales.add(appleSale1);
		sales.add(appleSale2);
		sales.add(pearSale1);

		return sales;
	}
	
	@Test
	public void testGetSaleGroupByProductType() throws Exception {
		List<Sale> sampleList = getSampleSaleList();
		SalesMessageRecordingServiceImpl salesMessageRecordingServiceImpl = new SalesMessageRecordingServiceImpl();
		Map<String, List<Sale>> salesMap = WhiteboxImpl.invokeMethod(salesMessageRecordingServiceImpl,"getSaleGroupByProductType", sampleList);
		Assert.assertNotNull(salesMap);
		Assert.assertTrue(salesMap.containsKey(sampleList.get(0).getProduct()));
	}
	

}