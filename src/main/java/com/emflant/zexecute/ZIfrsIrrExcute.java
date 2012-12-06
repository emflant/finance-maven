package com.emflant.zexecute;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.emflant.common.EntLogger;
import com.emflant.ifrs.irr.EirCalculate;

public class ZIfrsIrrExcute {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(new BigDecimal("-200000"));
		
		int nCnt = 24;
		for(int i=0;i<nCnt;i++){
			list.add(new BigDecimal("11333.333333333334"));
		}
		
		EirCalculate eir = new EirCalculate();
		double db = eir.calculate(list).doubleValue();
		EntLogger.info("double Çü : "+db);

	}

}
