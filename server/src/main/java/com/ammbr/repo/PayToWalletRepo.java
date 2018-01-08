package com.ammbr.repo;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import com.ammbr.entity.PayToWallet;

public interface PayToWalletRepo extends CrudRepository<PayToWallet, Long>{

}
