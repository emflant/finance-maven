package com.emflant.interest.split;

import java.util.List;

import com.emflant.common.EntLogger;
import com.emflant.interest.data.MakeTableAccount;
import com.emflant.interest.data.ReadTable;
import com.emflant.interest.data.TableAccountDTO;
import com.emflant.interest.dto.InterestCalculateInputDTO;
import com.emflant.interest.dto.InterestCalculateResultDTO;

/**
 * �����ڿ� InterestDTO �߰��Ѵ�.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 ���� 4:43:06
 */
public class Interest {
	
	public void execute(InterestCalculateInputDTO interestCalculateInputDTO){
		EntLogger.debug("���ڰ�� ����.");
		
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