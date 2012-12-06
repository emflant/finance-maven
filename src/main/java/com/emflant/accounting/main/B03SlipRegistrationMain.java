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

import com.emflant.accounting.dto.screen.A10CashInOutTradeMainInsert01DTO;
import com.emflant.accounting.dto.table.AccountMasterDTO;
import com.emflant.accounting.main.component.EntJComboBox;
import com.emflant.accounting.main.component.EntJTable;
import com.emflant.common.EntBean;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntCommon;
import com.emflant.common.EntDate;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;

public class B03SlipRegistrationMain extends EntScreenMain {

	private JPanel centerPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private JLabel lbAccount;
	private EntJComboBox cbAccount;
	
	private EntJComboBox cbLinkType;
	
	private EntJComboBox cbTradeType;
	
	private JLabel lbBalance;
	private JTextField tfBalance;
	
	private JLabel lbTradeDate;
	private JTextField tfTradeDate;
	
	
	private JLabel lbTradeAmount;
	private JTextField tfTradeAmount;
	
	private JLabel lbRemarks;
	private JTextField tfRemarks;
	
	private JButton btnInsert;
	private EntJTable tbAccountDetail;
	
	
	public B03SlipRegistrationMain(JFrame frame, EntBean entBean, String userId){
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
		this.tfBalance = new JTextField(10);
		this.tfBalance.setEnabled(false);
		this.tfBalance.setHorizontalAlignment(JTextField.RIGHT);
		
		this.lbTradeDate = new JLabel("       �������");
		this.tfTradeDate = new JTextField(7);
		this.tfTradeDate.setText(EntDate.getToday());
		this.tfTradeDate.setHorizontalAlignment(JTextField.CENTER);
		
		this.lbTradeAmount = new JLabel("�ŷ��ݾ�");
		
		this.tfTradeAmount = new JTextField(7);
		this.tfTradeAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.tfTradeAmount.setText("0");
		
		this.lbRemarks = new JLabel("���");
		this.tfRemarks = new JTextField(13);
		this.tfRemarks.setHorizontalAlignment(JTextField.LEFT);
		
		this.btnInsert = new JButton("�Ա�");
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
		this.panel1.add(tfBalance);
		this.panel1.add(lbTradeDate);
		this.panel1.add(tfTradeDate);

		this.panel2.add(cbLinkType);
		this.panel2.add(cbTradeType);
		this.panel2.add(lbTradeAmount);
		this.panel2.add(tfTradeAmount);

		this.panel2.add(lbRemarks);
		this.panel2.add(tfRemarks);
		this.panel2.add(btnInsert);
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbAccountDetail));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle("[S03] ��ǥ���");
		
	}
	

	
	public void insert(){

		this.transactionKinds = "INSERT";
		
		if(this.tfTradeAmount.getText().equals("0")){
			showMessageDialog("�ŷ��ݾ��� 0�Դϴ�.");
			return;
		}
		
		int nResult = showConfirmDialog(cbTradeType.entGetCodeNameOfSelectedItem()
				+"(��)�� ���� "
				+this.tfTradeAmount.getText()+"�� "
				+this.cbLinkType.entGetCodeNameOfSelectedItem()+" ó���Ͻðڽ��ϱ�?");
		if(nResult != 0) return;
		
		BigDecimal bdTradeAmount = new BigDecimal(this.tfTradeAmount.getText());
		String strLinkType = this.cbLinkType.entGetCodeOfSelectedItem();
		
		A10CashInOutTradeMainInsert01DTO inputDTO = new A10CashInOutTradeMainInsert01DTO();
		
		inputDTO.setInOutType(this.cbLinkType.entGetCodeOfSelectedItem());
		inputDTO.setTradeType(this.cbTradeType.entGetCodeOfSelectedItem());
		inputDTO.setTotalAmount(bdTradeAmount);
		inputDTO.setRemarks(this.tfRemarks.getText());
		
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A10201", inputDTO);
		transactionCommitAtOnce(businessDTO);
		
	}
	

	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		
		businessDTO.addTransaction("A04101", this.userId);
		businessDTO.addTransaction("C00101", "02");
		//businessDTO.addTransaction("A03101", "('3')");
		
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
		
		this.transactionKinds = "BIND_TRADE_KINDS";
		String strCode = this.cbLinkType.entGetCodeOfSelectedItem();
		
		EntBusiness businessDTO = new EntBusiness();
		
		//1:�Ա�
		if(strCode.equals("1")){
			businessDTO.addTransaction("A03101", "('3')");
		} else {
			businessDTO.addTransaction("A03101", "('4')");
		}
		
		transactionCommitOneByOne(businessDTO);
	}
	

	
	class CbAccountChangeListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			changeCbAccount();
		}
		
	}
	
	class CbLinkTypeChangeListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			changeCbLinkType();
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
		else if(this.transactionKinds.equals("BIND_TRADE_KINDS")){
			afterbindTradeKinds(businessDTO);
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
		
		//EntHashList ehlTradeType = (EntHashList) businessDTO.getResultByTransactionIndex(2);
		//this.cbTradeType.entSetEntHashList(ehlTradeType);
		
		changeCbLinkType();
	}
	public void afterbindTradeKinds(EntBusiness businessDTO){
		
		EntHashList ehlTradeType = (EntHashList) businessDTO.getResultByTransactionIndex(0);
		this.cbTradeType.entSetEntHashList(ehlTradeType);
	}
	
	public void afterSearch(EntBusiness businessDTO){
		
		DefaultTableModel model = (DefaultTableModel)businessDTO.getResultByTransactionIndex(0);
		this.tbAccountDetail.entSetTableModel(model);
		
		AccountMasterDTO accountMaster = (AccountMasterDTO)businessDTO.getResultByTransactionIndex(1);
		this.tfBalance.setText(EntCommon.formatAmount(accountMaster.getBalance()));
	}



}
