package com.ammbr.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource(value={"classpath:ammbrContract.properties"})
public class ContractProperties {

	public String contractBinary;
	public String contractAddress;
	public String walletAddress;
	public String privateKey;
	public void setContractBinary(String contractBinary) {
		this.contractBinary = contractBinary;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public void setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
