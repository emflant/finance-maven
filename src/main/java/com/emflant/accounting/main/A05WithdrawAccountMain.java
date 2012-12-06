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

import com.emflant.accounting.dto.screen.A05WithdrawAccountMainInsert01DTO;
import com.emflant.accounting.dto.table.AccountMasterDTO;
import com.emflant.accounting.dto.table.SlipMasterDTO;
import com.emflant.accounting.main.component.EntJButton;
import com.emflant.accounting.main.component.EntJComboBox;
import com.emflant.accounting.main.component.EntJTable;
import com.emflant.accounting.main.component.EntJTextFieldForAmount;
import com.emflant.accounting.main.component.EntJTextFieldForDate;
import com.emflant.accounting.main.component.EntJTextFieldForRemarks;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;
/**
 * ������� ȭ��
 * @author home
 *
 */
public class A05WithdrawAccountMain extends EntScreenMain {

	private JPanel centerPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private JLabel lbAccount;
	private EntJComboBox cbAccount;
	
	private JLabel lbBalance;
	private EntJTextFieldForAmount tfBalance;
	
	private JLabel lbTradeDate;
	private EntJTextFieldForDate tfTradeDate;
	
	private EntJComboBox cbLinkType;
	
	private EntJComboBox cbTradeType;
	
	private JLabel lbTradeAmount;
	private EntJTextFieldForAmount tfTradeAmount;
	private JLabel lbCashAmount;
	private EntJTextFieldForAmount tfCashAmount;
	
	private JLabel lbRemarks;
	private EntJTextFieldForRemarks tfRemarks;
	
	
	private EntJButton btnInsert;
	private EntJTable tbAccountDetail;
	
	
	public A05WithdrawAccountMain(JFrame frame, EntBean entBean, String userId){
		this.frame = frame;
		this.entBean = entBean;
		this.userId = userId;
	}

	@Override
	public void initScreen() {
		
		this.cbLinkType = new EntJComboBox();
		this.cbLinkType.addActionListener(new CbLinkTypeChangeListener());
		
		this.cbTradeType = new EntJComboBox();

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
		
		this.lbAccount = new JLabel("����");
		this.cbAccount = new EntJComboBox();
		this.cbAccount.addActionListener(new CbAccountChangeListener());
		
		this.lbBalance = new JLabel("       �ܾ�");
		this.tfBalance = new EntJTextFieldForAmount(10);
		this.tfBalance.setEnabled(false);
		
		this.lbTradeDate = new JLabel("       �������");
		this.tfTradeDate = new EntJTextFieldForDate(7);
		
		this.lbTradeAmount = new JLabel("�Ѿ�");
		this.tfTradeAmount = new EntJTextFieldForAmount(7);
		
		this.lbCashAmount = new JLabel("����");
		this.tfCashAmount = new EntJTextFieldForAmount(7);
		
		this.lbRemarks = new JLabel("���");
		
		this.tfRemarks = new EntJTextFieldForRemarks(13);
		
		this.btnInsert = new EntJButton("���");
		this.btnInsert.addActionListener(new InsertButtonListener());
		
		this.tbAccountDetail = new EntJTable();
		
		//�׸����� ��������� �����Ѵ�.
		this.tbAccountDetail.entAddTableHeader("trade_sequence", "#", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("format_reckon_date", "�ŷ�����", JLabel.CENTER, 100);
		this.tbAccountDetail.entAddTableHeader("trade_type_name", "����", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("cancel_type_name", "���", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("format_trade_amount", "�ŷ��ݾ�", JLabel.RIGHT, 120);
		this.tbAccountDetail.entAddTableHeader("format_after_reckon_balance", "�ܾ�", JLabel.RIGHT, 120);
		this.tbAccountDetail.entAddTableHeader("remarks", "���", JLabel.LEFT, 260);

		this.panel1.add(lbAccount);
		this.panel1.add(cbAccount);
		this.panel1.add(lbBalance);
		//this.panel1.add(tfBalance);
		tfBalance.addPanel(this.panel1);
		this.panel1.add(lbTradeDate);
		tfTradeDate.addPanel(this.panel1);

		this.panel2.add(cbLinkType);
		this.panel2.add(cbTradeType);
		this.panel2.add(lbTradeAmount);
		tfTradeAmount.addPanel(this.panel2);
		this.panel2.add(lbCashAmount);
		tfCashAmount.addPanel(this.panel2);
		
		this.panel2.add(lbRemarks);
		tfRemarks.addPanel(this.panel2);
		btnInsert.addPanel(this.panel2);
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbAccountDetail));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle("[A05] ����������");
		
	}
	

	
	public void insert(){

		this.transactionKinds = "INSERT";
		
		if(this.tfTradeAmount.getValue().equals("0")){
			showMessageDialog("�ŷ��ݾ��� 0�Դϴ�.");
			return;
		}
		int nResult = showConfirmDialog("��� ó���Ͻðڽ��ϱ�?");
		if(nResult != 0) return;
		
		String strAccountNo = this.cbAccount.entGetCodeOfSelectedItem();
		
		BigDecimal bdTradeAmount = new BigDecimal(this.tfTradeAmount.getValue());
		BigDecimal bdCashAmount = new BigDecimal(this.tfCashAmount.getValue());
		String strLinkType = this.cbLinkType.entGetCodeOfSelectedItem();
		
		A05WithdrawAccountMainInsert01DTO inputDTO = new A05WithdrawAccountMainInsert01DTO();
		inputDTO.setAccountNo(strAccountNo);
		inputDTO.setReckonDate(this.tfTradeDate.getValue());
		inputDTO.setTotalAmount(bdTradeAmount);
		inputDTO.setCashAmount(bdCashAmount);
		inputDTO.setRemarks(this.tfRemarks.getText());
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.setIsOneSlip(true);
		businessDTO.addTransaction("A05201", inputDTO);
		
		//�����ŷ��϶��� �ŷ������� �ִ´�.
		if(strLinkType.equals("1")){
			SlipMasterDTO slipMaster = new SlipMasterDTO();
			slipMaster.setSlipAmount(bdTradeAmount);
			slipMaster.setDebtorAmount(bdTradeAmount);
			slipMaster.setCreditAmount(BigDecimal.ZERO);
			slipMaster.setUserId(this.userId);
			slipMaster.setRegisterUserId(this.userId);
			slipMaster.setLastRegisterUserId(this.userId);
			
			slipMaster.addDebtorAmount(this.cbTradeType.entGetCodeOfSelectedItem()
					, bdTradeAmount, bdCashAmount);
			
			businessDTO.addTransaction("S03201", slipMaster);
		}
		
		
		
		transactionCommitAtOnce(businessDTO);
		
	}
	

	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		
		businessDTO.addTransaction("A05101", this.userId);
		businessDTO.addTransaction("C00101", "09");
		businessDTO.addTransaction("A03101", "('4')");
		
		transactionCommitOneByOne(businessDTO);		
	}


	@Override
	public void search() {

		this.transactionKinds = "SEARCH";
		
		String strAccountNo = this.cbAccount.entGetCodeOfSelectedItem();
		
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A02103", strAccountNo);
		businessDTO.addTransaction("A02102", strAccountNo);
		transactionCommitOneByOne(businessDTO);
		

	}
	

	
	public void changeCbAccount(){
		search();
	}
	
	public void changeCbLinkType(){
		
		String strCode = this.cbLinkType.entGetCodeOfSelectedItem();
		
		if(strCode.equals("0")){
			this.cbTradeType.setSelectedItem(null);
			this.cbTradeType.setEnabled(false);
			this.tfCashAmount.setValue("0");
			this.tfCashAmount.setEnabled(true);
			
		} else {
			this.cbTradeType.setSelectedIndex(0);
			this.cbTradeType.setEnabled(true);
			this.tfCashAmount.setValue("0");
			this.tfCashAmount.setEnabled(true);
		}
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
	class CbLinkTypeChangeListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			changeCbLinkType();
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
		else if(this.transactionKinds.equals("DELETE")){
			search();
		}
		
	}
	
	public void afterbindComboBox(EntBusiness businessDTO){
		
		EntHashList ehlAccount = (EntHashList) businessDTO.getResultByTransactionIndex(0);
		this.cbAccount.entSetEntHashList(ehlAccount);
		
		EntHashList ehlLinkType = (EntHashList) businessDTO.getResultByTransactionIndex(1);
		this.cbLinkType.entSetEntHashList(ehlLinkType);
		
		EntHashList ehlTradeType = (EntHashList) businessDTO.getResultByTransactionIndex(2);
		this.cbTradeType.entSetEntHashList(ehlTradeType);
		
		changeCbLinkType();
	}
	
	public void afterSearch(EntBusiness businessDTO){
		
		DefaultTableModel model = (DefaultTableModel)businessDTO.getResultByTransactionIndex(0);
		this.tbAccountDetail.entSetTableModel(model);
		
		AccountMasterDTO accountMaster = (AccountMasterDTO)businessDTO.getResultByTransactionIndex(1);
		this.tfBalance.setValue(accountMaster.getBalance());
	}




}
