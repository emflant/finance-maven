package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntDate;

public class Rate {
	
	public BigDecimal getGiveRate(String product, EntDate baseDate, int term){
		return BigDecimal.valueOf(0.042);
	}
	
	/**
	 * �ҵ漼��
	 * @return
	 */
	public BigDecimal getIncomeTaxRate(){
		return BigDecimal.valueOf(0.14);
	}
	
	/**
	 * �ֹμ���
	 * @return
	 */
	public BigDecimal getResidentTaxRate(){
		return BigDecimal.valueOf(0.10);
	}

}
