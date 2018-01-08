package com.ammbr.model;

import java.math.BigInteger;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class TransactionDto {
	private TransactionReceipt transactionReceipt;
	private BigInteger tokens;
	private String address;
	public TransactionReceipt getTransactionReceipt() {
		return transactionReceipt;
	}
	public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
		this.transactionReceipt = transactionReceipt;
	}
	public BigInteger getTokens() {
		return tokens;
	}
	public void setTokens(BigInteger tokens) {
		this.tokens = tokens;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
