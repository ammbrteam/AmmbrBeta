package com.ammbr.constants;

public enum ResponseCode {
	SUCCESS(200), FAIL(100), ERROR(101), ADDRESS_GENERATION_FAIL(102), TRANSFER_TRANSACTION_FAIL(103);
	
	private int code;

	private ResponseCode(int code){
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}
