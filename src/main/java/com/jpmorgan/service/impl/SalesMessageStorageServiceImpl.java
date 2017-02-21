package com.jpmorgan.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jpmorgan.domain.Sale;
import com.jpmorgan.service.SalesMessageStorageService;

/**
 * @author Tanmoy_Mondal
 * @version 1.0
 * @Description This class implements @SalesMessageStorageService and storing data in LinkedHashMap
 */
@Service
public class SalesMessageStorageServiceImpl implements SalesMessageStorageService {
	
    private static final Map<String, Sale> SALES_STORAGE = new LinkedHashMap<>();

    @Override
    public String save(Sale sale) {
        String key = UUID.randomUUID().toString();
        SALES_STORAGE.put(key, sale);
        return key;
    }
    // Using it for testing purpose
    @Override
    public Sale getRecord(String recordId) {
        return SALES_STORAGE.get(recordId);
    }
    
    

}
