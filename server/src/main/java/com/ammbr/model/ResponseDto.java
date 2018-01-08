package com.ammbr.model;

import com.ammbr.constants.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class ResponseDto {
	private String value;
	private int code;
	private String description;
	
	public ResponseDto(){}
	public ResponseDto(ResponseCode responseCode) {
		this.code = responseCode.getCode();
		this.value = responseCode.name();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
