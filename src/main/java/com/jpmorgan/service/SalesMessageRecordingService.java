package com.jpmorgan.service;

import java.util.List;

import com.jpmorgan.domain.Sale;

/**
 * 
 * This class acts as an interface for @SalesMessageRecordingServiceImpl
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 */
public interface SalesMessageRecordingService {

    /**
     * Save the number of sales of each product and their total value
     * @param List of sales
     */
    void saveSalesDetails(List<Sale> sales);

    /**
     * Save the adjustments that have been made to each sale type
     * @param List of sales
     */
    void saveAdjustmentsDetails(List<Sale> sales);
}
