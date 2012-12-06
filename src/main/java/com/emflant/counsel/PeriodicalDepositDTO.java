package com.emflant.counsel;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.emflant.common.EntLogger;

public class PeriodicalDepositDTO extends CounselDTO {

	public BigDecimal getAmount(){
		
		BigDecimal amount = super.getAmount();
		
		if(getPayAmount() != null){
			amount = getPayAmount().multiply(BigDecimal.valueOf(getTermMonths()));
		}
		
		return amount;
	}
	
	public void displayResult(){

		DecimalFormat df = new DecimalFormat("#,###");
		EntLogger.info("=============================");
		EntLogger.info(String.format("%5s%15s", "���Ⱓ:", getTermMonths()));
		EntLogger.info(String.format("%5s%15s", "�����Ծ�:", df.format(getPayAmount())));
		EntLogger.info(String.format("%5s%15s", "�Ѻ��Ծ�:", df.format(getAmount())));
		EntLogger.info(String.format("%5s%15s", "�����ھ�:", df.format(getGiveInterest())));
		EntLogger.info(String.format("%5s%15s", "�����ݾ�:", df.format(getBfTaxTotalAmount())));
		EntLogger.info(String.format("%5s%15s", "�ҵ漼��:", df.format(getIncomeTax())));
		EntLogger.info(String.format("%5s%15s", "�ֹμ���:", df.format(getResidentTax())));
		EntLogger.info("=============================");
		EntLogger.info(String.format("%5s%15s", "�����޾�:", df.format(getAfTaxTotalAmount())));
		EntLogger.info("=============================");
	}
	
	
}
