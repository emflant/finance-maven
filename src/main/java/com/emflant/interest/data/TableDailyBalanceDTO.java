package com.emflant.interest.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;

public class TableDailyBalanceDTO {
	
	private EntDate tradeDate;			//거래일자
	private BigDecimal openBalance;		//개시잔액
	private BigDecimal closeBalance;	//마감잔액
	private BigDecimal inputAmount;		//입금금액
	private BigDecimal outAmount;		//출금금액
	
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
