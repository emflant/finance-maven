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
	 * ��ȿ������ �� �̿��δ���ͻ󰢰�ȹǥ ����� main
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ZIfrsArrangeExecute().execute1();
		
	}
	
	public void execute1(){
		
		//�����帧ǥ�� �����.
		MakeCashFlowTable mcft = new MakeCashFlowTable();
		List<HashMap<String, Object>> list = mcft.read2();
		
		List<BigDecimal> listIrr = new ArrayList<BigDecimal>();
		for(HashMap<String, Object> hmInput : list){
			BigDecimal total = ((BigDecimal)hmInput.get("bal")).
						add((BigDecimal)hmInput.get("interest")).
						add((BigDecimal)hmInput.get("fee"));
			listIrr.add(total);
		}
		
		//��ȿ������ ���ϱ�
		EirCalculate cc = new EirCalculate();
		BigDecimal eir = cc.calculate(listIrr);
		EntLogger.info("��ȿ������ : "+eir.toPlainString());
		
		
		//�̿��δ���ͻ󰢰�ȹǥ �����
		ArrangeCashFlowTable asft = new ArrangeCashFlowTable(eir);
		asft.arrange(list);
	}
	


}
