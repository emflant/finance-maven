package com.emflant.accounting.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.table.CodeDetailDTO;
import com.emflant.accounting.dto.table.SlipDetailDTO;
import com.emflant.accounting.main.A03CreditCardPaymentMain.CbAccountChangeListener;
import com.emflant.accounting.main.B01SlipDetailInquiryByDaliyMain.TableSelectionListener;
import com.emflant.accounting.main.component.EntJTable;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntCommon;
import com.emflant.common.EntDate;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;
import com.emflant.common.EntTransaction;

public class B02SlipDetailInquiryByAccountTypeMain extends EntScreenMain {
	private JPanel centerPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private JLabel lbAccountType;
	private JComboBox cbAccountType;
	private EntHashList entHashList;
	
	private JLabel lbTradeDate;
	private JTextField tfTradeDate;

	private JButton btnInsert;
	private JButton btnDelete;
	private EntJTable tbSlipDetail;
	
	public B02SlipDetailInquiryByAccountTypeMain(JFrame frame, EntBean entBean, String userId){
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
		
		this.lbAccountType = new JLabel("��������");
		this.cbAccountType = new JComboBox();
		this.cbAccountType.addActionListener(new CbAccountChangeListener());
		
		this.lbTradeDate = new JLabel("��������");
		this.tfTradeDate = new JTextField(7);
		this.tfTradeDate.setText(EntDate.getToday());
		this.tfTradeDate.setHorizontalAlignment(JTextField.CENTER);

		this.tbSlipDetail = new EntJTable();
		
		this.btnInsert = new JButton("���");
		//this.btnInsert.addActionListener(new InsertButtonListener());
		this.btnDelete = new JButton("����");
		//this.btnDelete.addActionListener(new DeleteButtonListener());


		//��ǥ������ �׸����� ��������� �����Ѵ�.
		this.tbSlipDetail.entAddTableHeader("slip_no", "��ǥ��ȣ", JLabel.CENTER, 120);
		this.tbSlipDetail.entAddTableHeader("slip_sequence", "#", JLabel.CENTER, 30);
		this.tbSlipDetail.entAddTableHeader("debtor_account_type_name", "��������", JLabel.LEFT, 100);
		this.tbSlipDetail.entAddTableHeader("debtor_bs_pl_detail_type_name", "����", JLabel.CENTER, 30);
		this.tbSlipDetail.entAddTableHeader("debtor_amount", "�����ݾ�", JLabel.RIGHT, 100);
		this.tbSlipDetail.entAddTableHeader("credit_account_type_name", "�뺯����", JLabel.LEFT, 100);
		this.tbSlipDetail.entAddTableHeader("credit_bs_pl_detail_type_name", "����", JLabel.CENTER, 30);
		this.tbSlipDetail.entAddTableHeader("credit_amount", "�뺯�ݾ�", JLabel.RIGHT, 100);
		
		this.panel1.add(lbAccountType);
		this.panel1.add(cbAccountType);
		this.panel1.add(lbTradeDate);
		this.panel1.add(tfTradeDate);

		this.panel2.add(btnInsert);
		this.panel2.add(btnDelete);
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		
		this.centerPanel.add(new JScrollPane(tbSlipDetail));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		//this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle(this.userId + "�� �Ϻ���ǥ����");
		
	}
	
	
	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("S02101", this.userId);
		
		transactionCommitOneByOne(businessDTO);		
	}


	@Override
	public void search() {

		this.transactionKinds = "SEARCH";
		
		String strCode = ((CodeDetailDTO)cbAccountType.getSelectedItem()).getCode();
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("S02102", strCode);
		transactionCommitOneByOne(businessDTO);

	}
	

	
	public void bindGridData(DefaultTableModel model){
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

		else if(this.transactionKinds.equals("DELETE")){
			search();
		}
		
	}
	
	public void afterbindComboBox(EntBusiness businessDTO){
		List<EntTransaction> transactions = businessDTO.getTransactions();
		
		EntTransaction transaction1 = transactions.get(0);
		this.entHashList = (EntHashList) transaction1.getFirstResult();
		
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel(this.entHashList.getList().toArray());
		this.cbAccountType.setModel(dcbm);
	}
	
	public void afterSearch(EntBusiness businessDTO){
		
		EntTransaction transaction1 = businessDTO.getTransactionByIndex(0);
		List results1 = transaction1.getResults();
		DefaultTableModel model = (DefaultTableModel)results1.get(0);
		bindGridData(model);

	}

	public void changeCbAccount(){
		search();
	}
	
	class CbAccountChangeListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			changeCbAccount();
		}
		
	}
	


}
