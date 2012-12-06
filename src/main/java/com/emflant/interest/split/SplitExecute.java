package com.emflant.interest.split;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;
import com.emflant.interest.data.MakeTable;
import com.emflant.interest.data.TableSplitDTO;
import com.emflant.interest.dto.InterestCalculateResultDTO;

/**
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 오후 4:43:08
 */
public class SplitExecute {
	
	private ArrangeTransactions arrangeTransactions;
	private List<InterestCalculateResultDTO> list;
	
	public SplitExecute(ArrangeTransactions arrangeTransactions, List<InterestCalculateResultDTO> list){
		this.arrangeTransactions = arrangeTransactions;
		this.list = list;
	}

	public void execute(XSSFWorkbook wb){
		EntLogger.debug("이자계산구간을 나누기 시작.");
		SplitRuleByProduct r = new SplitRuleByProduct();
		List<MakeTable<TableSplitDTO>> ru = r.rule();
		
		for(MakeTable<TableSplitDTO> makeSplitTable : ru){
			split(makeSplitTable, wb);
			//추후삭제요망. 이자계산내역 변화를 보기위해 한것임.
			arrangeTransactions.display();
		}
		

		//구간계산완료된 데이터에 일수계산을 실시한다.
		//나중에 지급이자 계산하는 곳에 추가해야 퍼포먼스가 좋을듯.
		EntLogger.debug("구간계산완료된 데이터에 일수계산을 실시한다.");
		for(InterestCalculateResultDTO interestCalculateDetailDTO : list){
			int days = interestCalculateDetailDTO.getFromDate()
			.betweenDate(interestCalculateDetailDTO.getToDate());
			
			interestCalculateDetailDTO.setTermDays(days);
		}
		
		arrangeTransactions.display();
		EntLogger.debug("이자계산구간을 나누기 끝.");
		
	}
	
	
	
	private void split(MakeTable<TableSplitDTO> makeSplitTable, XSSFWorkbook wb){
		
		List<TableSplitDTO> tableDTOList = makeSplitTable.make(wb);
		makeSplitTable.display();
		
		int nSize = tableDTOList.size();
		
		for(int i=0;i<nSize;i++){
			TableSplitDTO tableDTO = tableDTOList.get(i);
			
			EntDate fromDate = tableDTO.getFromDate();
			EntDate toDate = tableDTO.getToDate();
			
			
			for(int j=0;j<list.size();j++){	
				InterestCalculateResultDTO interestCalculateResultDTO = list.get(j);
				
				//Logger.debug((j+1)+"번째 : "+fromDate+"~"+toDate+", "+interestCalculateResultDTO.getFromDate()+"~"+interestCalculateResultDTO.getToDate());
				if(interestCalculateResultDTO.getFromDate().compareTo(fromDate) >= 0
						&& interestCalculateResultDTO.getFromDate().compareTo(toDate) < 0){
					
					//바뀌는부분
					if(!tableDTO.put(interestCalculateResultDTO)){
						continue;
					}
					
					//Logger.debug((j+1)+"번째 : "+fromDate+"~"+toDate+", "+interestCalculateResultDTO.getFromDate()+"~"+interestCalculateResultDTO.getToDate());
					
					if(interestCalculateResultDTO.getToDate().compareTo(toDate) == 1){
						
						InterestCalculateResultDTO newInterestCalculateDetailDTO = new InterestCalculateResultDTO();
						newInterestCalculateDetailDTO.setFromDate(toDate);
						newInterestCalculateDetailDTO.setToDate(interestCalculateResultDTO.getToDate());
						newInterestCalculateDetailDTO.setBalance(interestCalculateResultDTO.getBalance());
						newInterestCalculateDetailDTO.setInterestRate(interestCalculateResultDTO.getInterestRate());
						newInterestCalculateDetailDTO.setIncomeTaxRate(interestCalculateResultDTO.getIncomeTaxRate());
						newInterestCalculateDetailDTO.setResidentTaxRate(interestCalculateResultDTO.getResidentTaxRate());
						newInterestCalculateDetailDTO.setCustomer(interestCalculateResultDTO.getCustomer());
						newInterestCalculateDetailDTO.setTaxClassification(interestCalculateResultDTO.getTaxClassification());
						list.add(j+1,newInterestCalculateDetailDTO);
						
						interestCalculateResultDTO.setToDate(toDate);
						
						break;
					}
				}
			}
		}
	}
	
}