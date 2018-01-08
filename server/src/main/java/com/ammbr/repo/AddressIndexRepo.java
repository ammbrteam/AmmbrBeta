package com.ammbr.repo;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import com.ammbr.entity.AddressIndex;


public interface AddressIndexRepo extends   CrudRepository<AddressIndex,  Long>{

	Iterable<AddressIndex> findByPaymentRecieved(boolean accepted);

}
