package com.ammbr.service;

import java.math.BigInteger;

import com.ammbr.entity.PayToWallet;

public interface PayToWalletService {

	PayToWallet save(Long id,String address, String transactionHash, BigInteger blockNumber, BigInteger cumulativeGasUsed,
			BigInteger gasUsed, BigInteger token, String restResponse, String email);

}
