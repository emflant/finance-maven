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

import com.emflant.accounting.business.bean.CodeDetailBean;
import com.emflant.accounting.business.bean.CodeMasterBean;
import com.emflant.accounting.dto.table.CodeDetailDTO;
import com.emflant.accounting.dto.table.CodeMasterDTO;
import com.emflant.accounting.main.component.EntJComboBox;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntException;
import com.emflant.common.EntCommon;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;
import com.emflant.common.EntTransaction;

public class C02CodeDetailMain extends EntScreenMain {
	
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
	private JButton btnDelete;
	private JTable tbCodeDetailList;
	
	
	public C02CodeDetailMain(JFrame frame, EntBean entBean, String userId){
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
		this.cbCodeType = new EntJComboBox();
		this.cbCodeType.addActionListener(new ChangeCodeTypeComboBoxListener());

		this.lbCode = new JLabel("Code");
		this.tfCode = new JTextField();
		this.tfCode.setColumns(8);
		this.lbCodeName = new JLabel("Code Name");
		this.tfCodeName = new JTextField();
		this.tfCodeName.setColumns(8);
		
		this.btnInsert = new JButton("등록");
		this.btnInsert.addActionListener(new InsertButtonListener());
		
		this.btnDelete = new JButton("삭제");
		this.btnDelete.addActionListener(new DeleteButtonListener());
		
		
		
		this.tbCodeDetailList = new JTable();
		
		this.panel1.add(lbCodeType);
		this.panel1.add(cbCodeType);
		this.panel1.add(lbCode);
		this.panel1.add(tfCode);
		this.panel1.add(lbCodeName);
		this.panel1.add(tfCodeName);
		this.panel1.add(btnInsert);
		this.panel1.add(btnDelete);
		
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbCodeDetailList));
		
		
		this.northPanel.add(panel1);
		//this.northPanel.add(panel2);
		
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

		setTitle(this.userId + "의 코드내역");

	}
	

	public void insertCodeDetail(){
		
		this.transactionKinds = "INSERT";
		
		int nResult = showConfirmDialog("등록하시겠습니까?");
		if(nResult != 0) return;
		
		CodeDetailDTO codeDetail = new CodeDetailDTO();
		codeDetail.setCodeType(this.cbCodeType.entGetCodeOfSelectedItem());
		codeDetail.setCode(this.tfCode.getText());
		codeDetail.setCodeName(this.tfCodeName.getText());
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("C02201", codeDetail);
		transactionCommitOneByOne(businessDTO);
		
		
			//EntCommon.isNull(codeDetail);
			//this.codeDetailBean.insert(codeDetail);

	}
	
	
	
	public void deleteCodeDetail(){
		
		int nSelectedRow = this.tbCodeDetailList.getSelectedRow();
		
		if(nSelectedRow == -1){
			//throw new EntException("삭제하려는 코드를 선택하세요.");
		}
		
		CodeDetailDTO codeDetail = new CodeDetailDTO();
		
		codeDetail.setCodeType(this.cbCodeType.entGetCodeOfSelectedItem());
		codeDetail.setCode((String)this.tbCodeDetailList.getValueAt(nSelectedRow, 0));
		//this.codeDetailBean.delete(codeDetail);

	}
	class InsertButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) 
		{
			insertCodeDetail();
			//tbCodeDetailList.setModel(codeDetailBean.selectTableModelByCodeType(getCodeType()));
		}
	}
	class DeleteButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) 
		{
			deleteCodeDetail();
			//tbCodeDetailList.setModel(codeDetailBean.selectTableModelByCodeType(getCodeType()));
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
		businessDTO.addTransaction("C02101", null);

		transactionCommitOneByOne(businessDTO);

	}


	@Override
	public void search() {

		this.transactionKinds = "SEARCH";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("C02102", this.cbCodeType.entGetCodeOfSelectedItem());
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
			search();
		}
		else if(this.transactionKinds.equals("DELETE")){
			search();
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
	
	public void bindGridData(DefaultTableModel model){
		this.tbCodeDetailList.setModel(model);
	}
}
