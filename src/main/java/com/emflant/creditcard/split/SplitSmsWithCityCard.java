/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 * ��Ƽī�� SMS �޽��� ���� Ŭ����
 */
public class SplitSmsWithCityCard extends SplitSms {

    
    /**
     * SMS �޽����� �Է¹ް�, ���ر����� �����ϴ� ������.
     * @param strSms 
     */
    public SplitSmsWithCityCard(){
        this(new SplitAmountWithWon(), new SplitDateWithSlash(), new SplitTimeWithColon(), new SplitRemarksWithLastSpace());
    }
    
    public SplitSmsWithCityCard(SplitAmount splitAmount, SplitDate splitDate, SplitTime splitTime, SplitRemarks splitRemarks){
        super("��Ƽī��", splitAmount, splitDate, splitTime, splitRemarks);
    }
    
}
