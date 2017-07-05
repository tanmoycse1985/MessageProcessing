package com.jpmorgan.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Adjustment Operation Model class
 * @author Tanmoy_Mondal
 * @version 1.0 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AdjustmentOperation {

    @XmlElement(name="AdjustmentOperationType", required=true)
    private AdjustmentOperationType adjustmentOperationType;
    
    @XmlElement(name="AdjustmentValue", required=true)
    private BigDecimal adjustmentValue;

	public AdjustmentOperationType getAdjustmentOperationType() {
		return adjustmentOperationType;
	}

	public void setAdjustmentOperationType(AdjustmentOperationType adjustmentOperationType) {
		this.adjustmentOperationType = adjustmentOperationType;
	}

	public BigDecimal getAdjustmentValue() {
		return adjustmentValue;
	}

	public void setAdjustmentValue(BigDecimal adjustmentValue) {
		this.adjustmentValue = adjustmentValue;
	}

	@Override
	public String toString() {
		return "AdjustmentOperation [adjustmentOperationType=" + adjustmentOperationType + ", adjustmentValue="
				+ adjustmentValue + "]";
	}

}
