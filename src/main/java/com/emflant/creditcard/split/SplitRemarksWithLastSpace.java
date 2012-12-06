/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emflant.creditcard.split;

/**
 *
 * @author emflant
 */
public class SplitRemarksWithLastSpace implements SplitRemarks {

    public String getRemarks(String strSms) {
        int nLastSpaceIndex = strSms.lastIndexOf(" ");
        //System.out.println(nWonIndex);
        int nMinIndex = strSms.lastIndexOf(" ", nLastSpaceIndex)+1;
        //System.out.println(nMinIndex);
        String strRemarksWithLastSpaceIndex = strSms.substring(nMinIndex);
        //String strMoney = strDateWithSlash.replace(",", "");
        //System.out.println(strMoney);
        return strRemarksWithLastSpaceIndex;
    }
}
