package com.jpmorgan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jpmorgan.domain.Sale;
import com.jpmorgan.service.SalesMessageRecordingService;

/**
 * @author Tanmoy_Mondal
 * @version 1.0
 * This class is an implementation of @SalesMessageRecordingService. It records
 * the information of Sales and Adjustments.
 */
@Service
public class SalesMessageRecordingServiceImpl implements SalesMessageRecordingService {
	
	public SalesMessageRecordingServiceImpl() {
	}

	@Override
	public void saveSalesDetails(final List<Sale> sales) {

		/** All sales are getting recorded **/
		System.out.println("Logging Sales report...");

		Map<String, List<Sale>> salesMap = getSaleGroupByProductType(sales);

		/** Logging the details of sales for each product type */
		for (Map.Entry<String, List<Sale>> entry : salesMap.entrySet()) {
			int totalNoOfSales = 0;
			BigDecimal totalSaleValue = new BigDecimal(0);
			String product = entry.getKey();
			List<Sale> productList = entry.getValue();
			for (Sale sale : productList) {
				totalNoOfSales += sale.getOccurrences();
				totalSaleValue = totalSaleValue
						.add(BigDecimal.valueOf(sale.getOccurrences()).multiply(sale.getValue()));
			}
			System.out.println("For Product " + product + " , the number of sales is " + totalNoOfSales
					+ ", and total values of all the sales of this product is " + totalSaleValue);

		}
	}

	/**
	 * Creating Map for grouping sales based on product types
	 * 
	 * @param List<Sale>
	 * @return Map containing Product as key and Sales as value.
	 */
	private Map<String, List<Sale>> getSaleGroupByProductType(final List<Sale> sales) {
		Map<String, List<Sale>> salesMap = new HashMap<String, List<Sale>>();
		if (sales != null) {
			for (Sale sale : sales) {
				List<Sale> savedSale = new ArrayList<>();
				if (salesMap.containsKey(sale.getProduct())) {
					savedSale = salesMap.get(sale.getProduct());
					savedSale.add(sale);
					salesMap.put(sale.getProduct(), savedSale);
				} else {
					savedSale.add(sale);
					salesMap.put(sale.getProduct(), savedSale);
				}
			}
		}
		return salesMap;
	}

	@Override
	public void saveAdjustmentsDetails(final List<Sale> saleList) {

		System.out.println("Logging Adjustments Details :--");
		List<Sale> adjustmentSaleList = new ArrayList<>();

		for (Sale sale : saleList) {

			adjustmentSaleList.add(sale);

			if (sale.getAdjustmentOperation() != null) {

				System.out.println("Logging adjustment for product " + sale.getProduct() + " by "
						+ sale.getAdjustmentOperation().getAdjustmentOperationType().toString() + "ing "
						+ sale.getAdjustmentOperation().getAdjustmentValue());

				BigDecimal totalSaleValueAfterAbjustment = new BigDecimal(0);
				Map<String, List<Sale>> adjustmentSalesMap = getSaleGroupByProductType(adjustmentSaleList);
				if (adjustmentSalesMap.get(sale.getProduct()) != null) {
					List<Sale> salesList = adjustmentSalesMap.get(sale.getProduct());
					for (Sale adjustingSale : salesList) {
						BigDecimal adjustmentValue = new BigDecimal(0);
						BigDecimal newSaleValue = new BigDecimal(0);
						switch (sale.getAdjustmentOperation().getAdjustmentOperationType()) {
						case ADD:
							adjustmentValue = adjustingSale.getValue()
									.add(sale.getAdjustmentOperation().getAdjustmentValue());
							newSaleValue = newSaleValue
									.add(BigDecimal.valueOf(adjustingSale.getOccurrences()).multiply(adjustmentValue));
							break;
						case SUBTRACT:
							adjustmentValue = adjustingSale.getValue()
									.subtract(sale.getAdjustmentOperation().getAdjustmentValue());
							newSaleValue = newSaleValue
									.add(BigDecimal.valueOf(adjustingSale.getOccurrences()).multiply(adjustmentValue));
							break;
						case MULTIPLY:
							adjustmentValue = adjustingSale.getValue()
									.multiply(sale.getAdjustmentOperation().getAdjustmentValue());
							newSaleValue = newSaleValue
									.add(BigDecimal.valueOf(adjustingSale.getOccurrences()).multiply(adjustmentValue));
							break;
						}
						totalSaleValueAfterAbjustment = totalSaleValueAfterAbjustment.add(newSaleValue);
						System.out.println("For Product " + adjustingSale.getProduct() + ", Total number of sales ::"
								+ adjustingSale.getOccurrences() + " value before :: " + adjustingSale.getValue()
								+ " and after adjustment :: " + adjustmentValue + " Total Value is :: " + newSaleValue);
					}
					System.out.println("After adjustment (" + sale.getAdjustmentOperation().getAdjustmentOperationType()
							+ " Operation) for product " + sale.getProduct() + ", total sale value :: "
							+ totalSaleValueAfterAbjustment);
				}
			}
		}

	}
}
