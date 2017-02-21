package com.jpmorgan.service;

import com.jpmorgan.domain.Sale;

/**
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 * @Description This class is act as an interface for @SalesMessageStorageServiceImpl
 *
 */
public interface SalesMessageStorageService {

    /**
     * Saves a single sale record
     * 
     * @param sale
     * @return An unique id
     */
    String save(Sale sale);


    /**
     * Get sale record by using the record Id
     *
     * @param recordId
     * @return the searched Sale
     */
    Sale getRecord(String recordId);

}
