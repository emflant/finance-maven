/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 * 롯데카드 SMS 메시지 분해 클래스
 */
public class SplitSmsWithLotteCard extends SplitSms {
    
    /**
     * SMS 메시지를 입력받고, 분해기준을 정의하는 생성자.
     * @param strSms 
     */
    public SplitSmsWithLotteCard(){
        this(new SplitAmountWithWon(), new SplitDateWithSlash(), new SplitTimeWithColon(), new SplitRemarksWithLastSpace());
    }
    
    public SplitSmsWithLotteCard(SplitAmount splitAmount, SplitDate splitDate, SplitTime splitTime, SplitRemarks splitRemarks){
        super("롯데카드", splitAmount, splitDate, splitTime, splitRemarks);
    }
}
