package com.ammbr.service.impl;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ammbr.entity.PayToWallet;
import com.ammbr.repo.PayToWalletRepo;
import com.ammbr.service.PayToWalletService;
@Service
public class PayToWalletServiceImpl implements PayToWalletService{

	@Autowired
	private PayToWalletRepo payToWalletRepo;
	
	@Override
	public PayToWallet save(Long id, String address, String transactionHash, BigInteger blockNumber,
			BigInteger cumulativeGasUsed, BigInteger gasUsed, BigInteger token, String restResponseJSON, String email) {
		PayToWallet payToWallet = new PayToWallet();
		payToWallet.setUniqueAddress(address);
		payToWallet.setTransactionHash(transactionHash);
		payToWallet.setBlockNumber(blockNumber.toString());
		payToWallet.setCumulativeGasUsed(cumulativeGasUsed.toString());
		payToWallet.setGasUsed(gasUsed.toString());
		payToWallet.setTokenRecieved(token.toString());
		payToWallet.setId(id);
		payToWallet.setEmail(email);
		payToWallet.setRestResponseJSON(restResponseJSON);
		payToWallet.setCreationDate(new Date());
		return payToWalletRepo.save(payToWallet);
	
	}
	
	
	

}
