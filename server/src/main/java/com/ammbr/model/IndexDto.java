package com.ammbr.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class IndexDto {
	private BigInteger index;

	

	public BigInteger getIndex() {
		return index;
	}

	public void setIndex(BigInteger index) {
		this.index = index;
	}
}
