package com.jpmorgan.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Tanmoy_Mondal
 * @version 1.0
 * @Description It represents the Sale data model.
 *
 */
@XmlRootElement(name="Sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sale {

	@XmlElement(name = "Product", required = true)
	private String product;
	
	@XmlElement(name = "Value", required = true)
	private BigDecimal value;
	
	@XmlElement(name = "Occurrences")
	private int occurrences = 1;
	
	@XmlElement(name = "AdjustmentOperation")
	private AdjustmentOperation adjustmentOperation;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public int getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public AdjustmentOperation getAdjustmentOperation() {
		return adjustmentOperation;
	}

	public void setAdjustmentOperation(AdjustmentOperation adjustmentOperation) {
		this.adjustmentOperation = adjustmentOperation;
	}

	@Override
	public String toString() {
		return "Sale [product=" + product + ", value=" + value + ", occurrences=" + occurrences + "]";
	}

	
	
	
}
