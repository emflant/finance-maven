/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 * �ſ�ī�� ����� ���� �޽���(SMS)�� �����ϴ� Ŭ����
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
     * �ſ�ī�� ����� ���� �޽���(SMS)�� �����ϴ� ���� �޼ҵ�    
     */
    public String split(String strSms){
    	StringBuilder sb = new StringBuilder(100);
    	sb.append(this.bankName);
    	sb.append(" ");
    	sb.append(this.splitAmount.getAmount(strSms));
    	sb.append("�� ");
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
     * �ſ�ī�� ����� ���� �޽���(SMS)�� �����ϴ� ���� �޼ҵ�    
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
