package com.ammbr.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource(value = "classpath:utility.properties")
public class UtilityProperties {

	public String url;
	public String key;
	public String address;
	public String transaction;
	
	public void setUrl(String url) {
		this.url = url;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

}
