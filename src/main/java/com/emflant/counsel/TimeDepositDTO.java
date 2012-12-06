package com.emflant.counsel;

import java.text.DecimalFormat;

import com.emflant.common.EntLogger;

public class TimeDepositDTO extends CounselDTO {

	public void displayResult(){

		DecimalFormat df = new DecimalFormat("#,###");
		EntLogger.info("=============================");
		EntLogger.info(String.format("%5s%15s", "계약기간:", getTermMonths()+"/"+getTermDays()));
		EntLogger.info(String.format("%5s%15s", "계약금액:", df.format(getAmount())));
		EntLogger.info(String.format("%5s%15s", "총이자액:", df.format(getGiveInterest())));
		EntLogger.info(String.format("%5s%15s", "세전금액:", df.format(getBfTaxTotalAmount())));
		EntLogger.info(String.format("%5s%15s", "소득세액:", df.format(getIncomeTax())));
		EntLogger.info(String.format("%5s%15s", "주민세액:", df.format(getResidentTax())));
		EntLogger.info("=============================");
		EntLogger.info(String.format("%5s%15s", "실지급액:", df.format(getAfTaxTotalAmount())));
		EntLogger.info("=============================");
	}
	
}
