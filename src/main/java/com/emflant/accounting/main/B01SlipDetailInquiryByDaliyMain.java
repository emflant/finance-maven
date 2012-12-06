package com.emflant.accounting.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.table.SlipDetailDTO;
import com.emflant.accounting.main.component.EntJTable;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntCommon;
import com.emflant.common.EntDate;
import com.emflant.common.EntScreenMain;
import com.emflant.common.EntTransaction;

public class B01SlipDetailInquiryByDaliyMain extends EntScreenMain {

	private JPanel centerPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private JLabel lbTradeDate;
	private JTextField tfTradeDate;

	private JButton btnInsert;
	private JButton btnDelete;
	private EntJTable tbSlipMaster;
	private EntJTable tbSlipDetail;
	
	
	public B01SlipDetailInquiryByDaliyMain(JFrame frame, EntBean entBean, String userId){
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
		

		
		this.lbTradeDate = new JLabel("��������");
		this.tfTradeDate = new JTextField(7);
		this.tfTradeDate.setText(EntDate.getToday());
		this.tfTradeDate.setHorizontalAlignment(JTextField.CENTER);

		this.btnInsert = new JButton("���");
		//this.btnInsert.addActionListener(new InsertButtonListener());
		this.btnDelete = new JButton("����");
		//this.btnDelete.addActionListener(new DeleteButtonListener());
		
		this.tbSlipMaster = new EntJTable();
		this.tbSlipMaster.getSelectionModel().addListSelectionListener(new TableSelectionListener());
		this.tbSlipDetail = new EntJTable();
		
		//��ǥ������ �׸����� ��������� �����Ѵ�.
		this.tbSlipMaster.entAddTableHeader("slip_no", "��ǥ��ȣ", JLabel.CENTER, 140);
		this.tbSlipMaster.entAddTableHeader("slip_sequence", "#", JLabel.CENTER, 20);
		this.tbSlipMaster.entAddTableHeader("format_slip_date", "��ǥ����", JLabel.CENTER, 90);
		this.tbSlipMaster.entAddTableHeader("format_slip_amount", "��ǥ�ݾ�", JLabel.RIGHT, 100);
		this.tbSlipMaster.entAddTableHeader("format_debtor_amount", "�����ݾ�", JLabel.RIGHT, 100);
		this.tbSlipMaster.entAddTableHeader("format_credit_amount", "�뺯�ݾ�", JLabel.RIGHT, 100);

		//��ǥ������ �׸����� ��������� �����Ѵ�.
		this.tbSlipDetail.entAddTableHeader("sequence", "#", JLabel.CENTER, 30);
		this.tbSlipDetail.entAddTableHeader("account_type_name", "����", JLabel.LEFT, 100);
		this.tbSlipDetail.entAddTableHeader("bs_pl_detail_type_name", "����", JLabel.CENTER, 50);
		this.tbSlipDetail.entAddTableHeader("debtor_credit_type_name", "����", JLabel.CENTER, 50);
		this.tbSlipDetail.entAddTableHeader("total_amount", "�ݾ�", JLabel.RIGHT, 100);
		this.tbSlipDetail.entAddTableHeader("cash_amount", "���ݱݾ�", JLabel.RIGHT, 100);
		this.tbSlipDetail.entAddTableHeader("non_cash_amount", "��ü�ݾ�", JLabel.RIGHT, 100);

		this.panel1.add(lbTradeDate);
		this.panel1.add(tfTradeDate);

		this.panel2.add(btnInsert);
		this.panel2.add(btnDelete);
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		
		this.centerPanel.add(new JScrollPane(tbSlipMaster));
		this.centerPanel.add(new JScrollPane(tbSlipDetail));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		//this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle(this.userId + "�� �Ϻ���ǥ����");
		
	}
	
	
	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		//BusinessDTO businessDTO = new BusinessDTO();
		//EntTransaction transaction1 = new EntTransaction("AccountMasterBean", "selectEntHashListByUserId", this.userId);
		//businessDTO.addTransaction(transaction1);
		
		//transactionCommitOneByOne(businessDTO);		
	}


	@Override
	public void search() {

		this.transactionKinds = "SEARCH";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("S01101", null);
		transactionCommitOneByOne(businessDTO);
		

	}
	

	
	public void bindGridData(DefaultTableModel model){
		this.tbSlipMaster.entSetTableModel(model);
	}
	
	public void bindGridDataDetail(DefaultTableModel model){
		this.tbSlipDetail.entSetTableModel(model);
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
		else if(this.transactionKinds.equals("SEARCH_DETAIL")){
			afterSearchDetail(businessDTO);
		}
		else if(this.transactionKinds.equals("DELETE")){
			search();
		}
		
	}
	
	public void afterbindComboBox(EntBusiness businessDTO){

	}
	
	public void afterSearch(EntBusiness businessDTO){
		
		EntTransaction transaction1 = businessDTO.getTransactionByIndex(0);
		List results1 = transaction1.getResults();
		DefaultTableModel model = (DefaultTableModel)results1.get(0);
		bindGridData(model);

	}
	
	public void afterSearchDetail(EntBusiness businessDTO){
		
		EntTransaction transaction1 = businessDTO.getTransactionByIndex(0);
		List results1 = transaction1.getResults();
		DefaultTableModel model = (DefaultTableModel)results1.get(0);
		bindGridDataDetail(model);
		
	}


	/**
	 * ������ ���� �׸���Ŭ���� ������ ���ε� �Ѵ�.
	 */
	public void bindUpdateData(){
		
		this.transactionKinds = "SEARCH_DETAIL";
		
		if(tbSlipMaster.getSelectedRow() == -1) return;
		
		String strSlipNo = tbSlipMaster.entGetValueOfSelectedRow("slip_no");
		String strSlipSequence = tbSlipMaster.entGetValueOfSelectedRow("slip_sequence");
		
		
		SlipDetailDTO slipDetail = new SlipDetailDTO();
		slipDetail.setSlipNo(strSlipNo);
		slipDetail.setSlipSequence(Integer.parseInt(strSlipSequence));
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("S01102", slipDetail);
		transactionCommitOneByOne(businessDTO);
	}

	class TableSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())	bindUpdateData();
		}
	}
}
