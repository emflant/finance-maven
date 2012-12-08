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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.table.CodeMasterDTO;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntScreenMain;
import com.emflant.common.EntTransaction;


public class C01CodeMasterInsert extends EntScreenMain {

	
	private JPanel northPanel;
	private JPanel panel1;
	private JPanel centerPanel;
	private JLabel lbCodeType;
	private JTextField tfCodeType;
	private JLabel lbCodeTypeName;
	private JTextField tfCodeTypeName;
	private JButton btnInsert;
	private JTable tbCodeTypeList;
	
	//private CodeMasterBean codeMasterBean;
	

	
	public C01CodeMasterInsert(JFrame frame, EntBean entBean, String userId){
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
		
		this.lbCodeType = new JLabel("Code Type");
		this.tfCodeType = new JTextField();
		this.tfCodeType.setColumns(5);
		
		this.lbCodeTypeName = new JLabel("Code Type Name");
		this.tfCodeTypeName = new JTextField();
		this.tfCodeTypeName.setColumns(8);
		
		
		this.btnInsert = new JButton("등록");
		this.btnInsert.addActionListener(new InsertButtonListener());
		
		
		this.tbCodeTypeList = new JTable();
		
		this.panel1.add(lbCodeType);
		this.panel1.add(tfCodeType);
		this.panel1.add(lbCodeTypeName);
		this.panel1.add(tfCodeTypeName);
		this.panel1.add(btnInsert);
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbCodeTypeList));
		
		
		this.northPanel.add(panel1);
		//this.northPanel.add(panel2);
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		setTitle(this.userId + "의 코드원장");

	}
	
	
	class InsertButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) 
		{
			insertCodeMaster();
		}
		
	}
	
	

	public void insertCodeMaster(){
		this.transactionKinds = "INSERT";
		
		int nResult = showConfirmDialog("등록하시겠습니까?");
		if(nResult != 0) return;
		
		CodeMasterDTO codeMaster = new CodeMasterDTO();
		
		codeMaster.setCodeType(this.tfCodeType.getText());
		codeMaster.setCodeTypeName(this.tfCodeTypeName.getText());
		codeMaster.setRemarks(null);
		codeMaster.setUseYn("Y");

		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("C01201", codeMaster);
		transactionCommitOneByOne(businessDTO);
		//EntCommon.isNull(codeMaster);
		//this.codeMasterBean.insert(codeMaster);
	}

	@Override
	public void bindComboBox() {
		// TODO Auto-generated method stub
		
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
		else if(this.transactionKinds.equals("DELETE")){
			search();
		}
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		this.transactionKinds = "SEARCH";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("C01101", null);
		transactionCommitOneByOne(businessDTO);
		
		
		//DefaultTableModel model = this.codeMasterBean.selectAll();
	}
	
	
	public void afterbindComboBox(EntBusiness businessDTO){
		
	}
	
	public void afterSearch(EntBusiness businessDTO){
		EntTransaction transaction = businessDTO.getFirstTransaction();
		List results = transaction.getResults();
		DefaultTableModel model = (DefaultTableModel)results.get(0);
		bindGridData(model);
	}
	
	public void bindGridData(DefaultTableModel model){
		this.tbCodeTypeList.setModel(model);
	}
}
