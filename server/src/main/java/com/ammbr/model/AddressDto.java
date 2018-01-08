package com.ammbr.model;

import java.math.BigInteger;

import com.ammbr.constants.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@JsonInclude(Include.NON_NULL)
@ApiModel(value = "Address response")
public class AddressDto extends ResponseDto {

	private String uniqueAddress;
	@ApiModelProperty(hidden=true)
	private BigInteger index;
	private String email;
	public AddressDto(){}
	public AddressDto(ResponseCode code) {
super(code);	}

	public String getUniqueAddress() {
		return uniqueAddress;
	}

	public void setUniqueAddress(String uniqueAddress) {
		this.uniqueAddress = uniqueAddress;
	}

	public BigInteger getIndex() {
		return index;
	}

	public void setIndex(BigInteger index) {
		this.index = index;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
