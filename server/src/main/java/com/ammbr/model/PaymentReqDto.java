package com.ammbr.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@JsonInclude(Include.NON_NULL)
@ApiModel
public class PaymentReqDto {
	@ApiModelProperty(required=true)
	@JsonProperty(required=true)
	private String payeeAddress;
	@ApiModelProperty(required=true)
	@JsonProperty(required=true)
	private BigInteger tokens;
	@ApiModelProperty(required=true)
	@JsonProperty(required=true)
	private String email;

	public String getPayeeAddress() {
		return payeeAddress;
	}

	public void setPayeeAddress(String payeeAddress) {
		this.payeeAddress = payeeAddress;
	}

	public BigInteger getTokens() {
		return tokens;
	}

	public void setTokens(BigInteger tokens) {
		this.tokens = tokens;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
