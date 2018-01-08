package com.ammbr.model;

public class TransactionStatusDto {
	private Integer status;
	private String transactionHash;

	public TransactionStatusDto(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public TransactionStatusDto() {
		// TODO Auto-generated constructor stub
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}
}
