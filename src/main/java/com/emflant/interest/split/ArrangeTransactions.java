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
 * 거래내역을 재배열한다.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 오후 4:43:06
 */
public class ArrangeTransactions {

	private List<InterestCalculateResultDTO> arrangedList;
	private TableAccountDTO tableAccountDTO;
	
	public ArrangeTransactions(TableAccountDTO tableAccountDTO){
		this.tableAccountDTO = tableAccountDTO;
	}
	
	/**
	 * 일별잔액명세 데이터를 읽어온다.
	 * 쿼리문으로 읽어올때 이자계산기준일자이전까지 조회하지말고
	 * 이자계산기준일포함한 내역을 가져온다.
	 */
	public List<InterestCalculateResultDTO> arrange(){
		
		EntLogger.debug("일별잔액명세 데이터를 읽어온다.");
		MakeTableDailyBalance mt = new MakeTableDailyBalance();
		List<TableDailyBalanceDTO> list = mt.make();
		mt.display();
		
		return arrange(list);
	}
	
	public List<InterestCalculateResultDTO> arrange(List<TableDailyBalanceDTO> list){
		
		
		//fromDate 이자계산시작일자 = 입력없으면 최종이수일자
		//toDate 이자계산기준일자 = 입력없으면 영업일자
		return arrange(list, new EntDate("20061228"), new EntDate("20100123"));
	}
	
	public List<InterestCalculateResultDTO> arrange(List<TableDailyBalanceDTO> list, EntDate fromDate, EntDate toDate){
				
		EntLogger.debug("일별잔액명세 데이터에 필요한 로우를 덧붙인다.");
		
		//이자계산시작일자가 더 작을때 거래일자 추가하고,
		//마감잔액은 일별잔액명세 첫로우의 개시잔액.
		if(list.get(0).getTradeDate().compareTo(fromDate) == 1){
			TableDailyBalanceDTO tableDailyBalanceDTO = new TableDailyBalanceDTO();
			tableDailyBalanceDTO.setTradeDate(fromDate);
			tableDailyBalanceDTO.setCloseBalance(list.get(0).getOpenBalance());
			list.add(0,tableDailyBalanceDTO);
		}
		
		//이자계산종료일자가 더 클때 거래일자만 필요하다.
		if(list.get(list.size()-1).getTradeDate().compareTo(toDate)==-1){
			TableDailyBalanceDTO tableDailyBalanceDTO = new TableDailyBalanceDTO();
			tableDailyBalanceDTO.setTradeDate(toDate);
			list.add(tableDailyBalanceDTO);
		}
		
		return make(list);
	}
	
	/**
	 * 실제로 이자계산내역을 배열한다.
	 * @param list
	 * @return
	 */
	private List<InterestCalculateResultDTO> make(List<TableDailyBalanceDTO> list){
		EntLogger.debug("실제로 이자계산내역을 배열한다.");
		arrangedList = new ArrayList<InterestCalculateResultDTO>();
		
		int i=0;
		
		for(TableDailyBalanceDTO tableDailyBalanceDTO : list){
			InterestCalculateResultDTO interestCalculateResultDTO = new InterestCalculateResultDTO();

			//종료일자를 셋팅한다.
			if(i > 0){
				arrangedList.get(i-1).setToDate(tableDailyBalanceDTO.getTradeDate());
			}
			interestCalculateResultDTO.setFromDate(tableDailyBalanceDTO.getTradeDate());
			interestCalculateResultDTO.setBalance(tableDailyBalanceDTO.getCloseBalance());
			
			//InterestCalculateResultDTO에 기본계좌정보를 넣어준다.
			tableAccountDTO.put(interestCalculateResultDTO);
			
			arrangedList.add(interestCalculateResultDTO);
			i++;
		}
		
		arrangedList.remove(i-1);
		return arrangedList;
	}
	
	public void display(){
		
		String pattern = "%2s%12s%12s%7s%12s%8s%8s%8s%10s%8s";
		
		EntLogger.info("────────────────────────────────────────────────────────────────────────────────────────");
		EntLogger.info(String.format(pattern
				,"no","from_date","to_date","days","balance","rate","inctxrt","rstxrt","customer","tax_cf"));
		EntLogger.info("────────────────────────────────────────────────────────────────────────────────────────");
		
		int cnt = 1;
		for(InterestCalculateResultDTO interestCalculateResultDTO : arrangedList){
			interestCalculateResultDTO.display(pattern,cnt++);
		}
		EntLogger.info("────────────────────────────────────────────────────────────────────────────────────────");
	}

}