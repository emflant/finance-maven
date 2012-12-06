package com.emflant.interest.data;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;
import com.emflant.interest.dto.InterestCalculateResultDTO;

public class TableTaxRateDTO implements TableSplitDTO {
	
	private String taxClassification;
	private EntDate fromDate;
	private EntDate toDate;
	private double incomeTaxRate;
	private double residentTaxRate;
	
	
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
	public double getIncomeTaxRate() {
		return incomeTaxRate;
	}
	public void setIncomeTaxRate(double incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}
	public double getResidentTaxRate() {
		return residentTaxRate;
	}
	public void setResidentTaxRate(double residentTaxRate) {
		this.residentTaxRate = residentTaxRate;
	}
	
	
	public void display(String pattern){
		DecimalFormat df = new DecimalFormat("0.000");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		EntLogger.info(String.format(pattern
				, sdf.format(fromDate.getDate())
				, sdf.format(toDate.getDate())
				, df.format(incomeTaxRate)
				, df.format(residentTaxRate)
		));
	}
	
	public String display() {
		return null;
	}
	
	public boolean put(InterestCalculateResultDTO interestCalculateDetailDTO) {
		// TODO Auto-generated method stub
		boolean result = true;
		if(interestCalculateDetailDTO.getTaxClassification().equals(this.getTaxClassification()))
		{
			interestCalculateDetailDTO.setIncomeTaxRate(getIncomeTaxRate());
			interestCalculateDetailDTO.setResidentTaxRate(getResidentTaxRate());
		} else {
			result = false;
		}
		
		
		
		return result;
	}

	public String getTaxClassification() {
		return taxClassification;
	}
	public void setTaxClassification(String taxClassification) {
		this.taxClassification = taxClassification;
	}
	
}
