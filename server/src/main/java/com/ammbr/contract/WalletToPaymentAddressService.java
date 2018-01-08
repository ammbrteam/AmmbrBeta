package com.ammbr.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import com.ammbr.properties.ContractProperties;

@Service
public  class WalletToPaymentAddressService extends AmmbrContract {
	
	@Autowired
	public WalletToPaymentAddressService(ContractProperties contractProperties, Web3j web3j) {
		super(contractProperties.contractBinary, contractProperties.contractAddress, web3j,
				contractProperties.privateKey);

	}
}
