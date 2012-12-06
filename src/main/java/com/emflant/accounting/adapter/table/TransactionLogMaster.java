package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class TransactionLogMaster implements EntTable {

	@EntNotNull
	protected String userId;
	@EntNotNull
	protected String systemDateTime;
	@EntNotNull
	protected String screenName;
	@EntNotNull
	protected String oneSlipYn;
	@EntNotNull
	protected Integer totalCount;
	@EntNotNull
	protected Integer successCount;

	public TransactionLogMaster(){
		this.totalCount = 0;
		this.successCount = 0;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getUserId(){
		return this.userId;
	}
	public void setSystemDateTime(String systemDateTime){
		this.systemDateTime = systemDateTime;
	}
	public String getSystemDateTime(){
		return this.systemDateTime;
	}
	public void setScreenName(String screenName){
		this.screenName = screenName;
	}
	public String getScreenName(){
		return this.screenName;
	}
	public void setOneSlipYn(String oneSlipYn){
		this.oneSlipYn = oneSlipYn;
	}
	public String getOneSlipYn(){
		return this.oneSlipYn;
	}
	public void setTotalCount(Integer totalCount){
		this.totalCount = totalCount;
	}
	public Integer getTotalCount(){
		return this.totalCount;
	}
	public void setSuccessCount(Integer successCount){
		this.successCount = successCount;
	}
	public Integer getSuccessCount(){
		return this.successCount;
	}
	public StringBuilder insert(){
		StringBuilder sb = new StringBuilder(1024);
		sb.append("INSERT INTO transaction_log_master (");
		sb.append("user_id, ");
		sb.append("system_date_time, ");
		sb.append("screen_name, ");
		sb.append("one_slip_yn, ");
		sb.append("total_count, ");
		sb.append("success_count");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.userId));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.systemDateTime));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.screenName));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.oneSlipYn));
		sb.append(", ");
		sb.append(this.totalCount);
		sb.append(", ");
		sb.append(this.successCount);
		sb.append(")");
		return sb;
	}

}
