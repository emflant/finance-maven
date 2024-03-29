package com.emflant.accounting.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.screen.A03CreditCardPaymentMainInsert01DTO;
import com.emflant.accounting.dto.screen.A04DepositAccountMainInsert01DTO;
import com.emflant.accounting.dto.table.SlipMasterDTO;
import com.emflant.accounting.main.component.EntJComboBox;
import com.emflant.accounting.main.component.EntJTable;
import com.emflant.common.EntBusiness;
import com.emflant.common.EntBean;
import com.emflant.common.EntHashList;
import com.emflant.common.EntScreenMain;
import com.emflant.common.EntTransaction;
import com.emflant.url.HttpUrlConnection;

public class D01BatchCreditCardPaymentsMain extends EntScreenMain {

	private JPanel centerPanel;
	private JPanel northPanel;
	private JPanel southPanel;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private JLabel lbStatus;
	private EntJComboBox cbStatus;
	
	
	private JButton btnSearch;
	private JButton btnInsert;
	private JButton btnUpdate;
	private EntJTable tbAccountDetail;

	
	public D01BatchCreditCardPaymentsMain(JFrame frame, EntBean entBean, String userId){
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
		
		
		this.lbStatus = new JLabel("처리구분");
		this.cbStatus = new EntJComboBox();
		this.cbStatus.addActionListener(new CbAccountChangeListener());
		
		this.btnSearch = new JButton("조회");
		this.btnSearch.addActionListener(new SearchButtonListener());
		this.btnInsert = new JButton("배치실행");
		this.btnInsert.addActionListener(new InsertButtonListener());
		this.btnUpdate = new JButton("초기화실행");
		this.btnUpdate.addActionListener(new UpdateButtonListener());
		
		this.tbAccountDetail = new EntJTable();
		
		//그리드의 헤더정보를 정의한다.
		this.tbAccountDetail.entAddTableHeader("key", "key", JLabel.CENTER, 120);
		this.tbAccountDetail.entAddTableHeader("status", "구분", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("trade_type", "비용", JLabel.CENTER, 50);
		this.tbAccountDetail.entAddTableHeader("credit_card", "신용카드", JLabel.CENTER, 120);
		this.tbAccountDetail.entAddTableHeader("reckon_date", "거래일자", JLabel.CENTER, 120);
		this.tbAccountDetail.entAddTableHeader("reckon_time", "시간", JLabel.CENTER, 80);
		this.tbAccountDetail.entAddTableHeader("total_amount", "전체금액", JLabel.RIGHT, 120);
		this.tbAccountDetail.entAddTableHeader("cash_amount", "현금금액", JLabel.RIGHT, 120);
		this.tbAccountDetail.entAddTableHeader("remarks", "적요", JLabel.LEFT, 260);

		this.panel1.add(lbStatus);
		this.panel1.add(cbStatus);
		this.panel1.add(btnSearch);
		this.panel2.add(btnInsert);
		this.panel2.add(btnUpdate);
		
		this.northPanel.add(panel1);
		this.southPanel.add(panel2);
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbAccountDetail));
		
		this.frame.getContentPane().add(BorderLayout.NORTH, this.northPanel);
		this.frame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		setTitle("[D01] 일괄카드승인");
		
	}
	

	
	public void insert(){
		
		int nResult = showConfirmDialog("일괄처리 하시겠습니까?");
		if(nResult != 0) return;
		
		DefaultTableModel model = (DefaultTableModel)this.tbAccountDetail.getModel();
		
		int nRows = model.getRowCount();
		
		A03CreditCardPaymentMainInsert01DTO inputDTO = new A03CreditCardPaymentMainInsert01DTO();
		
		String strStatus = null;
		int nResultCnt = 0;
		
		for(int i=0;i<nRows;i++){
			strStatus = model.getValueAt(i, model.findColumn("status")).toString();
			//Logger.debug("status :: "+model.getValueAt(i, model.findColumn("status")));
			if(!strStatus.equals("0")) continue;
			
			String strCreditCard = model.getValueAt(i, model.findColumn("credit_card")).toString();
			if(strCreditCard.equals("씨티카드")){
				inputDTO.setAccountNo("2100000012");
			} else if(strCreditCard.equals("롯데카드")){
				inputDTO.setAccountNo("2100000013");
			}
			
			inputDTO.setReckonDate(model.getValueAt(i, model.findColumn("reckon_date")).toString());
			inputDTO.setTradeType(model.getValueAt(i, model.findColumn("trade_type")).toString());
			inputDTO.setTotalAmount(new BigDecimal(model.getValueAt(i, model.findColumn("total_amount")).toString()));
			inputDTO.setCashAmount(new BigDecimal(model.getValueAt(i, model.findColumn("cash_amount")).toString()));
			inputDTO.setRemarks(model.getValueAt(i, model.findColumn("remarks")).toString());
			
			//카드승인처리한다.
			boolean result = insert(inputDTO, model.getValueAt(i, model.findColumn("key")).toString());
			
			if(result) nResultCnt++;
		}
		
		if(nResultCnt == 0){
			showMessageDialog("처리한 대상이 없습니다.");
		} else {
			showMessageDialog("정상적으로 "+nResultCnt+"건 처리했습니다.");
		}
		
		
		search();
	}
	
	

	
	public boolean insert(A03CreditCardPaymentMainInsert01DTO inputDTO, String strKey){

		this.transactionKinds = "INSERT";
		
		if(inputDTO.getTotalAmount().compareTo(BigDecimal.ZERO) == 0){
			showMessageDialog("거래금액이 0입니다.");
			return false;
		}
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.setIsOneSlip(true);
		businessDTO.setIsRemoveMessage(true);
		businessDTO.addTransaction("A03201", inputDTO);
		
		BigDecimal bdNonCashAmount = inputDTO.getTotalAmount().subtract(inputDTO.getCashAmount());
		
		//연동거래일때만 거래유형을 넣는다.
		if(bdNonCashAmount.compareTo(BigDecimal.ZERO) == 1){
			SlipMasterDTO slipMaster = new SlipMasterDTO();
			slipMaster.setSlipAmount(bdNonCashAmount);
			slipMaster.setDebtorAmount(bdNonCashAmount);
			slipMaster.setCreditAmount(BigDecimal.ZERO);
			slipMaster.setUserId(this.userId);
			slipMaster.setRegisterUserId(this.userId);
			slipMaster.setLastRegisterUserId(this.userId);
			
			slipMaster.addDebtorAmount(inputDTO.getTradeType()
					, bdNonCashAmount, BigDecimal.ZERO);
			
			businessDTO.addTransaction("S03201", slipMaster);
		}
		
		//현금을 받으면 바로 지갑으로 입금처리한다.
		if(inputDTO.getCashAmount().compareTo(BigDecimal.ZERO) == 1){
			A04DepositAccountMainInsert01DTO inputDTO3 = new A04DepositAccountMainInsert01DTO();
			inputDTO3.setAccountNo("1900000005");
			inputDTO3.setReckonDate(inputDTO.getReckonDate());
			inputDTO3.setTotalAmount(inputDTO.getCashAmount());
			inputDTO3.setCashAmount(inputDTO.getCashAmount());
			inputDTO3.setRemarks(inputDTO.getRemarks());
			inputDTO3.setLinkType("0");
			
			businessDTO.addTransaction("A04201", inputDTO3);
		}
		
		
		businessDTO.addTransaction("D01201", strKey);

		
		return transactionCommitAtOnce(businessDTO);
	}
	

	
	public void update(){
		
		int nResult = showConfirmDialog("일괄처리 하시겠습니까?");
		if(nResult != 0) return;
		
		DefaultTableModel model = (DefaultTableModel)this.tbAccountDetail.getModel();
		
		int nRows = model.getRowCount();
		
		String strStatus = null;


		for(int i=0;i<nRows;i++){
			EntBusiness businessDTO = new EntBusiness();
			businessDTO.setIsRemoveMessage(true);
			strStatus = model.getValueAt(i, model.findColumn("status")).toString();
			//Logger.debug("status :: "+model.getValueAt(i, model.findColumn("status")));
			if(strStatus.equals("0")) continue;

			businessDTO.addTransaction("D01202"
					, model.getValueAt(i, model.findColumn("key")).toString());
			transactionCommitAtOnce(businessDTO);
		}
		
		
		
		showMessageDialog("처리 완료했습니다.");

		
		search();
	}
	
	

	
	
	@Override
	public void bindComboBox() {
		this.transactionKinds = "BIND_COMBOBOX";
		
		EntBusiness businessDTO = new EntBusiness();
		businessDTO.addTransaction("C00102", "10");
		
		transactionCommitOneByOne(businessDTO);		
		
	}


	@Override
	public void search() {
		this.transactionKinds = "SEARCH";
		
		String strStatus = this.cbStatus.entGetCodeOfSelectedItem();
		
		if(strStatus.equals("*")) strStatus = "all";
		
		HttpUrlConnection httpUrl = new HttpUrlConnection();
		bindGridData(httpUrl.getCardPaymentDetails(strStatus));
	}
	

	
	public void bindGridData(DefaultTableModel model){
		this.tbAccountDetail.entSetTableModel(model);
	}
	
	
	
	public void changeCbAccount(){
		//search();
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

	class UpdateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			update();
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

		}
		
	}
	
	public void afterbindComboBox(EntBusiness businessDTO){
		
		List<EntTransaction> transactions = businessDTO.getTransactions();
		
		EntTransaction transaction2 = transactions.get(0);
		EntHashList ehlStatus = (EntHashList) transaction2.getFirstResult();
		this.cbStatus.entSetEntHashList(ehlStatus);
		this.cbStatus.entSetSelectedItemByCode("0");
	}
	
	public void afterSearch(EntBusiness businessDTO){
		
	}




}
