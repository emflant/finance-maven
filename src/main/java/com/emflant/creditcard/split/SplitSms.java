/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 * 신용카드 사용후 오는 메시지(SMS)를 분해하는 클래스
 */
public abstract class SplitSms {
    
	private String bankName;
    private SplitAmount splitAmount;
    private SplitDate splitDate;
    private SplitTime splitTime;
    private SplitRemarks splitRemarks;
    
    public SplitSms(String bankName, SplitAmount splitAmount, SplitDate splitDate
    		, SplitTime splitTime, SplitRemarks splitRemarks){
    	this.bankName = bankName;
        this.splitAmount = splitAmount;
        this.splitDate = splitDate;
        this.splitTime = splitTime;
        this.splitRemarks = splitRemarks;
    }
    
    
    /**
     * 신용카드 사용후 오는 메시지(SMS)를 분해하는 메인 메소드    
     */
    public String split(String strSms){
    	StringBuilder sb = new StringBuilder(100);
    	sb.append(this.bankName);
    	sb.append(" ");
    	sb.append(this.splitAmount.getAmount(strSms));
    	sb.append("원 ");
    	sb.append(this.splitDate.getDate(strSms));
    	sb.append(" ");
    	sb.append(this.splitTime.getTime(strSms));
    	sb.append(" ");
    	sb.append(this.splitRemarks.getRemarks(strSms));
    	
    	return sb.toString();
    	
        //System.out.println(this.splitAmount.getAmount(strSms));
        //System.out.println(this.splitDate.getDate(strSms));
        //System.out.println(this.splitTime.getTime(strSms));
        //System.out.println(this.splitRemarks.getRemarks(strSms));
    }
    

    /**
     * 신용카드 사용후 오는 메시지(SMS)를 분해하는 메인 메소드    
     */
    public String splitToInputDTO(String strSms){
    	StringBuilder sb = new StringBuilder(100);
    	sb.append(this.bankName);
    	sb.append(" ");
    	sb.append(this.splitAmount.getAmount(strSms));
    	sb.append(" ");
    	sb.append(this.splitDate.getDate(strSms));
    	sb.append(" ");
    	sb.append(this.splitTime.getTime(strSms));
    	sb.append(" ");
    	sb.append(this.splitRemarks.getRemarks(strSms));
    	
    	return sb.toString();
    	
        //System.out.println(this.splitAmount.getAmount(strSms));
        //System.out.println(this.splitDate.getDate(strSms));
        //System.out.println(this.splitTime.getTime(strSms));
        //System.out.println(this.splitRemarks.getRemarks(strSms));
    }
}
