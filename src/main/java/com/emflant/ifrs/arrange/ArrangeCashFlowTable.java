package com.emflant.ifrs.arrange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.emflant.common.EntLogger;

/**
 * ��ȿ�������� ���� ���ڼ����ν� �� �̿��δ���ͻ� ��ȹǥ�� �����Ѵ�.
 * @author nuri
 *
 */
public class ArrangeCashFlowTable {
	
	private BigDecimal irr;
	private List<CashFlowDTO> list;
	private List<ArrangedCashFlowDTO> arrangedList;
	
	/**
	 * ��ȿ�������� �Է��ϴ� ������.
	 * 
	 * @param irr
	 */
	public ArrangeCashFlowTable(BigDecimal irr){
		this.irr = irr;
	}
	
	/**
	 * ��ȿ�������� ���� ���ڼ����ν� �� �̿��δ���ͻ� ��ȹǥ�� �����Ѵ�.
	 * @param listCashflow
	 * @return
	 */
	public List<HashMap<String, Object>> arrange(List<HashMap<String, Object>> listCashflow){
		
		//�Է°�
		list = new ArrayList<CashFlowDTO>();
		for(HashMap<String, Object> hmInput : listCashflow)
		{
			CashFlowDTO cfd = new CashFlowDTO(hmInput);
			list.add(cfd);
		}
		
		//��迭�Ѵ�.
		arrange();
		
		//�����
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
		EntLogger.info("<<��ȿ�������� ���� ���ڼ����ν� �� �̿��δ���ͻ� ��ȹǥ >>");
		
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
