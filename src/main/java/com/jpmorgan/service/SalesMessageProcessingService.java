package com.jpmorgan.service;

import javax.xml.bind.JAXBException;

import com.jpmorgan.domain.Sale;

/**
 * A Sales Message Processing Service Interface
 * 
 * @author Tanmoy_Mondal
 */
public interface SalesMessageProcessingService {

    /**
     * Processing a Sale record
     * @param
     */
    void processSaleRecord(Sale sale);
    
    /**
     * Unmarshalling the Sale object
     * 
     * @param 
     * @return @Sale
     * @throws JAXBException
     */
    public Sale getSaleObjectFromXML(String msgText) throws JAXBException;
    
}
