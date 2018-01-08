package com.ammbr.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class PubHdKeyDto {
	
	private BigInteger levelOneParentIndex;
	private BigInteger index;
	private String unquiAddress;
	
	public BigInteger getLevelOneParentIndex() {
		return levelOneParentIndex;
	}
	public void setLevelOneParentIndex(BigInteger levelOneParentIndex) {
		this.levelOneParentIndex = levelOneParentIndex;
	}
	public BigInteger getIndex() {
		return index;
	}
	public void setIndex(BigInteger index) {
		this.index = index;
	}
	public String getUnquiAddress() {
		return unquiAddress;
	}
	public void setUnquiAddress(String unquiAddress) {
		this.unquiAddress = unquiAddress;
	}

}
