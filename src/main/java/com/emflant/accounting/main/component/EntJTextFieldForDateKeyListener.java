package com.emflant.accounting.main.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import com.emflant.common.EntLogger;



class EntJTextFieldForDateKeyListener implements KeyListener {

	private JTextField jTextField;
	private final int DATE_LENGTH = 10;
	
	public EntJTextFieldForDateKeyListener(JTextField jTextField){
		this.jTextField = jTextField;
	}
	
	public void keyPressed(KeyEvent e) {
		
		//EntLogger.debug(this.jTextField.getSelectedText());
		String strOrigin = this.jTextField.getText();
		String strInput = KeyEvent.getKeyText(e.getKeyCode());
		String strSelectedText = this.jTextField.getSelectedText();
		StringBuilder sb = new StringBuilder(strOrigin);
		int nStart = this.jTextField.getSelectionStart();

		char chr = e.getKeyChar();
		int  key = e.getKeyCode();
		//EntLogger.debug("input : "+key+", "+chr);
		
		if(e.getKeyCode() == 10){
			this.jTextField.transferFocus();
			return;
		}
		
		//���ýÿ� �̵��� �����ϵ���
		if(e.getKeyCode()==37 || e.getKeyCode()==39){
			return;
		}
		
		if(strSelectedText != null){
			
			int nEnd = this.jTextField.getSelectionEnd();
			
			//EntLogger.debug(nStart+","+nEnd);
			for(int i=nStart;i<nEnd;i++){
				
				if(i==4 || i== 7){
					sb.replace(i, i+1, "-");
				} else {
					sb.replace(i, i+1, "_");
				}
				//EntLogger.debug(sb.toString());
			}
			this.jTextField.setCaretPosition(nStart);
		}
		
		if(!(chr == '0' || chr == '1' || chr == '2' || chr == '3' || chr == '4'
			|| chr == '5' || chr == '6' || chr == '7' || chr == '8' || chr == '9'
			|| key == 8 
			|| key == 127)){
			
			return;
		}
		
		
		
		
		//EntLogger.debug(strInput+":"+e.getKeyChar());
		
//		if(strOrigin.equals("") 
//				|| e.getKeyCode() == 37 		//left key
//				|| e.getKeyCode() == 39			//right key
//				|| e.getKeyCode() == 16){			//shift key
//			return;
//		}
		

		
//		if(strSelectedText != null){
//			
//			int nEnd = this.jTextField.getSelectionEnd();
//			
//			//EntLogger.debug(nStart+","+nEnd);
//			for(int i=nStart;i<nEnd;i++){
//				
//				if(i==4 || i== 7){
//					sb.replace(i, i+1, "-");
//				} else {
//					sb.replace(i, i+1, "_");
//				}
//				//EntLogger.debug(sb.toString());
//			}
//			this.jTextField.setCaretPosition(nStart);
//		}
		
		int nPos = this.jTextField.getCaretPosition();
		
		if(e.getKeyCode() == 127){
			//EntLogger.debug(sb.toString());
			sb = sb.insert(nPos, sb.charAt(nPos));
			//EntLogger.debug(sb.toString());
		}
		
		else if(e.getKeyCode() == 8){
			//EntLogger.debug(sb.toString());
			
			if(nPos > 0){
				
				if(sb.charAt(nPos-1) == '-'){
					sb = sb.insert(nPos, '-');
				} else {
					sb = sb.insert(nPos, '_');
				}
				
			}
			
			
		} else {
			
			if(nPos+1 <= DATE_LENGTH){
				
				if(sb.charAt(nPos) == '-'){
					nPos++;
				}
				
				sb = sb.replace(nPos, nPos+1, "");
				
				if(strInput.matches("[^0-9]")){
					if(nPos > 0) nPos--;
				}
			}
			
		}
		
		this.jTextField.setText(sb.toString());
		if(strSelectedText != null && nPos != 0 && e.getKeyCode() == 8){
			this.jTextField.setCaretPosition(nPos+1);
		} else {
			this.jTextField.setCaretPosition(nPos);
		}
		
		
	}

	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == 10){
			return;
		}
		
		//EntLogger.debug(":"+e.getKeyCode());
//		StringBuilder sb = new StringBuilder(this.jTextField.getText());
//		int nPos = this.jTextField.getCaretPosition();
//		
//		if(nPos >= DATE_LENGTH){
//			//this.jTextField.setText(sb.substring(0, DATE_LENGTH));
//			return;
//		}
//		
//		String strInput = KeyEvent.getKeyText(e.getKeyCode());
//		
//		
//		if(e.getKeyCode() == 16 || e.getKeyCode() == 37 
//				|| e.getKeyCode() == 39
//				|| e.getKeyCode() == 8){
//			
//		} else {
//			if(sb.charAt(nPos) == '-'){
//				//this.jTextField.setCaretPosition(nPos+1);
//			} else {
//				//this.jTextField.setCaretPosition(nPos);
//			}
//		}
		

	}

	public void keyTyped(KeyEvent e) {

	}



}
