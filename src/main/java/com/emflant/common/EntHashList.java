package com.emflant.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.emflant.accounting.dto.table.CodeDetailDTO;

public class EntHashList {
	
	private List<CodeDetailDTO> list;
	private HashMap<String, CodeDetailDTO> hashMap;
	
	public EntHashList(){
		this.list = new ArrayList<CodeDetailDTO>();
		this.hashMap = new HashMap<String, CodeDetailDTO>();
	}

	public List<CodeDetailDTO> getList() {
		return list;
	}

	public void setList(List<CodeDetailDTO> list) {
		this.list = list;
	}

	public HashMap<String, CodeDetailDTO> getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap<String, CodeDetailDTO> hashMap) {
		this.hashMap = hashMap;
	}
	
	public void addItem(String code, String codeName){
		
		CodeDetailDTO codeDetailDTO = new CodeDetailDTO();
		codeDetailDTO.setCode(code);
		codeDetailDTO.setCodeName(codeName);
		
		this.list.add(codeDetailDTO);
		this.hashMap.put(code, codeDetailDTO);
	}
	
	public CodeDetailDTO getCodeDetail(String code){
		return this.hashMap.get(code);
	}
	
	public String getCodeBySelectedIndex(int index){
		CodeDetailDTO codeDetail = (CodeDetailDTO)list.get(index);
		return codeDetail.getCode();
	}
	
	public String getCodeNameBySelectedIndex(int index){
		CodeDetailDTO codeDetail = (CodeDetailDTO)list.get(index);
		return codeDetail.getCodeName();
	}
	
}
