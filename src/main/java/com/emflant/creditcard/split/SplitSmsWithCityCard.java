/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 * 씨티카드 SMS 메시지 분해 클래스
 */
public class SplitSmsWithCityCard extends SplitSms {

    
    /**
     * SMS 메시지를 입력받고, 분해기준을 정의하는 생성자.
     * @param strSms 
     */
    public SplitSmsWithCityCard(){
        this(new SplitAmountWithWon(), new SplitDateWithSlash(), new SplitTimeWithColon(), new SplitRemarksWithLastSpace());
    }
    
    public SplitSmsWithCityCard(SplitAmount splitAmount, SplitDate splitDate, SplitTime splitTime, SplitRemarks splitRemarks){
        super("씨티카드", splitAmount, splitDate, splitTime, splitRemarks);
    }
    
}
