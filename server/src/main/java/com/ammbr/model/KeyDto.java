package com.ammbr.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class KeyDto {
	private String key;
	private BigInteger index;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public BigInteger getIndex() {
		return index;
	}
	public void setIndex(BigInteger index) {
		this.index = index;
	}
}
