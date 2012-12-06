package com.emflant.interest.split;

import java.util.ArrayList;
import java.util.List;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;
import com.emflant.interest.data.MakeTableDailyBalance;
import com.emflant.interest.data.TableAccountDTO;
import com.emflant.interest.data.TableDailyBalanceDTO;
import com.emflant.interest.dto.InterestCalculateResultDTO;

/**
 * �ŷ������� ��迭�Ѵ�.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 ���� 4:43:06
 */
public class ArrangeTransactions {

	private List<InterestCalculateResultDTO> arrangedList;
	private TableAccountDTO tableAccountDTO;
	
	public ArrangeTransactions(TableAccountDTO tableAccountDTO){
		this.tableAccountDTO = tableAccountDTO;
	}
	
	/**
	 * �Ϻ��ܾ׸� �����͸� �о�´�.
	 * ���������� �о�ö� ���ڰ����������������� ��ȸ��������
	 * ���ڰ������������� ������ �����´�.
	 */
	public List<InterestCalculateResultDTO> arrange(){
		
		EntLogger.debug("�Ϻ��ܾ׸� �����͸� �о�´�.");
		MakeTableDailyBalance mt = new MakeTableDailyBalance();
		List<TableDailyBalanceDTO> list = mt.make();
		mt.display();
		
		return arrange(list);
	}
	
	public List<InterestCalculateResultDTO> arrange(List<TableDailyBalanceDTO> list){
		
		
		//fromDate ���ڰ��������� = �Է¾����� �����̼�����
		//toDate ���ڰ��������� = �Է¾����� ��������
		return arrange(list, new EntDate("20061228"), new EntDate("20100123"));
	}
	
	public List<InterestCalculateResultDTO> arrange(List<TableDailyBalanceDTO> list, EntDate fromDate, EntDate toDate){
				
		EntLogger.debug("�Ϻ��ܾ׸� �����Ϳ� �ʿ��� �ο츦 �����δ�.");
		
		//���ڰ��������ڰ� �� ������ �ŷ����� �߰��ϰ�,
		//�����ܾ��� �Ϻ��ܾ׸� ù�ο��� �����ܾ�.
		if(list.get(0).getTradeDate().compareTo(fromDate) == 1){
			TableDailyBalanceDTO tableDailyBalanceDTO = new TableDailyBalanceDTO();
			tableDailyBalanceDTO.setTradeDate(fromDate);
			tableDailyBalanceDTO.setCloseBalance(list.get(0).getOpenBalance());
			list.add(0,tableDailyBalanceDTO);
		}
		
		//���ڰ���������ڰ� �� Ŭ�� �ŷ����ڸ� �ʿ��ϴ�.
		if(list.get(list.size()-1).getTradeDate().compareTo(toDate)==-1){
			TableDailyBalanceDTO tableDailyBalanceDTO = new TableDailyBalanceDTO();
			tableDailyBalanceDTO.setTradeDate(toDate);
			list.add(tableDailyBalanceDTO);
		}
		
		return make(list);
	}
	
	/**
	 * ������ ���ڰ�곻���� �迭�Ѵ�.
	 * @param list
	 * @return
	 */
	private List<InterestCalculateResultDTO> make(List<TableDailyBalanceDTO> list){
		EntLogger.debug("������ ���ڰ�곻���� �迭�Ѵ�.");
		arrangedList = new ArrayList<InterestCalculateResultDTO>();
		
		int i=0;
		
		for(TableDailyBalanceDTO tableDailyBalanceDTO : list){
			InterestCalculateResultDTO interestCalculateResultDTO = new InterestCalculateResultDTO();

			//�������ڸ� �����Ѵ�.
			if(i > 0){
				arrangedList.get(i-1).setToDate(tableDailyBalanceDTO.getTradeDate());
			}
			interestCalculateResultDTO.setFromDate(tableDailyBalanceDTO.getTradeDate());
			interestCalculateResultDTO.setBalance(tableDailyBalanceDTO.getCloseBalance());
			
			//InterestCalculateResultDTO�� �⺻���������� �־��ش�.
			tableAccountDTO.put(interestCalculateResultDTO);
			
			arrangedList.add(interestCalculateResultDTO);
			i++;
		}
		
		arrangedList.remove(i-1);
		return arrangedList;
	}
	
	public void display(){
		
		String pattern = "%2s%12s%12s%7s%12s%8s%8s%8s%10s%8s";
		
		EntLogger.info("��������������������������������������������������������������������������������������������������������������������������������������������������������������������������������");
		EntLogger.info(String.format(pattern
				,"no","from_date","to_date","days","balance","rate","inctxrt","rstxrt","customer","tax_cf"));
		EntLogger.info("��������������������������������������������������������������������������������������������������������������������������������������������������������������������������������");
		
		int cnt = 1;
		for(InterestCalculateResultDTO interestCalculateResultDTO : arrangedList){
			interestCalculateResultDTO.display(pattern,cnt++);
		}
		EntLogger.info("��������������������������������������������������������������������������������������������������������������������������������������������������������������������������������");
	}

}