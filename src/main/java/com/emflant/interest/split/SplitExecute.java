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
 * @created 09-3-2011 ���� 4:43:08
 */
public class SplitExecute {
	
	private ArrangeTransactions arrangeTransactions;
	private List<InterestCalculateResultDTO> list;
	
	public SplitExecute(ArrangeTransactions arrangeTransactions, List<InterestCalculateResultDTO> list){
		this.arrangeTransactions = arrangeTransactions;
		this.list = list;
	}

	public void execute(XSSFWorkbook wb){
		EntLogger.debug("���ڰ�걸���� ������ ����.");
		SplitRuleByProduct r = new SplitRuleByProduct();
		List<MakeTable<TableSplitDTO>> ru = r.rule();
		
		for(MakeTable<TableSplitDTO> makeSplitTable : ru){
			split(makeSplitTable, wb);
			//���Ļ������. ���ڰ�곻�� ��ȭ�� �������� �Ѱ���.
			arrangeTransactions.display();
		}
		

		//�������Ϸ�� �����Ϳ� �ϼ������ �ǽ��Ѵ�.
		//���߿� �������� ����ϴ� ���� �߰��ؾ� �����ս��� ������.
		EntLogger.debug("�������Ϸ�� �����Ϳ� �ϼ������ �ǽ��Ѵ�.");
		for(InterestCalculateResultDTO interestCalculateDetailDTO : list){
			int days = interestCalculateDetailDTO.getFromDate()
			.betweenDate(interestCalculateDetailDTO.getToDate());
			
			interestCalculateDetailDTO.setTermDays(days);
		}
		
		arrangeTransactions.display();
		EntLogger.debug("���ڰ�걸���� ������ ��.");
		
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
				
				//Logger.debug((j+1)+"��° : "+fromDate+"~"+toDate+", "+interestCalculateResultDTO.getFromDate()+"~"+interestCalculateResultDTO.getToDate());
				if(interestCalculateResultDTO.getFromDate().compareTo(fromDate) >= 0
						&& interestCalculateResultDTO.getFromDate().compareTo(toDate) < 0){
					
					//�ٲ�ºκ�
					if(!tableDTO.put(interestCalculateResultDTO)){
						continue;
					}
					
					//Logger.debug((j+1)+"��° : "+fromDate+"~"+toDate+", "+interestCalculateResultDTO.getFromDate()+"~"+interestCalculateResultDTO.getToDate());
					
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