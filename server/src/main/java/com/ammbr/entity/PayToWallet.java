package com.ammbr.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "pay_to_wallet")
public class PayToWallet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "unique_address")
	private String uniqueAddress;
	
	@Column(name = "transaction_hash", length = 255)
	private String transactionHash;

	@Column(name = "block_number", length = 255)
	private String blockNumber;
	
	@Column(name = "cumulative_gas_used", length = 255)
	private String cumulativeGasUsed;

	@Column(name = "gas_used", length = 255)
	private String gasUsed;
	

	@Column(name="creation_date")
	private Date creationDate;
	
	
	@Column(name = "rest_response_json", length = Integer.MAX_VALUE,columnDefinition="Text")
	@Type(type="text")
	private String restResponseJSON;
	
	@Column(name = "token_recieved")
	private String tokenRecieved;
	@Column(name = "email")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniqueAddress() {
		return uniqueAddress;
	}

	public void setUniqueAddress(String uniqueAddress) {
		this.uniqueAddress = uniqueAddress;
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public String getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getCumulativeGasUsed() {
		return cumulativeGasUsed;
	}

	public void setCumulativeGasUsed(String cumulativeGasUsed) {
		this.cumulativeGasUsed = cumulativeGasUsed;
	}

	public String getGasUsed() {
		return gasUsed;
	}

	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}

	public String getRestResponseJSON() {
		return restResponseJSON;
	}

	public void setRestResponseJSON(String restResponseJSON) {
		this.restResponseJSON = restResponseJSON;
	}

	public String getTokenRecieved() {
		return tokenRecieved;
	}

	public void setTokenRecieved(String tokenRecieved) {
		this.tokenRecieved = tokenRecieved;
	}

	public void setEmail(String email) {
		this.email =email;
		
	}

	public String getEmail() {
		return email;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


}
