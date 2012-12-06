package com.emflant.accounting.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.table.AccountMasterDTO;
import com.emflant.accounting.main.component.EntJComboBox;
import com.emflant.accounting.main.component.EntJTable;
import com.emflant.accounting.main.component.EntJTextFieldForAmount;
import com.emflant.accounting.main.component.EntJTextFieldForDate;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntDate;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;

/**
 * �ŷ�������ȸ ȭ��
 * @author home
 *
 */
public class A02AccountDetailInquiryMain extends EntScreenMain {

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
	
	
	private EntJTable tbAccountDetail;
	
	
	public A02AccountDetailInquiryMain(JFrame frame, EntBean entBean, String userId){
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
		this.southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.lbAccount = new JLabel("����");
		this.cbAccount = new EntJComboBox();
		this.cbAccount.addActionListener(new CbAccountChangeListener());
		
		this.lbBalance = new JLabel("       �ܾ�");
		this.tfBalance = new EntJTextFieldForAmount(10);
		this.tfBalance.setEnabled(false);
		
		this.lbTradeDate = new JLabel("       �������");
		this.tfTradeDate = new EntJTextFieldForDate(7);
				
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
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbAccountDetail));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle("[A02] �ŷ�������ȸ");
		
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
		EntHashList ehlAccount = (EntHashList)businessDTO.getResultByTransactionIndex(0);
		this.cbAccount.entSetEntHashList(ehlAccount);
	}
	
	public void afterSearch(EntBusiness businessDTO){
		
		DefaultTableModel model = (DefaultTableModel)businessDTO.getResultByTransactionIndex(0);
		this.tbAccountDetail.entSetTableModel(model);
		
		AccountMasterDTO accountMaster = (AccountMasterDTO)businessDTO.getResultByTransactionIndex(1);;
		this.tfBalance.setValue(accountMaster.getBalance());
	}




}
