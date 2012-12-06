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
 * 상품별 이자계산구간 나누는 기준 정의한다.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 오후 4:43:08
 */
public class SplitRuleByProduct {

	private boolean changeCustomer;

	/**
	 * 이자계산구간 나누는 기준을 정의한다.
	 */
	public List<MakeTable<TableSplitDTO>> rule(){
		EntLogger.debug("상품에따라 이자계산구간방법들을 정의한다.");
		//XSSFWorkbook wb = ReadTable.getConnect();
		
		List<MakeTable<TableSplitDTO>> list = new ArrayList<MakeTable<TableSplitDTO>>();
		list.add(new MakeTableInterestRate());
		list.add(new MakeTableCustomer());
		list.add(new MakeTableTaxRate());
		
/*		MakeTableCustomer makeTableCustomer = new MakeTableCustomer(wb);
		int nSize = makeTableCustomer.getListSize();
		
		//고객변경이 있을때만 구간나눈다.
		if(nSize > 1){
			Logger.debug("고객변경내역이 존재한다.");
			this.changeCustomer = true;
			list.add(makeTableCustomer);
		}*/
		
		//list.add(new MakeTableTaxRate(wb));
		//list.add(new SplitTransactionsCustomer());
		
		return list;
	}
	
	/**
	 * 고객변경되었는지 
	 * @return
	 */
	public boolean isChangeCustomer(){
		return changeCustomer;
	}

}