package com.ammbr.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource(value={"classpath:messages.properties"})
public class MessageProperties {
	public String insufficentBalance;
	public String insufficentEther;
	public String addressGenerationFail;
	public String badRequest;

	public void setInsufficentBalance(String insufficentBalance) {
		this.insufficentBalance = insufficentBalance;
	}

	public void setInsufficentEther(String insufficentEther) {
		this.insufficentEther = insufficentEther;
	}

	public void setAddressGenerationFail(String addressGenerationFail) {
		this.addressGenerationFail = addressGenerationFail;
	}

	public void setBadRequest(String badRequest) {
		this.badRequest = badRequest;
	}
}
