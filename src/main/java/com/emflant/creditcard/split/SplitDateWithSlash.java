/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 *
 * @author emflant
 */
public class SplitDateWithSlash implements SplitDate {

    public String getDate(String strSms) {
        int nSlashIndex = strSms.indexOf("/");
        //System.out.println(nWonIndex);
        int nMinIndex = strSms.lastIndexOf(" ", nSlashIndex)+1;
        //System.out.println(nMinIndex);
        
        int nMaxIndex = strSms.indexOf(" ", nSlashIndex);
        
        String strDateWithSlash = strSms.substring(nMinIndex, nMaxIndex);
        //String strMoney = strDateWithSlash.replace(",", "");
        //System.out.println(strMoney);
        return strDateWithSlash;
        
        
    }
    
}
