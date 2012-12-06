/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 * �Ե�ī�� SMS �޽��� ���� Ŭ����
 */
public class SplitSmsWithLotteCard extends SplitSms {
    
    /**
     * SMS �޽����� �Է¹ް�, ���ر����� �����ϴ� ������.
     * @param strSms 
     */
    public SplitSmsWithLotteCard(){
        this(new SplitAmountWithWon(), new SplitDateWithSlash(), new SplitTimeWithColon(), new SplitRemarksWithLastSpace());
    }
    
    public SplitSmsWithLotteCard(SplitAmount splitAmount, SplitDate splitDate, SplitTime splitTime, SplitRemarks splitRemarks){
        super("�Ե�ī��", splitAmount, splitDate, splitTime, splitRemarks);
    }
}
