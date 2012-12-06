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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.screen.A01AccountMasterMainInsert01DTO;
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
 * 계좌신규등록
 * @author home
 *
 */
public class A01AccountMasterMain extends EntScreenMain {
	
	private JPanel northPanel;
	private JPanel panel1;

	private JPanel centerPanel;
	private JPanel southPanel;
	private EntJTextFieldForRemarks tfRemarks;
	private EntJComboBox cbAccountType;
	private JLabel lbAmount;
	private EntJTextFieldForAmount tfAmount;
	private JLabel lbCashAmount;
	private EntJTextFieldForAmount tfCashAmount;
	
	private JLabel lbNewDate;
	private EntJTextFieldForDate tfNewDate;
	
	private EntJButton btnInsert;
	private EntJTable tbAccountList;
	
	
	
	/**
	 * USER ID 를 입력받는 생성자
	 * @param userId
	 */
	public A01AccountMasterMain(JFrame frame, EntBean entBean, String userId){
		this.frame = frame;
		this.entBean = entBean;
		this.userId = userId;
	}

	@Override
	public void initScreen()
	{
		
		this.northPanel = new JPanel();
		this.northPanel.setLayout(new BoxLayout(this.northPanel, BoxLayout.Y_AXIS));
		
		this.panel1 = new JPanel();
		this.panel1.setBackground(Color.WHITE);
		this.panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.centerPanel = new JPanel();
		this.centerPanel.setBackground(Color.WHITE);
		this.centerPanel.setLayout(new BorderLayout());
		
		this.southPanel = new JPanel();
		this.southPanel.setBackground(Color.WHITE);
		this.southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.tfRemarks = new EntJTextFieldForRemarks();
		this.cbAccountType = new EntJComboBox();
		
		
		this.lbAmount = new JLabel("금액");
		this.tfAmount = new EntJTextFieldForAmount(7);
		
		this.lbCashAmount = new JLabel("현금");
		this.tfCashAmount = new EntJTextFieldForAmount(7);
		
		this.lbNewDate = new JLabel("신규일자");
		this.tfNewDate = new EntJTextFieldForDate();
		
		this.btnInsert = new EntJButton("등록");
		
		this.btnInsert.addActionListener(new InsertButtonListener());
		

		this.tbAccountList = new EntJTable();
		this.tbAccountList.getSelectionModel().addListSelectionListener(new TableSelectionListener());
		
		this.tbAccountList.entAddTableHeader("account_type_nm", "계좌유형", JLabel.CENTER, 50);
		this.tbAccountList.entAddTableHeader("remarks", "적요", JLabel.LEFT, 300);
		this.tbAccountList.entAddTableHeader("format_new_date", "신규일자", JLabel.CENTER, 80);
		this.tbAccountList.entAddTableHeader("account_status_nm", "계좌상태", JLabel.CENTER, 50);
		this.tbAccountList.entAddTableHeader("format_balance", "금액", JLabel.RIGHT, 100);
		
		
		this.southPanel.add(cbAccountType);
		tfRemarks.addPanel(this.southPanel);
		this.southPanel.add(lbNewDate);
		tfNewDate.addPanel(this.southPanel);
		this.southPanel.add(lbAmount);
		tfAmount.addPanel(this.southPanel);
		this.southPanel.add(lbCashAmount);
		tfCashAmount.addPanel(this.southPanel);
		btnInsert.addPanel(this.southPanel);
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbAccountList));
		
		this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

		setTitle("[A01] 계좌내역");
		
	}
	
	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A01102", null);

		transactionCommitOneByOne(businessDTO);
		
	}
	
	@Override
	public void search() {
		this.transactionKinds = "SEARCH";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("A01101", this.userId);
		transactionCommitOneByOne(businessDTO);
	}
	
	
	public void bindUpdateData(){
		
		if(tbAccountList.getSelectedRow() == -1) return;
		
		String code = this.tbAccountList.entGetValueOfSelectedRow("account_type");
		this.cbAccountType.entSetSelectedItemByCode(code);
		
		this.tfRemarks.setText(this.tbAccountList.entGetValueOfSelectedRow("remarks"));
		//this.tfAmount.setText(this.tbAccountList.entGetValueOfSelectedRow("balance"));
		this.tfNewDate.setValue(this.tbAccountList.entGetValueOfSelectedRow("new_date"));
		
	}
	
	class TableSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			//if(e.getValueIsAdjusting())	bindUpdateData();
		}
	}
	
	class SearchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			searchAccountMaster();
		}
	}
	
	class InsertButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			insertAccountMaster();
		}
	}


	
	public void searchAccountMaster(){
		search();
	}
	
	public void insertAccountMaster() {

		this.transactionKinds = "INSERT";	
	
		
		String strAccountType = this.cbAccountType.entGetCodeOfSelectedItem();
		
		A01AccountMasterMainInsert01DTO inputDTO1 = new A01AccountMasterMainInsert01DTO();
		inputDTO1.setAccountType(strAccountType);
		inputDTO1.setRemarks(this.tfRemarks.getText());
		inputDTO1.setNewDate(this.tfNewDate.getValue());
		inputDTO1.setTotalAmount(new BigDecimal(this.tfAmount.getValue()));
		inputDTO1.setCashAmount(new BigDecimal(this.tfCashAmount.getValue()));
		
		int nResult = showConfirmDialog(this.cbAccountType.entGetCodeNameOfSelectedItem() 
				+" 계좌를 신규하시겠습니까?");
		if(nResult != 0) return;
		
		EntBusiness business = new EntBusiness();
		business.addTransaction("A01201", inputDTO1);
		transactionCommitAtOnce(business);

	}
	
	
	@Override
	public void localCallback(EntBusiness business) {
		if(this.transactionKinds.equals("BIND_COMBOBOX")){
			afterbindComboBox(business);
		}
		else if(this.transactionKinds.equals("SEARCH")){
			afterSearch(business);
		}
		else if(this.transactionKinds.equals("UPDATE")){
			search();
		}
		else if(this.transactionKinds.equals("INSERT")){
			search();
		}
		else if(this.transactionKinds.equals("DELETE")){
			search();
		}
	}

	public void afterbindComboBox(EntBusiness business) {
		EntHashList entHashList = (EntHashList) business.getResultByTransactionIndex(0);
		this.cbAccountType.entSetEntHashList(entHashList);
	}

	public void afterSearch(EntBusiness business){
		DefaultTableModel model = (DefaultTableModel)business.getResultByTransactionIndex(0);
		this.tbAccountList.entSetTableModel(model);
		
	}
}


