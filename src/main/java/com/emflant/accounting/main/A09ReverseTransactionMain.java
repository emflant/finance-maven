package com.emflant.accounting.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.screen.A09ReverseTransactionMainInsert01DTO;
import com.emflant.accounting.dto.screen.A09ReverseTransactionMainSelect01DTO;
import com.emflant.accounting.dto.table.AccountMasterDTO;
import com.emflant.accounting.dto.table.SlipDetailDTO;
import com.emflant.accounting.main.B01SlipDetailInquiryByDaliyMain.TableSelectionListener;
import com.emflant.accounting.main.component.EntJButton;
import com.emflant.accounting.main.component.EntJComboBox;
import com.emflant.accounting.main.component.EntJTable;
import com.emflant.accounting.main.component.EntJTextFieldForAmount;
import com.emflant.accounting.main.component.EntJTextFieldForRemarks;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntCommon;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;
import com.emflant.common.EntTransaction;
import com.emflant.common.EntLogger;

public class A09ReverseTransactionMain extends EntScreenMain {


	private JPanel centerPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private JLabel lbAccount;
	private EntJComboBox cbAccount;
	
	private JLabel lbBalance;
	private EntJTextFieldForAmount tfBalance;

	
	private JLabel lbRemarks;
	private EntJTextFieldForRemarks tfRemarks;
	
	
	private EntJButton btnInsert;
	private EntJTable tbAccountDetail;
	private EntJTable tbLinkTransactions;
	
	
	public A09ReverseTransactionMain(JFrame frame, EntBean entBean, String userId){
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
		this.centerPanel.setLayout(new BoxLayout(this.centerPanel, BoxLayout.Y_AXIS));
		//this.centerPanel.setLayout(new BorderLayout());
		
		this.southPanel = new JPanel();
		this.southPanel.setBackground(Color.WHITE);
		this.southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.lbAccount = new JLabel("계좌");
		this.cbAccount = new EntJComboBox();
		this.cbAccount.addActionListener(new CbAccountChangeListener());
		
		this.lbBalance = new JLabel("       잔액");
		this.tfBalance = new EntJTextFieldForAmount(10);
		this.tfBalance.setEnabled(false);
		

		this.lbRemarks = new JLabel("적요");
		
		this.tfRemarks = new EntJTextFieldForRemarks(15);
		
		this.btnInsert = new EntJButton("취소 / 정정");
		this.btnInsert.addActionListener(new InsertButtonListener());

		this.tbAccountDetail = new EntJTable();
		this.tbAccountDetail.getSelectionModel().addListSelectionListener(new TableSelectionListener());
		
		//그리드의 헤더정보를 정의한다.
		this.tbAccountDetail.entAddTableHeader("trade_sequence", "#", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("format_reckon_date", "거래일자", JLabel.CENTER, 100);
		this.tbAccountDetail.entAddTableHeader("trade_type_name", "종류", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("cancel_type_name", "취소", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("format_trade_amount", "거래금액", JLabel.RIGHT, 120);
		this.tbAccountDetail.entAddTableHeader("format_after_reckon_balance", "잔액", JLabel.RIGHT, 120);
		this.tbAccountDetail.entAddTableHeader("remarks", "적요", JLabel.LEFT, 260);
		
		this.tbLinkTransactions = new EntJTable();
		
		//그리드의 헤더정보를 정의한다.
		//this.tbLinkTransactions.entAddCheckBoxTableHeader("checkbox", "check", JCheckBox.CENTER, 50);
		this.tbLinkTransactions.entAddTableHeader("transaction_sequence", "#", JLabel.CENTER, 50);
		this.tbLinkTransactions.entAddTableHeader("bean_type", "클래스명", JLabel.LEFT, 200);
		this.tbLinkTransactions.entAddTableHeader("method_type", "메소드명", JLabel.LEFT, 200);
		this.tbLinkTransactions.entAddTableHeader("start_date_time", "등록일시", JLabel.CENTER, 200);
		this.tbLinkTransactions.entAddTableHeader("gap_time", "경과시간", JLabel.CENTER, 100);
		this.tbLinkTransactions.entAddTableHeader("cancel_trade_code", "취소거래", JLabel.CENTER, 100);
		
		this.panel1.add(lbAccount);
		this.panel1.add(cbAccount);
		this.panel1.add(lbBalance);
		tfBalance.addPanel(this.panel1);
		
		this.panel2.add(lbRemarks);
		tfRemarks.addPanel(this.panel2);
		btnInsert.addPanel(this.panel2);
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		
		this.centerPanel.add(new JScrollPane(tbAccountDetail));
		this.centerPanel.add(new JScrollPane(tbLinkTransactions));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle(this.userId + "의 계정취소/정정");
		
	}
	


	public void insert(){

		this.transactionKinds = "INSERT";
		
		String strAccountNo = this.cbAccount.entGetCodeOfSelectedItem();

		int nSelectedRow = this.tbAccountDetail.getSelectedRow();
		
		if(nSelectedRow == -1){
			showMessageDialog("취소할 거래를 선택하세요.");
		}
		
		tbLinkTransactions.entGetValueOfSelectedRow("");
		
		String strTradeSequence = this.tbAccountDetail.entGetValueOfSelectedRow("trade_sequence");
		
		int nResult = showConfirmDialog(strAccountNo +" 계좌의 "+strTradeSequence+"번 거래를 취소하시겠습니까?");
		if(nResult != 0) return;
		
		A09ReverseTransactionMainInsert01DTO inputDTO = null;
		//inputDTO.setAccountNo(strAccountNo);
		//inputDTO.setTradeSequence(Integer.parseInt(strTradeSequence));
		
		int nRowCount = this.tbLinkTransactions.getRowCount();
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.setIsOneSlip(true);
		
		
		for(int i=0;i<nRowCount;i++){
			inputDTO = new A09ReverseTransactionMainInsert01DTO();
			inputDTO.setRegisterUserId(this.userId);
			inputDTO.setRegisterDateTime(this.tbLinkTransactions.entGetValueOfSelectedRow(i, "start_date_time"));
			
			String strCancelTradeCode = this.tbLinkTransactions.entGetValueOfSelectedRow(i, "cancel_trade_code");
			
			
			if(strCancelTradeCode != null) 
				businessDTO.addTransaction(strCancelTradeCode, inputDTO);
			
		}
		
		if(businessDTO.getTransactionCount() > 0){
			transactionCommitAtOnce(businessDTO);
		} else {
			showMessageDialog("취소할 거래가 없습니다.");
		}
		
		
	}

	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A02101", this.userId);
		
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
		else if(this.transactionKinds.equals("SEARCH_DETAIL")){
			afterSearchDetail(businessDTO);
			
		}
		
	}
	
	
	public void afterSearchDetail(EntBusiness businessDTO){
		
		DefaultTableModel model = (DefaultTableModel)businessDTO.getResultByTransactionIndex(0);
		this.tbLinkTransactions.entSetTableModel(model);
		
	}
	
	public void afterbindComboBox(EntBusiness businessDTO){
		
		EntHashList ehlAccount = (EntHashList) businessDTO.getResultByTransactionIndex(0);
		this.cbAccount.entSetEntHashList(ehlAccount);
	}
	
	public void afterSearch(EntBusiness businessDTO){
		
		DefaultTableModel model = (DefaultTableModel)businessDTO.getResultByTransactionIndex(0);
		this.tbAccountDetail.entSetTableModel(model);
		
		AccountMasterDTO accountMaster = (AccountMasterDTO)businessDTO.getResultByTransactionIndex(1);
		this.tfBalance.setValue(accountMaster.getBalance());
	}

	class TableSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())	bindUpdateData();
		}
	}

	/**
	 * 수정을 위해 그리드클릭시 데이터 바인딩 한다.
	 */
	public void bindUpdateData(){
		
		this.transactionKinds = "SEARCH_DETAIL";
		
		if(tbAccountDetail.getSelectedRow() == -1) return;
		
		String strAccountNo = this.cbAccount.entGetCodeOfSelectedItem();
		String strTradeSequence = this.tbAccountDetail.entGetValueOfSelectedRow("trade_sequence");
		
		
		A09ReverseTransactionMainSelect01DTO inputDTO = new A09ReverseTransactionMainSelect01DTO();
		inputDTO.setAccountNo(strAccountNo);
		inputDTO.setTradeSequence(new Integer(strTradeSequence));
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A09101", inputDTO);
		transactionCommitOneByOne(businessDTO);
	}
}
