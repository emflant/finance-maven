package com.emflant.accounting.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import com.emflant.common.EntBean;
import com.emflant.common.EntMessage;
import com.emflant.common.EntLogger;
import com.emflant.common.EntMessage.EntMessageType;

public class Z00LoginMain {

	private String userId;
	private JFrame frame;
	private EntBean entBean;
	
	private String currentScreen;
	
	public void init(){
		
		this.userId = "emflant";
		this.frame = new JFrame();
		
		JMenuBar mb = new JMenuBar();
		JMenu screen = new JMenu("Account");
		
		JMenuItem menuItemA01 = new JMenuItem("[A01] 계좌등록");
		JMenuItem menuItemA02 = new JMenuItem("[A02] 거래내역조회");
		JMenuItem menuItemA03 = new JMenuItem("[A03] 신용카드승인");
		JMenuItem menuItemA06 = new JMenuItem("[A06] 신용카드결제");
		JMenuItem menuItemA04 = new JMenuItem("[A04] 입금");
		JMenuItem menuItemA05 = new JMenuItem("[A05] 출금");
		JMenuItem menuItemA09 = new JMenuItem("[A09] 계정취소/정정");
		JMenuItem menuItemA07 = new JMenuItem("[A07] 은행계좌이체");
		
		

		menuItemA01.addActionListener(new MenuItemListener("A01AccountMasterMain"));
		menuItemA02.addActionListener(new MenuItemListener("A02AccountDetailInquiryMain"));
		menuItemA03.addActionListener(new MenuItemListener("A03CreditCardPaymentMain"));
		menuItemA04.addActionListener(new MenuItemListener("A04DepositAccountMain"));
		menuItemA05.addActionListener(new MenuItemListener("A05WithdrawAccountMain"));
		menuItemA09.addActionListener(new MenuItemListener("A09ReverseTransactionMain"));
		menuItemA06.addActionListener(new MenuItemListener("A06CreditCardRepaymentMain"));
		menuItemA07.addActionListener(new MenuItemListener("A07TransferAccountsMain"));
		
		
		
		//X01MakeSource
		screen.add(menuItemA01);
		screen.add(menuItemA02);
		screen.add(menuItemA03);
		screen.add(menuItemA06);
		screen.add(menuItemA04);
		screen.add(menuItemA05);
		screen.add(menuItemA07);
		screen.add(menuItemA09);

		JMenu screen3 = new JMenu("Slip");
		JMenuItem menuItemB01 = new JMenuItem("[B01] 일별전표내역");
		JMenuItem menuItemB02 = new JMenuItem("[B02] 계정별전표내역");
		JMenuItem menuItemB03 = new JMenuItem("[B03] 전표등록");
		JMenuItem menuItemB04 = new JMenuItem("[B04] 총계정원장 조회");
		
		menuItemB01.addActionListener(new MenuItemListener("B01SlipDetailInquiryByDaliyMain"));
		menuItemB02.addActionListener(new MenuItemListener("B02SlipDetailInquiryByAccountTypeMain"));
		menuItemB03.addActionListener(new MenuItemListener("B03SlipRegistrationMain"));
		menuItemB04.addActionListener(new MenuItemListener("B04WholeAccountInquiryFromSlipDetail"));
		
		screen3.add(menuItemB01);
		screen3.add(menuItemB02);
		screen3.add(menuItemB03);
		screen3.add(menuItemB04);

		
		JMenu screen2 = new JMenu("Common");
		
		JMenuItem menuItem3 = new JMenuItem("[C01] 코드원장");
		JMenuItem menuItem4 = new JMenuItem("[C02] 코드내역");
		JMenuItem menuItemX01 = new JMenuItem("[X01] 테이블 정보 조회");
		JMenuItem menuItemX02 = new JMenuItem("[X02] 거래코드 정보 조회");
		JMenuItem menuItemX03 = new JMenuItem("[X03] SVN 이력 조회");
		
		menuItem3.addActionListener(new MenuItemListener("C01CodeMasterInsert"));
		menuItem4.addActionListener(new MenuItemListener("C02CodeDetailMain"));
		menuItemX01.addActionListener(new MenuItemListener("X01MakeTableDTO"));
		menuItemX02.addActionListener(new MenuItemListener("X02MakeRemoteInterface"));
		menuItemX03.addActionListener(new MenuItemListener("X03SVNShowHistory"));
		
		JMenu menuE = new JMenu("Calculate");
		JMenuItem menuItemE01 = new JMenuItem("[E01] 코드원장");
		
		screen2.add(menuItem3);
		screen2.add(menuItem4);
		screen2.add(menuItemX01);
		screen2.add(menuItemX02);
		screen2.add(menuItemX03);
		
		JMenu screen4 = new JMenu("Batch");
		JMenuItem menuItemD01 = new JMenuItem("[D01] 일괄카드승인");
		menuItemD01.addActionListener(new MenuItemListener("D01BatchCreditCardPaymentsMain"));
		screen4.add(menuItemD01);
		
		mb.add(screen);
		mb.add(screen3);
		mb.add(screen2);
		mb.add(screen4);
		this.frame.setJMenuBar(mb);
		
		this.frame.addWindowListener(new FrameWindowListener());
		this.frame.setSize(800, 600);
		this.frame.setVisible(true);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	
	class MenuItemListener implements ActionListener {
		
		private String screenName;
		
		public MenuItemListener(String screenName){
			this.screenName = screenName;
		}

		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e) {
			
			
			if(currentScreen != null && currentScreen.equals(this.screenName)){
				EntLogger.debug("같은 메뉴는 skip.");
				return;
			} else {
				EntLogger.debug("메뉴 : "+this.screenName);
			}
			
			currentScreen = this.screenName;
			frame.getContentPane().removeAll();
			
			
	        try {

	            Class clazz = Class.forName("com.emflant.accounting.main."+this.screenName);
	            Class[] constructorTypes = { JFrame.class, EntBean.class, String.class };
	            Constructor constructor = clazz.getConstructor(constructorTypes);
	            
	            Object[] constructorParams = { frame, entBean, userId };
	            Object obj = constructor.newInstance(constructorParams);

	            Class[] paramTypes = { String.class };
	            Method method = clazz.getMethod("execute", paramTypes);
	            
				Object[] parameters = { this.screenName };
				method.invoke(obj, parameters);
				
	            
	        } 
	        
	        //EntException 발생시에 결국 InvocationTargetException 로 연관되기에 이곳으로 빠진다.
	        catch (InvocationTargetException error) {
	        	error.printStackTrace();
	        } 
	        

	        catch (Exception error) {
	        	error.printStackTrace();
	        	new EntMessage(EntMessageType.ERROR, "연결된 화면이 없습니다.").showMessageDialog(frame);
			} finally {
				
			}

		}
	}
	
	
	class FrameWindowListener implements WindowListener {

		public void windowActivated(WindowEvent e) {}

		public void windowClosed(WindowEvent e) {}

		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			entBean.closeConnection();
			e.getWindow().setVisible(false);
			e.getWindow().dispose();
			System.exit(0);
		}

		public void windowDeactivated(WindowEvent e) {}

		public void windowDeiconified(WindowEvent e) {}

		public void windowIconified(WindowEvent e) {}

		public void windowOpened(WindowEvent e) {}
		
	}
	
	public void loginDialog(){
		EntMessage message = new EntMessage(EntMessageType.INFORMATION, "로그인정보");
		String ip = message.showInputDialog(frame);
		if(ip != null){
			this.entBean = new EntBean(ip);
		} else {
			this.entBean = new EntBean();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Z00LoginMain z00LoginMain = new Z00LoginMain();
		z00LoginMain.init();
		z00LoginMain.loginDialog();
	}

}
