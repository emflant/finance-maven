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
     * 신용카드 사용후 오는 메시지(SMS)를 분해하는 실행 메소드    
     */
    public String execute() {
        return execute(getBankSplitInstance());
    }

    /**
     * 특정기관의 SMS분해클래스의 인스턴스를 입력받아 SMS를 분해하는 실행 메소드    
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
     * 신용카드의 해당기관을 알아내어 기관별SMS분해클래스의 인스턴스를 생성한다.
     * @return 
     */
    private SplitSms getBankSplitInstance(){
        
        SplitSms splitSms = null;
        
        if(this.strSms.substring(0, 4).equals("씨티카드")){
            //System.out.println(this.strSms.substring(0, 4));
            splitSms = new SplitSmsWithCityCard();
        }
        
        else if(this.strSms.substring(0, 4).equals("롯데카드")){
            //System.out.println(this.strSms.substring(0, 4));
            splitSms = new SplitSmsWithLotteCard();
        }
        
        return splitSms;
    }
    
    /**
     * 스페이스 및 개행문자를 없앤다.
     */
    private void makeSimpleString(){
        this.strSms = this.strSms.trim();
        this.strSms = this.strSms.replace("\r\n", " ");
        this.strSms = this.strSms.replace("  ", " ");
    }
    
    /**
     * SMS 메시지를 리턴한다.
     * @return 
     */
    public String getStrSms() {
        return strSms;
    }

    /**
     * SMS 메시지를 셋팅한다.
     * @param strSms 
     */
    public void setStrSms(String strSms) {
        this.strSms = strSms;
    }
    
    
}
