package com.emflant.accounting.dto.table;

import com.emflant.accounting.adapter.table.AccountDetail;
import com.emflant.accounting.dto.screen.A01AccountMasterMainInsert01DTO;
import com.emflant.accounting.dto.screen.A03CreditCardPaymentMainInsert01DTO;
import com.emflant.accounting.dto.screen.A04DepositAccountMainInsert01DTO;
import com.emflant.accounting.dto.screen.A05WithdrawAccountMainInsert01DTO;
import com.emflant.accounting.dto.screen.A06CreditCardRepaymentMainInsert01DTO;
import com.emflant.common.EntTransaction;

public class AccountDetailDTO extends AccountDetail{
	
	AccountDetailDTO(){
		
	}

	public AccountDetailDTO(EntTransaction transaction){
		this.slipDate 				= transaction.getSystemDate();
		this.tradeDate 				= transaction.getSystemDate();
		this.tradeTime				= transaction.getSystemTimeMillis();
		this.reckonDate 			= transaction.getSystemDate();
		this.registerUserId			= transaction.getUserId();
		this.registerDateTime 		= transaction.getSystemDateTimeMillis();
		this.lastRegisterUserId		= transaction.getUserId();
		this.lastRegisterDateTime 	= transaction.getSystemDateTimeMillis();
	}
	
	
	public void setAccountDetailDTO(A01AccountMasterMainInsert01DTO a01AccountMasterMainInsert01DTO){
		this.accountNo				= null;
		this.tradeSequence 			= null;
		this.slipNo 				= null;
		this.slipSeq 				= null;
		this.reckonDate 			= a01AccountMasterMainInsert01DTO.getNewDate();
		this.inoutType 				= null;
		this.tradeType 				= null;
		this.cancelType 			= "0";
		this.tradeAmount 			= null;
		this.cashAmount 			= null;
		this.nonCashAmount 			= null;
		this.afterTradeBalance		= null;
		this.afterReckonBalance		= null;
		this.remarks 				= null;
	}
	

	public void setAccountDetailDTO(A03CreditCardPaymentMainInsert01DTO a03CreditCardPaymentMainInsert01DTO){
		this.accountNo 				= a03CreditCardPaymentMainInsert01DTO.getAccountNo();
		this.tradeSequence 			= null;
		this.slipNo 				= null;
		this.slipSeq 				= null;
		this.reckonDate 			= a03CreditCardPaymentMainInsert01DTO.getReckonDate();
		this.inoutType 				= "2";		//2:출금
		this.tradeType 				= "04";		//04:승인
		this.cancelType 			= "0";		//0:정상
		this.tradeAmount 			= a03CreditCardPaymentMainInsert01DTO.getTotalAmount();
		this.cashAmount 			= a03CreditCardPaymentMainInsert01DTO.getCashAmount();
		this.nonCashAmount 			= a03CreditCardPaymentMainInsert01DTO.getTotalAmount().subtract(a03CreditCardPaymentMainInsert01DTO.getCashAmount());
		this.afterTradeBalance		= null;
		this.afterReckonBalance		= null;
		this.remarks 				= a03CreditCardPaymentMainInsert01DTO.getRemarks();
	}
	
	
	public void setAccountDetailDTO(A04DepositAccountMainInsert01DTO a04DepositAccountMainInsert01DTO){
		this.accountNo 				= a04DepositAccountMainInsert01DTO.getAccountNo();
		this.tradeSequence 			= null;
		this.slipNo 				= null;
		this.slipSeq 				= null;
		this.reckonDate 			= a04DepositAccountMainInsert01DTO.getReckonDate();
		this.inoutType 				= "1";
		this.tradeType 				= "02";
		this.cancelType 			= "0";
		this.tradeAmount 			= a04DepositAccountMainInsert01DTO.getTotalAmount();
		this.cashAmount 			= a04DepositAccountMainInsert01DTO.getCashAmount();
		this.nonCashAmount 			= a04DepositAccountMainInsert01DTO.getTotalAmount().
										subtract(a04DepositAccountMainInsert01DTO.getCashAmount());
		this.afterTradeBalance		= null;
		this.afterReckonBalance		= null;
		this.remarks 				= a04DepositAccountMainInsert01DTO.getRemarks();
	}
	
	public void setAccountDetailDTO(A05WithdrawAccountMainInsert01DTO a05WithdrawAccountMainInsert01DTO){
		this.accountNo 				= a05WithdrawAccountMainInsert01DTO.getAccountNo();
		this.tradeSequence 			= null;
		this.slipNo 				= null;
		this.slipSeq 				= null;
		this.reckonDate 			= a05WithdrawAccountMainInsert01DTO.getReckonDate();
		this.inoutType 				= "2";
		this.tradeType 				= "03";
		this.cancelType 			= "0";
		this.tradeAmount 			= a05WithdrawAccountMainInsert01DTO.getTotalAmount();
		this.cashAmount 			= a05WithdrawAccountMainInsert01DTO.getCashAmount();
		this.nonCashAmount 			= a05WithdrawAccountMainInsert01DTO.getTotalAmount().
										subtract(a05WithdrawAccountMainInsert01DTO.getCashAmount());
		this.afterTradeBalance		= null;
		this.afterReckonBalance		= null;
		this.remarks 				= a05WithdrawAccountMainInsert01DTO.getRemarks();
	}
	
	public void setAccountDetailDTO(A06CreditCardRepaymentMainInsert01DTO a06CreditCardRepaymentMainInsert01DTO){
		this.accountNo 				= a06CreditCardRepaymentMainInsert01DTO.getAccountNo();
		this.tradeSequence 			= null;
		this.slipNo 				= null;
		this.slipSeq 				= null;
		this.reckonDate 			= a06CreditCardRepaymentMainInsert01DTO.getReckonDate();
		this.inoutType 				= "1";
		this.tradeType 				= "05";
		this.cancelType 			= "0";
		this.tradeAmount 			= a06CreditCardRepaymentMainInsert01DTO.getTotalAmount();
		this.cashAmount 			= a06CreditCardRepaymentMainInsert01DTO.getCashAmount();
		this.nonCashAmount 			= a06CreditCardRepaymentMainInsert01DTO.getTotalAmount().
										subtract(a06CreditCardRepaymentMainInsert01DTO.getCashAmount());
		this.afterTradeBalance		= null;
		this.afterReckonBalance		= null;
		this.remarks 				= a06CreditCardRepaymentMainInsert01DTO.getRemarks();
	}
	
	
}
