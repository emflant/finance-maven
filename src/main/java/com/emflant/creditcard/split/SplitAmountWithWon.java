/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 *
 * @author emflant
 */
public class SplitAmountWithWon implements SplitAmount {

    public String getAmount(String strSms) 
    {   
        int nWonIndex = strSms.indexOf("��");
        //System.out.println(nWonIndex);
        int nMinIndex = strSms.lastIndexOf(" ", nWonIndex)+1;
        //System.out.println(nMinIndex);
        
        String strMoneyWithComma = strSms.substring(nMinIndex, nWonIndex);
        String strMoney = strMoneyWithComma.replace(",", "");
        //System.out.println(strMoney);
        return strMoney;
    }
}
