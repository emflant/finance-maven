/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;



/**
 *
 * @author emflant
 */
public class ExecuteSmsSplit {
    
    private String strSms;
    
    public ExecuteSmsSplit(String strSms){
        this.strSms = strSms;
    }
    
    /**
     * �ſ�ī�� ����� ���� �޽���(SMS)�� �����ϴ� ���� �޼ҵ�    
     */
    public String execute() {
        return execute(getBankSplitInstance());
    }

    /**
     * Ư������� SMS����Ŭ������ �ν��Ͻ��� �Է¹޾� SMS�� �����ϴ� ���� �޼ҵ�    
     * @param splitSms 
     */
    public String execute(SplitSms splitSms){
    	
    	String strResult = null;
    	
    	if(splitSms!=null){
    		makeSimpleString();
    		strResult = splitSms.split(this.strSms);
    	}
    	
        
        return strResult;
    }
    
    
    
    /**
     * �ſ�ī���� �ش����� �˾Ƴ��� �����SMS����Ŭ������ �ν��Ͻ��� �����Ѵ�.
     * @return 
     */
    private SplitSms getBankSplitInstance(){
        
        SplitSms splitSms = null;
        
        if(this.strSms.substring(0, 4).equals("��Ƽī��")){
            //System.out.println(this.strSms.substring(0, 4));
            splitSms = new SplitSmsWithCityCard();
        }
        
        else if(this.strSms.substring(0, 4).equals("�Ե�ī��")){
            //System.out.println(this.strSms.substring(0, 4));
            splitSms = new SplitSmsWithLotteCard();
        }
        
        return splitSms;
    }
    
    /**
     * �����̽� �� ���๮�ڸ� ���ش�.
     */
    private void makeSimpleString(){
        this.strSms = this.strSms.trim();
        this.strSms = this.strSms.replace("\r\n", " ");
        this.strSms = this.strSms.replace("  ", " ");
    }
    
    /**
     * SMS �޽����� �����Ѵ�.
     * @return 
     */
    public String getStrSms() {
        return strSms;
    }

    /**
     * SMS �޽����� �����Ѵ�.
     * @param strSms 
     */
    public void setStrSms(String strSms) {
        this.strSms = strSms;
    }
    
    
}
