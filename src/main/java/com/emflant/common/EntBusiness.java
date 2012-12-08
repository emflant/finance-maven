package com.emflant.common;

import java.util.ArrayList;
import java.util.List;

import com.emflant.accounting.annotation.EntDisplay;



public class EntBusiness {
	
	@EntDisplay
	private String userId;
	@EntDisplay
	private String screenName;
	@EntDisplay
	private boolean isOneSlip;
	
	private List<EntTransaction> transactions;

	
	private boolean isRemoveMessage;
	private int successCount;
	private String systemDateTime;
	
	public EntBusiness(){
		this.transactions = new ArrayList<EntTransaction>();
	}
	
	public void setupTime(){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.systemDateTime = EntDate.getTodayDateTimeMillis()+"0";
	}
	
	
	public void addTransaction(String tradeCode) {
		addTransaction(tradeCode, null);
	}

	public void addTransaction(String tradeCode, Object methodParam) {
		EntTransaction transaction = new EntTransaction(tradeCode, methodParam);
		transaction.setRemoveMessage(this.isRemoveMessage);
		this.transactions.add(transaction);
	}
	/*
	@Deprecated
	public void addTransaction(String beanName, String methodName, Object methodParam) {
	
		EntTransaction transaction = new EntTransaction(beanName, methodName, methodParam);
		transaction.setRemoveMessage(this.isRemoveMessage);
		this.transactions.add(transaction);
		
	}*/
	
	public List<EntTransaction> getTransactions(){
		return this.transactions;
	}
	
	public EntTransaction getTransactionByIndex(int index){
		return this.transactions.get(index);
	}
	
	public Object getResultByTransactionIndex(int index){
		return getTransactionByIndex(index).getFirstResult();
	}
	
	public Object getResultByTransactionIndex(int index, int index2){
		return getTransactionByIndex(index).getResults().get(index2);
	}
	
	public List getResultsByTransactionIndex(int index){
		return getTransactionByIndex(index).getResults();
	}
	
	public EntTransaction getFirstTransaction(){
		return this.transactions.get(0);
	}

	public boolean getIsOneSlip() {
		return isOneSlip;
	}
	
	/**
	 * 전표번호 생성시 트랜젝션마다 다르게 하려면 false
	 * 모두 같은 전표로 기표하려면 true;
	 */
	public void setIsOneSlip(boolean isOneSlip) {
		this.isOneSlip = isOneSlip;
	}

	/**
	 * If you don't want to see the messagebox, set true.
	 * @return
	 */
	public boolean getIsRemoveMessage() {
		return isRemoveMessage;
	}

	public void setIsRemoveMessage(boolean isRemoveMessage) {
		this.isRemoveMessage = isRemoveMessage;
	}

	public int getSuccessCount() {
		return successCount;
	}
	
	public void addSuccessCount(){
		this.successCount++;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}


	public int getTransactionCount(){
		return transactions.size();
	}
	
	

	public String getSystemDateTime() {
		return systemDateTime;
	}



	public void setSystemDateTime(String systemDateTime) {
		this.systemDateTime = systemDateTime;
	}



	public boolean isSuccessTransaction(){
		boolean result = false;
		
		if(transactions.size() == this.successCount){
			result = true;
		}
		
		return result;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getScreenName() {
		return screenName;
	}



	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	

	
}
