package com.ammbr.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ether_transfer_state")
public class EtherTransferTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ether_transfer_state")
	private Long etherTransferStateId;
	
	
	@Column(name = "hash")
	private String hash;
	
	@Column(name = "mining_state")
	private boolean miningState;
	
	
	
	@Column(name = "unique_address", length = 255)
	private String uniqueAddress;

	@Column(name = "transfer_ether", length = 255)
	private String  transferEther;

	@Column(name="creation_date")
	private Date creationDate;
	
	@Override
	public String toString(){
		return "hash : "+hash+" miningState : "+miningState+" unique Address " + uniqueAddress;
	}


	public Long getEtherTransferStateId() {
		return etherTransferStateId;
	}


	public void setEtherTransferStateId(Long etherTransferStateId) {
		this.etherTransferStateId = etherTransferStateId;
	}


	public String getHash() {
		return hash;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}


	public boolean isMiningState() {
		return miningState;
	}


	public void setMiningState(boolean miningState) {
		this.miningState = miningState;
	}


	public String getUniqueAddress() {
		return uniqueAddress;
	}


	public void setUniqueAddress(String uniqueAddress) {
		this.uniqueAddress = uniqueAddress;
	}


	public String getTransferEther() {
		return transferEther;
	}


	public void setTransferEther(String transferEther) {
		this.transferEther = transferEther;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
