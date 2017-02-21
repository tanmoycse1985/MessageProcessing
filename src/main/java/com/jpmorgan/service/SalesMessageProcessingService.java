package com.jpmorgan.service;

import javax.xml.bind.JAXBException;

import com.jpmorgan.domain.Sale;

/**
 * A Sales Message Processing Service Interface
 */
public interface SalesMessageProcessingService {

    /**
     * Processing a Sale record
     * @param sale
     */
    void processSaleRecord(Sale sale);
    
    /**
     * Unmarshalling the Sale object
     * 
     * @param msgText
     * @return @Sale
     * @throws JAXBException
     */
    public Sale getSaleObjectFromXML(String msgText) throws JAXBException;
    
}
