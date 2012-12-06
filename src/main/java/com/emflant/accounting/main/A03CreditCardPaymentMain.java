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

import com.emflant.accounting.dto.screen.A03CreditCardPaymentMainInsert01DTO;
import com.emflant.accounting.dto.screen.A04DepositAccountMainInsert01DTO;
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
import com.emflant.common.EntDate;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;

/**
 * ī����� ȭ��
 * @author home
 *
 */
public class A03CreditCardPaymentMain extends EntScreenMain {

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
	
	private EntJComboBox cbTradeType;
	
	private JLabel lbTradeAmount;
	private EntJTextFieldForAmount tfTradeAmount;
	private JLabel lbCashAmount;
	private EntJTextFieldForAmount tfCashAmount;
	
	private JLabel lbRemarks;
	private EntJTextFieldForRemarks tfRemarks;
	
	
	private EntJButton btnInsert;
	private EntJTable tbAccountDetail;
	
	
	public A03CreditCardPaymentMain(JFrame frame, EntBean entBean, String userId){
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
		
		this.lbAccount = new JLabel("����");
		this.cbAccount = new EntJComboBox();
		this.cbAccount.addActionListener(new CbAccountChangeListener());
		
		this.lbBalance = new JLabel("       �ܾ�");
		this.tfBalance = new EntJTextFieldForAmount(10);
		this.tfBalance.setEnabled(false);
		
		this.lbTradeDate = new JLabel("       �������");
		this.tfTradeDate = new EntJTextFieldForDate(7);
		
		this.cbTradeType = new EntJComboBox();
		
		this.lbTradeAmount = new JLabel("�Ѿ�");
		this.tfTradeAmount = new EntJTextFieldForAmount(7);
		
		this.lbCashAmount = new JLabel("�����Ա�");
		this.tfCashAmount = new EntJTextFieldForAmount(7);
		
		this.lbRemarks = new JLabel("���");
		
		this.tfRemarks = new EntJTextFieldForRemarks(15);
		
		this.btnInsert = new EntJButton("���");
		this.btnInsert.addActionListener(new InsertButtonListener());
		//this.btnDelete = new JButton("����");
		//this.btnDelete.addActionListener(new DeleteButtonListener());
		
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
		tfBalance.addPanel(this.panel1);
		this.panel1.add(lbTradeDate);
		//this.panel1.add(tfTradeDate);
		tfTradeDate.addPanel(this.panel1);
		this.panel2.add(cbTradeType);

		this.panel2.add(lbTradeAmount);
		tfTradeAmount.addPanel(this.panel2);
		//this.panel2.add(tfTradeAmount);
		this.panel2.add(lbCashAmount);
		tfCashAmount.addPanel(this.panel2);
		//this.panel2.add(tfCashAmount);
		
		this.panel2.add(lbRemarks);
		tfRemarks.addPanel(this.panel2);
		//this.panel2.add(tfRemarks);
		btnInsert.addPanel(panel2);
		//this.panel2.add(btnInsert);
		//this.panel2.add(btnDelete);
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbAccountDetail));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle("[A03] ī�����");
		
	}
	

	
	public void insert(){

		if(this.tfTradeAmount.getValue().equals("0")){
			showMessageDialog("�ŷ��ݾ��� 0�Դϴ�.");
			return;
		}
		
		int nResult = showConfirmDialog(this.cbTradeType.entGetCodeNameOfSelectedItem()
				+"(��)�� ī����� ó���Ͻðڽ��ϱ�?");
		if(nResult != 0) return;
		
		String strAccountNo = this.cbAccount.entGetCodeOfSelectedItem();

		BigDecimal bdTradeAmount = new BigDecimal(this.tfTradeAmount.getValue());
		BigDecimal bdCashAmount = new BigDecimal(this.tfCashAmount.getValue());
		
		
		String strTradeType = this.cbTradeType.entGetCodeOfSelectedItem();
		

		
		A03CreditCardPaymentMainInsert01DTO inputDTO = new A03CreditCardPaymentMainInsert01DTO();
		
		inputDTO.setAccountNo(strAccountNo);
		inputDTO.setRemarks(this.tfRemarks.getText());
		inputDTO.setReckonDate(this.tfTradeDate.getValue());
		inputDTO.setTradeType(strTradeType);
		inputDTO.setTotalAmount(bdTradeAmount);
		inputDTO.setCashAmount(bdCashAmount);
		
		insert(inputDTO);
	}
	

	
	public void insert(A03CreditCardPaymentMainInsert01DTO inputDTO){

		this.transactionKinds = "INSERT";
		

		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.setIsOneSlip(true);
		businessDTO.addTransaction("A03201", inputDTO);
		
		BigDecimal bdNonCashAmount = inputDTO.getTotalAmount().subtract(inputDTO.getCashAmount());
		
		//�����ŷ��϶��� �ŷ������� �ִ´�.
		if(bdNonCashAmount.compareTo(BigDecimal.ZERO) == 1){
			SlipMasterDTO slipMaster = new SlipMasterDTO();
			slipMaster.setSlipAmount(bdNonCashAmount);
			slipMaster.setDebtorAmount(bdNonCashAmount);
			slipMaster.setCreditAmount(BigDecimal.ZERO);
			
			slipMaster.addDebtorAmount(this.cbTradeType.entGetCodeOfSelectedItem()
					, bdNonCashAmount, BigDecimal.ZERO);
			
			businessDTO.addTransaction("S03201", slipMaster);
		}
		
		//������ ������ �ٷ� �������� �Ա�ó���Ѵ�.
		if(inputDTO.getCashAmount().compareTo(BigDecimal.ZERO) == 1){
			A04DepositAccountMainInsert01DTO inputDTO3 = new A04DepositAccountMainInsert01DTO();
			inputDTO3.setAccountNo("1900000005");
			inputDTO3.setReckonDate(this.tfTradeDate.getValue());
			inputDTO3.setTotalAmount(inputDTO.getCashAmount());
			inputDTO3.setCashAmount(inputDTO.getCashAmount());
			inputDTO3.setRemarks(this.tfRemarks.getText());
			inputDTO3.setLinkType("0");
			
			businessDTO.addTransaction("A04201", inputDTO3);
		}
		
		
		transactionCommitAtOnce(businessDTO);
	}
	
	
	
	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A03102", null);
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
		
		EntHashList ehlAccount = (EntHashList) businessDTO.getResultByTransactionIndex(0);
		this.cbAccount.entSetEntHashList(ehlAccount);
		
		EntHashList ehlTradeType = (EntHashList) businessDTO.getResultByTransactionIndex(1);
		this.cbTradeType.entSetEntHashList(ehlTradeType);
		
	}
	
	public void afterSearch(EntBusiness businessDTO){
		
		DefaultTableModel model = (DefaultTableModel)businessDTO.getResultByTransactionIndex(0);
		this.tbAccountDetail.entSetTableModel(model);
		
		AccountMasterDTO accountMaster = (AccountMasterDTO)businessDTO.getResultByTransactionIndex(1);
		this.tfBalance.setValue(accountMaster.getBalance());
	}




}
