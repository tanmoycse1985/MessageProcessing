package com.jpmorgan.service;

import com.jpmorgan.domain.Sale;

/**
 * This class is act as an interface for @SalesMessageStorageServiceImpl
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 *
 */
public interface SalesMessageStorageService {

    /**
     * Saves a single sale record
     * 
     * @param 
     * @return An unique id
     */
    String save(Sale sale);


    /**
     * Get sale record by using the record Id
     *
     * @param 
     * @return the searched Sale
     */
    Sale getRecord(String recordId);

}
