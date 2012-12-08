package com.emflant.common;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.adapter.table.EntTable;
import com.emflant.accounting.dto.table.EntQuery;
import com.emflant.accounting.dto.table.TradeMasterDTO;
import com.emflant.common.EntMessage.EntMessageType;


public class EntTransaction {
	
	private int transactionSequence;
	
	private EntQuery entQuery;
	private boolean transactionCompleted;
	
	private String tradeCode;
	private String beanType;
	private String methodType;
	private String parameterType;
	private Object methodParam;
	private String slipNo;
	private int slipSeq;
	private boolean isOneSlip;
	private List results;
	private EntMessage message;
	private boolean isRemoveMessage;
	private String businessBaseDataTimeMillis;		//business 가 시작된 일시. 로그쌓일때 필요.
	private String systemDateTimeMillis;
	private String systemDate;
	private String systemTimeMillis;
	private String userId;		//log-in user.
	private String screenName;

	
	public EntTransaction(String tradeCode, Object methodParam){
		this.results = new ArrayList();
		this.tradeCode = tradeCode;
		this.methodParam = methodParam;
		this.slipSeq = 1;
	}
	
	/*
	public EntTransaction(String beanType, String methodType, Object methodParam){
		this.results = new ArrayList();
		this.beanType = beanType;
		this.methodType = methodType;
		this.methodParam = methodParam;
		this.slipSeq = 1;
	}
	*/
	
	public void setupTime(){
		this.systemDateTimeMillis = EntDate.getTodayDateTimeMillis()+this.transactionSequence;
		this.systemDate = this.systemDateTimeMillis.substring(0, 8);
		this.systemTimeMillis = this.systemDateTimeMillis.substring(8,17);
	}
	
	public void copyCommonInfo(EntBusiness business){
		this.isOneSlip = business.getIsOneSlip();
		this.userId = business.getUserId();
		this.screenName = business.getScreenName();
		this.businessBaseDataTimeMillis = business.getSystemDateTime();
	}
	
	public void getMethodInfoWithTradeCode() throws EntException{
		
		if(this.tradeCode != null){
			
			StringBuilder sbQuery = new StringBuilder(256);
			sbQuery.append("SELECT * FROM trade_master where trade_code = ");
			sbQuery.append(EntCommon.convertQuery(this.tradeCode));
			List<TradeMasterDTO> lResult = this.entQuery.select(TradeMasterDTO.class, sbQuery);
			
			if(lResult.isEmpty()){
				throw new EntException("["+this.tradeCode+"] 해당 거래코드가 존재하지 않습니다.");
			}
			
			TradeMasterDTO tradeMasterDTO = lResult.get(0);
			
			this.beanType = tradeMasterDTO.getBeanType();
			this.methodType = tradeMasterDTO.getMethodType();
			this.parameterType = tradeMasterDTO.getParameterType();
		}
		
	}
	
	
	public EntQuery getEntQuery() {
		
		return entQuery;
	}



	void setEntQuery(EntQuery entQuery) {
		this.entQuery = entQuery;
	}


	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}


	public String getBeanType() {
		return beanType;
	}

	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public Object getMethodParam() {
		return methodParam;
	}
	void setMethodParam(Object methodParam) {
		this.methodParam = methodParam;
	}
	
	
	public void addResult(Object result){
		this.results.add(result);
	}
	
	public List getResults(){
		return this.results;
	}
	
	public Object getFirstResult(){
		return this.results.get(0);
	}

	public boolean isTransactionCompleted() {
		return transactionCompleted;
	}

	void setTransactionCompleted(boolean transactionCompleted) {
		this.transactionCompleted = transactionCompleted;
	}
	
	
	public EntMessage getMessage() {
		return this.message;
	}
	
	public void setSuccessMessage(String message){
		this.message = new EntMessage(EntMessageType.INFORMATION, message);
	}
	
	public void removeMessage(){
		this.message = null;
	}
	
	public void setErrorMessage(Exception e){
		e.printStackTrace();
		if(isRemoveMessage){
			this.message = null;
		} else {
			this.message = new EntMessage(e);
		}
	}

	public void setErrorMessage(String message){
		this.message = new EntMessage(EntMessageType.ERROR, message);
	}
	
	public String getSlipNo() {
		return slipNo;
	}

	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	
	

	public int getSlipSeq() {
		return slipSeq;
	}

	public void setSlipSeq(int slipSeq) {
		this.slipSeq = slipSeq;
	}

	public boolean isOneSlip() {
		return isOneSlip;
	}

	void setOneSlip(boolean isOneSlip) {
		this.isOneSlip = isOneSlip;
	}

	public void display(){
		
		String strParameterType = "";
		if(this.parameterType != null){
			strParameterType = this.parameterType;
		}
		
		EntLogger.info("Call method ("+this.tradeCode + ") : "+this.beanType+"."+this.methodType+"("+strParameterType+")");
		
	}

	public boolean isRemoveMessage() {
		return isRemoveMessage;
	}

	void setRemoveMessage(boolean isRemoveMessage) {
		this.isRemoveMessage = isRemoveMessage;
	}
	
	
	
	public String getUserId() {
		return userId;
	}



	void setUserId(String userId) {
		this.userId = userId;
	}



	public String getSystemDateTimeMillis() {
		return systemDateTimeMillis;
	}



	void setSystemDateTimeMillis(String systemDateTimeMillis) {
		this.systemDateTimeMillis = systemDateTimeMillis;
	}



	public String getSystemDate() {
		return systemDate;
	}



	void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}



	public String getSystemTimeMillis() {
		return systemTimeMillis;
	}



	void setSystemTimeMillis(String systemTimeMillis) {
		this.systemTimeMillis = systemTimeMillis;
	}



	public String getBusinessBaseDataTimeMillis() {
		return businessBaseDataTimeMillis;
	}

	public void setBusinessBaseDataTimeMillis(String businessBaseDataTimeMillis) {
		this.businessBaseDataTimeMillis = businessBaseDataTimeMillis;
	}

	public EntHashList selectCodeToHashMap(StringBuilder sbQuery) throws EntException{
		return this.entQuery.selectCodeToHashMap(sbQuery);
	}
	
	@SuppressWarnings("unchecked")
	public List select(Class clazz, StringBuilder sbQuery) throws EntException{
		return this.entQuery.select(clazz, sbQuery);
	}
	
	
	public DefaultTableModel selectToTableModel(StringBuilder sbQuery) throws EntException{
		return this.entQuery.selectToTableModel(sbQuery);
	}
	
	public void insert(EntTable entTable) throws EntException {
		this.entQuery.insert(entTable);
	}
	
	public int update(StringBuilder sbQuery) throws EntException {
		return this.entQuery.update(sbQuery);
	}

	public void setTransactionSequence(int transactionSequence) {
		this.transactionSequence = transactionSequence;
	}

	public int getTransactionSequence() {
		return transactionSequence;
	}
}
