package com.ammbr.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource(value = "classpath:webService.properties")
public class WebserviceProperties {

	public String notificationAddress;
	public String apiVersion;
	public String codeCreate ;
	public String md5SmartContract;
	public String md5ForOurService;
	
	
	public void setNotificationAddress(String notificationAddress) {
		this.notificationAddress = notificationAddress;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public void setCodeCreate(String codeCreate) {
		this.codeCreate = codeCreate;
	}
	public void setMd5SmartContract(String md5SmartContract) {
		this.md5SmartContract = md5SmartContract;
	}
	public void setMd5ForOurService(String md5ForOurService) {
		this.md5ForOurService = md5ForOurService;
	} 
	
}
