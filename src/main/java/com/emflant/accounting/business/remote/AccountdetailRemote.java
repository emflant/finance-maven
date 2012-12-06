package com.emflant.accounting.business.remote;

import com.emflant.common.EntException;
import com.emflant.accounting.dto.screen.A03CreditCardPaymentMainInsert01DTO;
import com.emflant.accounting.dto.screen.A04DepositAccountMainInsert01DTO;
import com.emflant.accounting.dto.screen.A05WithdrawAccountMainInsert01DTO;
import com.emflant.accounting.dto.screen.A06CreditCardRepaymentMainSelect01DTO;
import com.emflant.accounting.dto.screen.A06CreditCardRepaymentMainInsert01DTO;
import com.emflant.accounting.dto.screen.A10CashInOutTradeMainInsert01DTO;


public interface AccountdetailRemote {
	void select(String input) throws EntException;
	void creditCardPayment(A03CreditCardPaymentMainInsert01DTO input) throws EntException;
	void deposit(A04DepositAccountMainInsert01DTO input) throws EntException;
	void withdraw(A05WithdrawAccountMainInsert01DTO input) throws EntException;
	void selectCreditCardRepayment(A06CreditCardRepaymentMainSelect01DTO input) throws EntException;
	void creditCardRepayment(A06CreditCardRepaymentMainInsert01DTO input) throws EntException;
	void cashInOutTrade(A10CashInOutTradeMainInsert01DTO input) throws EntException;
	void httpUrlUpdateMoneyStatus(String input) throws EntException;
	void httpUrlUpdateMoneyStatus2(String input) throws EntException;
}
