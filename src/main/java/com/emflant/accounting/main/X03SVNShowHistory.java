package com.emflant.accounting.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.table.CodeDetailDTO;
import com.emflant.accounting.main.component.EntJComboBox;
import com.emflant.accounting.main.component.EntJTable;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;
import com.emflant.common.EntTransaction;
import com.emflant.common.SourceFactory;

public class X03SVNShowHistory extends EntScreenMain {
	
	private JPanel northPanel;
	private JPanel panel1;
	private JPanel centerPanel;
	private JLabel lbCodeType;
	private EntJComboBox cbBeanType;
	
	private JLabel lbCode;
	private JTextField tfCode;
	private JLabel lbCodeName;
	private JTextField tfCodeName;
	private JButton btnInsert;
	private JButton btnAllInsert;
	private EntJTable tbCodeDetailList;
	
	
	public X03SVNShowHistory(JFrame frame, EntBean entBean, String userId){
		this.frame = frame;
		this.entBean = entBean;
		this.userId = userId;
	}

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
		
		this.lbCodeType = new JLabel("���̺�");
		this.cbBeanType = new EntJComboBox();
		//this.cbBeanType.addActionListener(new X03ChangeBeanTypeComboBoxListener());

		this.lbCode = new JLabel("Code");
		this.tfCode = new JTextField();
		this.tfCode.setColumns(4);
		this.lbCodeName = new JLabel("Code Name");
		this.tfCodeName = new JTextField();
		this.tfCodeName.setColumns(8);

		
		this.tbCodeDetailList = new EntJTable();
		
		this.tbCodeDetailList.entAddTableHeader("date", "��¥", JLabel.CENTER, 150);
		this.tbCodeDetailList.entAddTableHeader("version", "����", JLabel.LEFT, 30);
		this.tbCodeDetailList.entAddTableHeader("comment", "�޸�", JLabel.LEFT, 500);
		
		
		this.panel1.add(lbCodeType);
		this.panel1.add(cbBeanType);
		this.panel1.add(lbCode);
		this.panel1.add(tfCode);
		this.panel1.add(lbCodeName);
		this.panel1.add(tfCodeName);
		//this.panel1.add(btnInsert);
		//this.panel1.add(this.btnAllInsert);
		
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbCodeDetailList));
		
		
		this.northPanel.add(panel1);
		//this.northPanel.add(panel2);
		
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

		setTitle("[X03] svn �̷� ��ȸ");

	}
	





	class X03ChangeBeanTypeComboBoxListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			search();
		}
		
	}
	
	


	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		//EntBusiness businessDTO = new EntBusiness();
		//businessDTO.addTransaction("X02101");
		//transactionCommitOneByOne(businessDTO);

	}


	@Override
	public void search() {

		this.transactionKinds = "SEARCH";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("X03101");
		transactionCommitOneByOne(businessDTO);
		
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
			afterInsert(businessDTO);
		}
	}
	
	
	public void afterbindComboBox(EntBusiness businessDTO){
		
		List<EntTransaction> transactions = businessDTO.getTransactions();
		
		EntTransaction transaction1 = transactions.get(0);
		EntHashList ehlCodeType = (EntHashList) transaction1.getFirstResult();
		this.cbBeanType.entSetEntHashList(ehlCodeType);
		
	}
	
	public void afterSearch(EntBusiness businessDTO){
		DefaultTableModel model = (DefaultTableModel)businessDTO.getResultByTransactionIndex(0);
		this.tbCodeDetailList.entSetTableModel(model);
		
//		EntTransaction transaction = businessDTO.getFirstTransaction();
//		List results = transaction.getResults();
//		DefaultTableModel model = (DefaultTableModel)results.get(0);
//		bindGridData(model);
	}
	
	public void afterInsert(EntBusiness businessDTO){
		
		EntTransaction transaction = businessDTO.getFirstTransaction();
		List results = transaction.getResults();
		List result01 = (List)results.get(0);
		SourceFactory sf = new SourceFactory();
		sf.print2(result01, (String)transaction.getMethodParam());
	}
	
	public void bindGridData(DefaultTableModel model){
		this.tbCodeDetailList.setModel(model);
	}
}
