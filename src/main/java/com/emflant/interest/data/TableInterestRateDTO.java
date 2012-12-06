package com.emflant.interest.data;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;
import com.emflant.interest.dto.InterestCalculateResultDTO;

public class TableInterestRateDTO implements TableSplitDTO {

	private EntDate fromDate;
	private EntDate toDate;
	private double rate;
	
	public EntDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(EntDate fromDate) {
		this.fromDate = fromDate;
	}
	public EntDate getToDate() {
		return toDate;
	}
	public void setToDate(EntDate toDate) {
		this.toDate = toDate;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
	public void display(String pattern){
		DecimalFormat df = new DecimalFormat("0.000");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		EntLogger.info(String.format(pattern
				, sdf.format(fromDate.getDate())
				, sdf.format(toDate.getDate())
				, df.format(rate)));
	}
	
	public String display() {
		return null;
	}
	
	
	public boolean put(InterestCalculateResultDTO interestCalculateDetailDTO) {
		interestCalculateDetailDTO.setInterestRate(getRate());
		return true;
	}

}
