package com.emflant.counsel;

import java.text.DecimalFormat;

import com.emflant.common.EntLogger;

public class TimeDepositDTO extends CounselDTO {

	public void displayResult(){

		DecimalFormat df = new DecimalFormat("#,###");
		EntLogger.info("=============================");
		EntLogger.info(String.format("%5s%15s", "���Ⱓ:", getTermMonths()+"/"+getTermDays()));
		EntLogger.info(String.format("%5s%15s", "���ݾ�:", df.format(getAmount())));
		EntLogger.info(String.format("%5s%15s", "�����ھ�:", df.format(getGiveInterest())));
		EntLogger.info(String.format("%5s%15s", "�����ݾ�:", df.format(getBfTaxTotalAmount())));
		EntLogger.info(String.format("%5s%15s", "�ҵ漼��:", df.format(getIncomeTax())));
		EntLogger.info(String.format("%5s%15s", "�ֹμ���:", df.format(getResidentTax())));
		EntLogger.info("=============================");
		EntLogger.info(String.format("%5s%15s", "�����޾�:", df.format(getAfTaxTotalAmount())));
		EntLogger.info("=============================");
	}
	
}
