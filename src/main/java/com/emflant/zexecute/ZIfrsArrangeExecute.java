package com.emflant.zexecute;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.emflant.common.EntLogger;
import com.emflant.ifrs.arrange.ArrangeCashFlowTable;
import com.emflant.ifrs.data.MakeCashFlowTable;
import com.emflant.ifrs.irr.EirCalculate;


public class ZIfrsArrangeExecute {

	/**
	 * 유효이자율 및 이연부대수익상각계획표 만드는 main
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ZIfrsArrangeExecute().execute1();
		
	}
	
	public void execute1(){
		
		//현금흐름표를 만든다.
		MakeCashFlowTable mcft = new MakeCashFlowTable();
		List<HashMap<String, Object>> list = mcft.read2();
		
		List<BigDecimal> listIrr = new ArrayList<BigDecimal>();
		for(HashMap<String, Object> hmInput : list){
			BigDecimal total = ((BigDecimal)hmInput.get("bal")).
						add((BigDecimal)hmInput.get("interest")).
						add((BigDecimal)hmInput.get("fee"));
			listIrr.add(total);
		}
		
		//유효이자율 구하기
		EirCalculate cc = new EirCalculate();
		BigDecimal eir = cc.calculate(listIrr);
		EntLogger.info("유효이자율 : "+eir.toPlainString());
		
		
		//이연부대수익상각계획표 만들기
		ArrangeCashFlowTable asft = new ArrangeCashFlowTable(eir);
		asft.arrange(list);
	}
	


}
