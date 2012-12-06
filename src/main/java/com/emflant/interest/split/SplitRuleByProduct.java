package com.emflant.interest.split;

import java.util.ArrayList;
import java.util.List;


import com.emflant.common.EntLogger;
import com.emflant.interest.data.MakeTable;
import com.emflant.interest.data.MakeTableCustomer;
import com.emflant.interest.data.MakeTableInterestRate;
import com.emflant.interest.data.MakeTableTaxRate;
import com.emflant.interest.data.TableSplitDTO;

/**
 * ��ǰ�� ���ڰ�걸�� ������ ���� �����Ѵ�.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 ���� 4:43:08
 */
public class SplitRuleByProduct {

	private boolean changeCustomer;

	/**
	 * ���ڰ�걸�� ������ ������ �����Ѵ�.
	 */
	public List<MakeTable<TableSplitDTO>> rule(){
		EntLogger.debug("��ǰ������ ���ڰ�걸��������� �����Ѵ�.");
		//XSSFWorkbook wb = ReadTable.getConnect();
		
		List<MakeTable<TableSplitDTO>> list = new ArrayList<MakeTable<TableSplitDTO>>();
		list.add(new MakeTableInterestRate());
		list.add(new MakeTableCustomer());
		list.add(new MakeTableTaxRate());
		
/*		MakeTableCustomer makeTableCustomer = new MakeTableCustomer(wb);
		int nSize = makeTableCustomer.getListSize();
		
		//�������� �������� ����������.
		if(nSize > 1){
			Logger.debug("�����泻���� �����Ѵ�.");
			this.changeCustomer = true;
			list.add(makeTableCustomer);
		}*/
		
		//list.add(new MakeTableTaxRate(wb));
		//list.add(new SplitTransactionsCustomer());
		
		return list;
	}
	
	/**
	 * ������Ǿ����� 
	 * @return
	 */
	public boolean isChangeCustomer(){
		return changeCustomer;
	}

}