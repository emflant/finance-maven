/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 *
 * @author emflant
 */
public class SplitTimeWithColon implements SplitTime {

    public String getTime(String strSms) {
        int nColonIndex = strSms.indexOf(":");
        //System.out.println(nWonIndex);
        int nMinIndex = strSms.lastIndexOf(" ", nColonIndex)+1;
        //System.out.println(nMinIndex);
        
        int nMaxIndex = strSms.indexOf(" ", nColonIndex);
        
        String strTimeWithColon = strSms.substring(nMinIndex, nMaxIndex);
        //String strMoney = strDateWithSlash.replace(",", "");
        //System.out.println(strMoney);
        return strTimeWithColon;
        
    }
}
