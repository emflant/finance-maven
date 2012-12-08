package com.emflant.accounting.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.screen.A04DepositAccountMainInsert01DTO;
import com.emflant.accounting.dto.screen.A05WithdrawAccountMainInsert01DTO;
import com.emflant.accounting.dto.table.AccountMasterDTO;
import com.emflant.accounting.main.component.EntJButton;
import com.emflant.accounting.main.component.EntJComboBox;
import com.emflant.accounting.main.component.EntJTable;
import com.emflant.accounting.main.component.EntJTextFieldForAmount;
import com.emflant.accounting.main.component.EntJTextFieldForDate;
import com.emflant.accounting.main.component.EntJTextFieldForRemarks;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntCommon;
import com.emflant.common.EntDate;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;

public class A07TransferAccountsMain extends EntScreenMain {

	private JPanel centerPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private JLabel lbWithdrawAccount;
	private EntJComboBox cbWithdrawAccount;
	
	private JLabel lbBalance;
	private EntJTextFieldForAmount tfBalance;
	
	private JLabel lbTradeDate;
	private EntJTextFieldForDate tfTradeDate;
	
	private JLabel lbDepositAccount;
	private EntJComboBox cbDepositAccount;
	
	private JLabel lbTradeAmount;
	private EntJTextFieldForAmount tfTradeAmount;
	
	private JLabel lbCashAmount;
	private EntJTextFieldForAmount tfCashAmount;
	
	private JLabel lbRemarks;
	private EntJTextFieldForRemarks tfRemarks;
	
	
	private EntJButton btnInsert;
	private EntJTable tbAccountDetail;
	
	
	public A07TransferAccountsMain(JFrame frame, EntBean entBean, String userId){
		this.frame = frame;
		this.entBean = entBean;
		this.userId = userId;
	}

	@Override
	public void initScreen() {

		this.northPanel = new JPanel();
		this.northPanel.setBackground(Color.WHITE);
		this.northPanel.setLayout(new BoxLayout(this.northPanel, BoxLayout.Y_AXIS));
		
		this.panel1	= new JPanel();
		this.panel1.setBackground(Color.WHITE);
		this.panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.panel2	= new JPanel();
		this.panel2.setBackground(Color.WHITE);
		this.panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.centerPanel = new JPanel();
		this.centerPanel.setBackground(Color.WHITE);
		this.centerPanel.setLayout(new BorderLayout());
		
		this.southPanel = new JPanel();
		this.southPanel.setBackground(Color.WHITE);
		this.southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.lbWithdrawAccount = new JLabel("출금계좌");
		this.cbWithdrawAccount = new EntJComboBox();
		this.cbWithdrawAccount.addActionListener(new CbAccountChangeListener());
		
		this.lbBalance = new JLabel("       잔액");
		this.tfBalance = new EntJTextFieldForAmount(10);
		this.tfBalance.setEnabled(false);
		
		this.lbTradeDate = new JLabel("       기산일자");
		this.tfTradeDate = new EntJTextFieldForDate(7);
		
		this.lbDepositAccount = new JLabel("입금계좌");
		this.cbDepositAccount = new EntJComboBox();
		
		this.lbTradeAmount = new JLabel("이체금액");
		this.tfTradeAmount = new EntJTextFieldForAmount(7);

		
		this.lbCashAmount = new JLabel("현금");
		this.tfCashAmount = new EntJTextFieldForAmount(7);
		
		this.lbRemarks = new JLabel("적요");
		
		this.tfRemarks = new EntJTextFieldForRemarks(7);
		
		this.btnInsert = new EntJButton("등록");
		this.btnInsert.addActionListener(new InsertButtonListener());

		this.tbAccountDetail = new EntJTable();
		
		//그리드의 헤더정보를 정의한다.
		this.tbAccountDetail.entAddTableHeader("trade_sequence", "#", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("format_reckon_date", "거래일자", JLabel.CENTER, 100);
		this.tbAccountDetail.entAddTableHeader("trade_type_name", "종류", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("cancel_type_name", "취소", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("format_trade_amount", "거래금액", JLabel.RIGHT, 120);
		this.tbAccountDetail.entAddTableHeader("format_after_reckon_balance", "잔액", JLabel.RIGHT, 120);
		this.tbAccountDetail.entAddTableHeader("remarks", "적요", JLabel.LEFT, 260);



		this.panel1.add(lbWithdrawAccount);
		this.panel1.add(cbWithdrawAccount);
		this.panel1.add(lbBalance);
		//this.panel1.add(tfBalance);
		tfBalance.addPanel(this.panel1);
		this.panel1.add(lbTradeDate);
		tfTradeDate.addPanel(this.panel1);
		this.panel2.add(lbDepositAccount);
		this.panel2.add(cbDepositAccount);

		this.panel2.add(lbTradeAmount);
		this.tfTradeAmount.addPanel(this.panel2);
		this.panel2.add(lbCashAmount);
		this.tfCashAmount.addPanel(this.panel2);
		
		this.panel2.add(lbRemarks);
		//this.panel2.add(tfRemarks);
		tfRemarks.addPanel(this.panel2);
		btnInsert.addPanel(this.panel2);
		//this.panel2.add(btnInsert);
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbAccountDetail));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle("[A07] 은행계좌이체");
		
	}
	


	
	public void insert(){

		this.transactionKinds = "INSERT";
		
		if(this.tfTradeAmount.getValue().equals("0")){
			showMessageDialog("이체금액이 0입니다.");
			return;
		}
		
		String strWithdrawAccountNo = this.cbWithdrawAccount.entGetCodeOfSelectedItem();
		BigDecimal bdTradeAmount = new BigDecimal(this.tfTradeAmount.getValue());
		BigDecimal bdCashAmount = new BigDecimal(this.tfCashAmount.getValue());
		String strDepositAccountNo = this.cbDepositAccount.entGetCodeOfSelectedItem();

		if(strWithdrawAccountNo.equals(strDepositAccountNo)){
			showMessageDialog("출금계좌와 입금계좌가 동일합니다.");
			return;
		}
		
		int nResult = showConfirmDialog("계좌간 이체 처리하시겠습니까?");
		if(nResult != 0) return;
		
		//은행계좌출금
		A05WithdrawAccountMainInsert01DTO inputDTO1 = new A05WithdrawAccountMainInsert01DTO();
		inputDTO1.setAccountNo(strWithdrawAccountNo);
		inputDTO1.setReckonDate(this.tfTradeDate.getValue());
		inputDTO1.setTotalAmount(bdTradeAmount);
		inputDTO1.setCashAmount(bdCashAmount);
		inputDTO1.setRemarks(this.tfRemarks.getText());
		
		//은행계좌입금
		A04DepositAccountMainInsert01DTO inputDTO2 = new A04DepositAccountMainInsert01DTO();
		inputDTO2.setAccountNo(strDepositAccountNo);
		inputDTO2.setReckonDate(this.tfTradeDate.getValue());
		inputDTO2.setTotalAmount(bdTradeAmount);
		inputDTO2.setCashAmount(bdCashAmount);
		inputDTO2.setRemarks(this.tfRemarks.getText());
		inputDTO2.setLinkType("0");
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.setIsOneSlip(true);
		businessDTO.addTransaction("A05201", inputDTO1);
		businessDTO.addTransaction("A04201", inputDTO2);
		
		transactionCommitAtOnce(businessDTO);
		
	}
	

	
	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A05101", this.userId);
		businessDTO.addTransaction("A04101", this.userId);
		
		
		transactionCommitOneByOne(businessDTO);		
	}


	@Override
	public void search() {

		this.transactionKinds = "SEARCH";
		
		String strAccountNo = this.cbWithdrawAccount.entGetCodeOfSelectedItem();
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A02103", strAccountNo);
		businessDTO.addTransaction("A02102", strAccountNo);
		transactionCommitOneByOne(businessDTO);
		
	}
	
	public void changeCbAccount(){
		search();
	}
	
	
	
	class CbAccountChangeListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			changeCbAccount();
		}
		
	}
	
	class InsertButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			insert();
		}
	}

	
	
	@Override
	public void localCallback(EntBusiness businessDTO) {
		// TODO Auto-generated method stub

		if(this.transactionKinds.equals("BIND_COMBOBOX")){
			afterbindComboBox(businessDTO);
		}
		else if(this.transactionKinds.equals("SEARCH")){
			afterSearch(businessDTO);
		}
		else if(this.transactionKinds.equals("INSERT")){
			search();
		}
		
	}
	
	public void afterbindComboBox(EntBusiness businessDTO){

		EntHashList ehlWithdrawAccount = (EntHashList) businessDTO.getResultByTransactionIndex(0);
		this.cbWithdrawAccount.entSetEntHashList(ehlWithdrawAccount);
		
		EntHashList ehlDepositAccount = (EntHashList) businessDTO.getResultByTransactionIndex(1);
		this.cbDepositAccount.entSetEntHashList(ehlDepositAccount);
	}
	
	public void afterSearch(EntBusiness businessDTO){
		
		DefaultTableModel model = (DefaultTableModel)businessDTO.getResultByTransactionIndex(0);
		this.tbAccountDetail.entSetTableModel(model);
		
		AccountMasterDTO accountMaster = (AccountMasterDTO)businessDTO.getResultByTransactionIndex(1);
		this.tfBalance.setValue(accountMaster.getBalance());
	}


}
