package com.jpmorgan.util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Utility class
 * 
 * @author Tanmoy_Mondal
 * @version 1.0
 */
public class Util {

	/**
	 * Validating the received XML from JMS Queue.
	 * 
	 * @param xml
	 * @throws Exception
	 */
	public static void validateSaleXML(String xml) throws Exception {

		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new InputSource(new StringReader(xml)));
		NodeList nodeList = doc.getElementsByTagName("*");
		Map<String, String> nodeMap = new HashMap<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeMap.put(nodeList.item(i).getNodeName(), nodeList.item(i).getTextContent());
		}
		if (!nodeMap.containsKey(Constants.PRODUCT)) {
			throw new Exception("Product field doesn't exists");
		}
		if (!nodeMap.containsKey(Constants.VALUE)) {
			throw new Exception("Value field doesn't exists");
		}
		if (null == nodeMap.get(Constants.PRODUCT) || nodeMap.get(Constants.PRODUCT).trim().isEmpty()) {
			throw new Exception("Product Name doesn't exists");
		} else if (null == nodeMap.get(Constants.VALUE) || nodeMap.get(Constants.VALUE).toString().trim().isEmpty()) {
			throw new Exception("Value doesn't exists");
		}
	}

}
