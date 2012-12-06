package com.emflant.interest.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;

public class TableDailyBalanceDTO {
	
	private EntDate tradeDate;			//�ŷ�����
	private BigDecimal openBalance;		//�����ܾ�
	private BigDecimal closeBalance;	//�����ܾ�
	private BigDecimal inputAmount;		//�Աݱݾ�
	private BigDecimal outAmount;		//��ݱݾ�
	
	public TableDailyBalanceDTO(){
		this.openBalance = BigDecimal.ZERO;
		this.closeBalance = BigDecimal.ZERO;
		this.inputAmount = BigDecimal.ZERO;
		this.outAmount = BigDecimal.ZERO;
	}
	
	public EntDate getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(EntDate tradeDate) {
		this.tradeDate = tradeDate;
	}
	public BigDecimal getOpenBalance() {
		return openBalance;
	}
	public void setOpenBalance(BigDecimal openBalance) {
		this.openBalance = openBalance;
	}
	public BigDecimal getCloseBalance() {
		return closeBalance;
	}
	public void setCloseBalance(BigDecimal closeBalance) {
		this.closeBalance = closeBalance;
	}
	public BigDecimal getInputAmount() {
		return inputAmount;
	}
	public void setInputAmount(BigDecimal inputAmount) {
		this.inputAmount = inputAmount;
	}
	public BigDecimal getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}
	
	public void display(){
		String pattern = "%10s%15s%15s%15s%15s";
		display(pattern);
	}
	
	public void display(String pattern){
		DecimalFormat df = new DecimalFormat("#,###");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		EntLogger.info(String.format(pattern
				, sdf.format(tradeDate.getDate())
				, df.format(openBalance)
				, df.format(closeBalance)
				, df.format(inputAmount)
				, df.format(outAmount)));
	}


}
