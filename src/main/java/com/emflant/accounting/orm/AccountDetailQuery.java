package com.emflant.accounting.orm;

import com.emflant.accounting.dto.table.AccountDetailDTO;
import com.emflant.common.EntCommon;

public class AccountDetailQuery {


	public StringBuilder select(String accountNo){
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT ");
		//sb.append("(SELECT account_type_name FROM account WHERE account_type = trade_type) as trade_type_name ");
		sb.append("  (SELECT code_name FROM code_detail WHERE code_type = '01' and code = trade_type) as trade_type_name ");
		sb.append(", (SELECT code_name FROM code_detail WHERE code_type = '02' and code = inout_type) as inout_type_name ");
		sb.append(", (SELECT code_name FROM code_detail WHERE code_type = '11' and code = cancel_type) as cancel_type_name ");
		sb.append(", DATE_FORMAT(reckon_date,'%Y-%m-%d') AS format_reckon_date, format(trade_amount,0) as format_trade_amount, format(after_trade_balance,0) as format_after_balance, format(after_reckon_balance,0) as format_after_reckon_balance, trade_amount, trade_sequence, remarks, trade_date, DATE_FORMAT(trade_date,'%Y-%m-%d') AS format_trade_date ");
		sb.append(" FROM account_detail WHERE account_no = ");
		sb.append(EntCommon.convertQuery(accountNo));
		sb.append(" ORDER BY reckon_date, trade_sequence");

		return sb;
	}	
	
	public StringBuilder selectAccountDetailByKey(String accountNo, int tradeSequence){
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT * ");
		sb.append(" FROM account_detail WHERE account_no = ");
		sb.append(EntCommon.convertQuery(accountNo));
		sb.append(" AND trade_sequence = ");
		sb.append(tradeSequence);
		
		return sb;
	}	
	
	public StringBuilder selectAccountDetailByRegisterDateTime(String registerUserId, String registerDateTime){
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT * ");
		sb.append(" FROM account_detail WHERE register_user_id = ");
		sb.append(EntCommon.convertQuery(registerUserId));
		sb.append(" AND register_date_time = ");
		sb.append(EntCommon.convertQuery(registerDateTime));
		
		return sb;
	}	

	public StringBuilder delete(AccountDetailDTO accountDetail)  {
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("DELETE FROM account_detail WHERE account_no = ");
		sb.append(EntCommon.convertQuery(accountDetail.getAccountNo()));
		sb.append(" AND trade_sequence = ");
		sb.append(accountDetail.getTradeSequence());

		return sb;
	}
	
}
