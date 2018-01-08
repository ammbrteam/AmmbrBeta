package com.ammbr.service.impl;

import java.math.BigInteger;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ammbr.entity.WalletToPayee;
import com.ammbr.repo.WalletToPayeeRepo;
import com.ammbr.service.WalletToPayeeService;

@Service
public class WalletToPayeeServiceImpl implements WalletToPayeeService{
	private  Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired
	private WalletToPayeeRepo walletToPayeeRepo;
	
	@Override
	public WalletToPayee save(String payeeAddress, String transactionHash, BigInteger blockNumber, BigInteger cumulativeGasUsed,
			BigInteger gasUsed, String email,  Boolean transactionStatus, BigInteger numberOfToken) {
		LOGGER.info("Saving Wallet To Payee info");
		WalletToPayee walletToPayee = new WalletToPayee();
		walletToPayee.setPaymentAddress(payeeAddress);
		walletToPayee.setTransactionHash(transactionHash);
		walletToPayee.setBlockNumber(blockNumber.toString());
		walletToPayee.setCumulativeGasUsed(cumulativeGasUsed.toString());
		walletToPayee.setGasUsed(gasUsed.toString());
		walletToPayee.setEmail(email);
		walletToPayee.setStatus(transactionStatus);
		walletToPayee.setNumberOfToken(numberOfToken.toString());
		walletToPayee.setCreationDate(new Date());
		return walletToPayeeRepo.save(walletToPayee);
	}

}
