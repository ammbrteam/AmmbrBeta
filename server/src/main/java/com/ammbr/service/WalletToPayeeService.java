package com.ammbr.service;

import java.math.BigInteger;

import com.ammbr.entity.WalletToPayee;

public interface WalletToPayeeService {
	
	WalletToPayee save(String payeeAddress, String transactionHash, BigInteger blockNumber, BigInteger cumulativeGasUsed,
			BigInteger gasUsed, String email, Boolean transactionStatus, BigInteger numberOfToken);

}
