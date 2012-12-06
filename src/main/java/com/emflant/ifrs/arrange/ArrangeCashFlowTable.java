package com.emflant.ifrs.arrange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.emflant.common.EntLogger;

/**
 * 유효이자율에 의한 이자수익인식 및 이연부대수익상각 계획표를 생성한다.
 * @author nuri
 *
 */
public class ArrangeCashFlowTable {
	
	private BigDecimal irr;
	private List<CashFlowDTO> list;
	private List<ArrangedCashFlowDTO> arrangedList;
	
	/**
	 * 유효이자율을 입력하는 생성자.
	 * 
	 * @param irr
	 */
	public ArrangeCashFlowTable(BigDecimal irr){
		this.irr = irr;
	}
	
	/**
	 * 유효이자율에 의한 이자수익인식 및 이연부대수익상각 계획표를 생성한다.
	 * @param listCashflow
	 * @return
	 */
	public List<HashMap<String, Object>> arrange(List<HashMap<String, Object>> listCashflow){
		
		//입력값
		list = new ArrayList<CashFlowDTO>();
		for(HashMap<String, Object> hmInput : listCashflow)
		{
			CashFlowDTO cfd = new CashFlowDTO(hmInput);
			list.add(cfd);
		}
		
		//재배열한다.
		arrange();
		
		//결과값
		List<HashMap<String, Object>> listResult = new ArrayList<HashMap<String, Object>>();
		for(ArrangedCashFlowDTO arrangedDTO : arrangedList)
		{
			HashMap<String, Object> hmResult = arrangedDTO.transferToHashMap();
			listResult.add(hmResult);
		}
		
		return listResult;
	}

	
	
	
	
	private void arrange(){
		
		int size = list.size();

		arrangedList = new ArrayList<ArrangedCashFlowDTO>();
		
		BigDecimal balBeforeAmortization = null;
		
		EntLogger.info("=====================================================================");
		EntLogger.info("<<유효이자율에 의한 이자수익인식 및 이연부대수익상각 계획표 >>");
		
		for(int i=0;i<size;i++){
			
			ArrangedCashFlowDTO arrangedDTO = new ArrangedCashFlowDTO(list.get(i));
			
			
			if(i==0){
				balBeforeAmortization = arrangedDTO.getTotal().abs();
				arrangedDTO.setBalAfterAmortization(balBeforeAmortization);
			}
			
			else {
				
				arrangedDTO.setBalBeforeAmortization(balBeforeAmortization);
				arrangedDTO.setEffectiveInterestRate(irr);
				arrangedDTO.calculate();
				
				balBeforeAmortization = arrangedDTO.getBalAfterAmortization();
				
			}
			EntLogger.info(arrangedDTO.display());
			arrangedList.add(arrangedDTO);
			
		}
		
		EntLogger.info("=====================================================================");
		
	}

}
