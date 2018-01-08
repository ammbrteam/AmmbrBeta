package com.ammbr.model;

import java.math.BigInteger;

import com.ammbr.constants.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class PaymentResponseDto extends ResponseDto {
	
	private String transactionHash;

	private BigInteger blockNumber;
	
	private BigInteger cumulativeGasUsed;

	private BigInteger gasUsed;
	
	private boolean transactionSuccess;
	public PaymentResponseDto(){}
	public PaymentResponseDto(ResponseCode code){
		super(code);
		
	}
	public void setTransactionHash(String transactionHash) {
		// TODO Auto-generated method stub
		
	}
	public BigInteger getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(BigInteger blockNumber) {
		this.blockNumber = blockNumber;
	}
	public BigInteger getCumulativeGasUsed() {
		return cumulativeGasUsed;
	}
	public void setCumulativeGasUsed(BigInteger cumulativeGasUsed) {
		this.cumulativeGasUsed = cumulativeGasUsed;
	}
	public BigInteger getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(BigInteger gasUsed) {
		this.gasUsed = gasUsed;
	}
	public String getTransactionHash() {
		return transactionHash;
	}
	 
	public boolean isTransactionSuccess() {
		return transactionSuccess;
	}
	public void setTransactionSuccess(boolean transactionSuccess) {
		this.transactionSuccess = transactionSuccess;
	}

}
