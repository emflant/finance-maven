package com.emflant.interest.split;

import java.util.List;

import com.emflant.common.EntLogger;
import com.emflant.interest.data.MakeTableAccount;
import com.emflant.interest.data.ReadTable;
import com.emflant.interest.data.TableAccountDTO;
import com.emflant.interest.dto.InterestCalculateInputDTO;
import com.emflant.interest.dto.InterestCalculateResultDTO;

/**
 * 생성자에 InterestDTO 추가한다.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 오후 4:43:06
 */
public class Interest {
	
	public void execute(InterestCalculateInputDTO interestCalculateInputDTO){
		EntLogger.debug("이자계산 시작.");
		
		MakeTableAccount makeTableAccount = new MakeTableAccount();
		
		
		List<TableAccountDTO> listAccount = makeTableAccount.make(ReadTable.getConnect());
		makeTableAccount.display();
		TableAccountDTO tableAccountDTO = listAccount.get(0);
		
		
		ArrangeTransactions arrangeTransactions = new ArrangeTransactions(tableAccountDTO);
		List<InterestCalculateResultDTO> list = arrangeTransactions.arrange();
		arrangeTransactions.display();
		
		new SplitExecute(arrangeTransactions, list).execute(ReadTable.getConnect());
	}
	
}