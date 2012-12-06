package com.emflant.accounting.dto.table;

import com.emflant.accounting.adapter.table.CodeDetail;

public class CodeDetailDTO extends CodeDetail {
	
	public CodeDetailDTO(){
	}
	
	public String toString(){
		
		String strResult;
		if(this.codeName == null){
			strResult = this.code;
		} else {
			strResult = this.code+":"+this.codeName;
		}
		
		return strResult;
	}

}
