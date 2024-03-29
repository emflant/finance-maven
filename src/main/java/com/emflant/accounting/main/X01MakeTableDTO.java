package com.emflant.accounting.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.table.CodeDetailDTO;
import com.emflant.accounting.main.component.EntJComboBox;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntCommon;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;
import com.emflant.common.EntTransaction;
import com.emflant.common.SourceFactory;

public class X01MakeTableDTO extends EntScreenMain {
	
	private JPanel northPanel;
	private JPanel panel1;
	private JPanel centerPanel;
	private JLabel lbCodeType;
	private EntJComboBox cbCodeType;
	
	private JLabel lbCode;
	private JTextField tfCode;
	private JLabel lbCodeName;
	private JTextField tfCodeName;
	private JButton btnInsert;
	private JButton btnAllInsert;
	private JTable tbCodeDetailList;
	
	
	public X01MakeTableDTO(JFrame frame, EntBean entBean, String userId){
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
		
		this.lbCodeType = new JLabel("테이블");
		this.cbCodeType = new EntJComboBox();
		this.cbCodeType.addActionListener(new ChangeCodeTypeComboBoxListener());

		this.lbCode = new JLabel("Code");
		this.tfCode = new JTextField();
		this.tfCode.setColumns(4);
		this.lbCodeName = new JLabel("Code Name");
		this.tfCodeName = new JTextField();
		this.tfCodeName.setColumns(8);
		
		this.btnInsert = new JButton("파일생성");
		this.btnAllInsert = new JButton("전체파일생성");
		this.btnInsert.addActionListener(new InsertButtonListener());
		this.btnAllInsert.addActionListener(new AllInsertButtonListener());

		
		
		this.tbCodeDetailList = new JTable();
		
		this.panel1.add(lbCodeType);
		this.panel1.add(cbCodeType);
		this.panel1.add(lbCode);
		this.panel1.add(tfCode);
		this.panel1.add(lbCodeName);
		this.panel1.add(tfCodeName);
		this.panel1.add(btnInsert);
		this.panel1.add(this.btnAllInsert);
		
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbCodeDetailList));
		
		
		this.northPanel.add(panel1);
		//this.northPanel.add(panel2);
		
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

		setTitle("[X01] 테이블 정보 조회");

	}
	

	public void insert(String tableName){
		
		this.transactionKinds = "INSERT";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("X01103", tableName);
		transactionCommitOneByOne(businessDTO);
		
	}



	class InsertButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) 
		{
			insert(cbCodeType.entGetCodeOfSelectedItem());
			//tbCodeDetailList.setModel(codeDetailBean.selectTableModelByCodeType(getCodeType()));
		}
	}

	class AllInsertButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) 
		{
			List<CodeDetailDTO> list = cbCodeType.getCodeDetailList();
			
			for(CodeDetailDTO codeDetailDTO : list){
				insert(codeDetailDTO.getCode());
			}

		}
	}

	class ChangeCodeTypeComboBoxListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			search();
		}
		
	}
	
	


	@Override
	public void bindComboBox() {
		
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("X01101", null);

		transactionCommitOneByOne(businessDTO);

	}


	@Override
	public void search() {

		this.transactionKinds = "SEARCH";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("X01102", this.cbCodeType.entGetCodeOfSelectedItem());
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
		this.cbCodeType.entSetEntHashList(ehlCodeType);
		
	}
	
	public void afterSearch(EntBusiness businessDTO){
	
		EntTransaction transaction = businessDTO.getFirstTransaction();
		List results = transaction.getResults();
		DefaultTableModel model = (DefaultTableModel)results.get(0);
		bindGridData(model);
	}
	
	public void afterInsert(EntBusiness businessDTO){
		
		EntTransaction transaction = businessDTO.getFirstTransaction();
		List results = transaction.getResults();
		List result01 = (List)results.get(0);
		SourceFactory sf = new SourceFactory();
		sf.print(result01, (String)transaction.getMethodParam());
	}
	
	public void bindGridData(DefaultTableModel model){
		this.tbCodeDetailList.setModel(model);
	}
}
