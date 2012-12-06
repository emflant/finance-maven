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
import com.emflant.accounting.dto.screen.A06CreditCardRepaymentMainInsert01DTO;
import com.emflant.accounting.dto.screen.A06CreditCardRepaymentMainSelect01DTO;
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

public class A06CreditCardRepaymentMain extends EntScreenMain {

	private JPanel centerPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private JLabel lbAccount;
	private EntJComboBox cbAccount;
	
	private JLabel lbBalance;
	private EntJTextFieldForAmount tfTotalRepaymentAmount;
	
	private JLabel lbRepaymentYm;
	private JTextField tfRepaymentYm;
	

	
	private EntJButton btnSearch;
	
	private EntJComboBox cbWithdrawAccount;
	
	private JLabel lbTradeDate;
	private EntJTextFieldForDate tfTradeDate;
	
	private JLabel lbRemarks;
	private EntJTextFieldForRemarks tfRemarks;
	
	private EntJButton btnInsert;
	private EntJTable tbAccountDetail;
	
	
	public A06CreditCardRepaymentMain(JFrame frame, EntBean entBean, String userId){
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
		
		this.lbAccount = new JLabel("�ſ�ī��");
		this.cbAccount = new EntJComboBox();
		this.cbAccount.addActionListener(new CbAccountChangeListener());
		
		this.lbBalance = new JLabel(" ���������ݾ�");
		this.tfTotalRepaymentAmount = new EntJTextFieldForAmount(8);
		this.tfTotalRepaymentAmount.setEnabled(false);
		
		this.lbRepaymentYm = new JLabel("   �������س��");
		this.tfRepaymentYm = new JTextField(7);
		this.tfRepaymentYm.setText(EntDate.getToday().substring(0, 6));
		this.tfRepaymentYm.setHorizontalAlignment(JTextField.CENTER);
		
		this.btnSearch = new EntJButton("��ȸ");
		this.btnSearch.addActionListener(new SearchButtonListener());
		
		this.cbWithdrawAccount = new EntJComboBox();
		
		this.lbTradeDate = new JLabel("   ��������");
		this.tfTradeDate = new EntJTextFieldForDate(7);
		
		this.lbRemarks = new JLabel("���");
		this.tfRemarks = new EntJTextFieldForRemarks(15);
		
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
		//this.panel1.add(tfTotalRepaymentAmount);
		tfTotalRepaymentAmount.addPanel(this.panel1);
		this.panel1.add(lbRepaymentYm);
		this.panel1.add(tfRepaymentYm);
		btnSearch.addPanel(this.panel1);
		this.panel2.add(cbWithdrawAccount);
		this.panel2.add(lbTradeDate);
		this.tfTradeDate.addPanel(this.panel2);
		this.panel2.add(lbRemarks);
		tfRemarks.addPanel(this.panel2);
		btnInsert.addPanel(this.panel2);
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbAccountDetail));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle("[A06] ī�����");
		
	}
	

	
	public void insert(){

		this.transactionKinds = "INSERT";
		
		String totalRepaymentAmount = this.tfTotalRepaymentAmount.getValue();
		
		if(totalRepaymentAmount.equals("") || totalRepaymentAmount.equals("0")){
			showMessageDialog("�ſ�ī�� �̿����� 0�Դϴ�.");
			return;
		}
		
		int nResult = showConfirmDialog("ī���̿��� " + this.tfTotalRepaymentAmount.getText() + 
				"�� �����Ͻðڽ��ϱ�?");
		if(nResult != 0) return;
		
		String strCreditcardAccountNo = this.cbAccount.entGetCodeOfSelectedItem();
		String strWithdrawAccountNo = this.cbWithdrawAccount.entGetCodeOfSelectedItem();
		
		BigDecimal bdTradeAmount = new BigDecimal(totalRepaymentAmount);

		
		
		//����������
		A05WithdrawAccountMainInsert01DTO inputDTO1 = new A05WithdrawAccountMainInsert01DTO();
		inputDTO1.setAccountNo(strWithdrawAccountNo);
		inputDTO1.setReckonDate(this.tfTradeDate.getValue());
		inputDTO1.setTotalAmount(bdTradeAmount);
		inputDTO1.setCashAmount(BigDecimal.ZERO);
		inputDTO1.setRemarks(this.tfRemarks.getText());
		
		//�ſ�ī�����(��ȯ)
		A06CreditCardRepaymentMainInsert01DTO inputDTO2 = new A06CreditCardRepaymentMainInsert01DTO();
		inputDTO2.setAccountNo(strCreditcardAccountNo);
		inputDTO2.setRemarks(this.tfRemarks.getText());
		inputDTO2.setReckonDate(this.tfTradeDate.getValue());
		inputDTO2.setTotalAmount(bdTradeAmount);
		inputDTO2.setCashAmount(BigDecimal.ZERO);
		inputDTO2.setRepaymentYm(this.tfRepaymentYm.getText());
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.setIsOneSlip(true);
		businessDTO.addTransaction("A05201", inputDTO1);
		businessDTO.addTransaction("A06201", inputDTO2);
		
		transactionCommitAtOnce(businessDTO);
		
	}
	

	
	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A03102", this.userId);
		businessDTO.addTransaction("A05101", this.userId);
		
		transactionCommitOneByOne(businessDTO);		
	}


	@Override
	public void search() {

		this.transactionKinds = "SEARCH";
		
		String strAccountNo = this.cbAccount.entGetCodeOfSelectedItem();
		
		A06CreditCardRepaymentMainSelect01DTO inputDTO = new A06CreditCardRepaymentMainSelect01DTO();
		inputDTO.setAccountNo(strAccountNo);
		inputDTO.setRepaymentYm(this.tfRepaymentYm.getText());
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A06101", inputDTO);
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
	
	class SearchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			search();
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
		
		EntHashList ehlAccount = (EntHashList) businessDTO.getResultByTransactionIndex(0);
		this.cbAccount.entSetEntHashList(ehlAccount);
		
		EntHashList ehlWithdrawAccount = (EntHashList) businessDTO.getResultByTransactionIndex(1);
		this.cbWithdrawAccount.entSetEntHashList(ehlWithdrawAccount);
	}
	
	public void afterSearch(EntBusiness businessDTO){
		
		DefaultTableModel model = (DefaultTableModel)businessDTO.getResultByTransactionIndex(0);
		this.tbAccountDetail.entSetTableModel(model);
		
		String totalRepaymentAmount = (String)businessDTO.getResultByTransactionIndex(0,1);
		
		this.tfTotalRepaymentAmount.setValue(totalRepaymentAmount);
	}




}