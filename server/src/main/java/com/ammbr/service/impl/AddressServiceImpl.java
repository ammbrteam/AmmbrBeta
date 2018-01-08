package com.ammbr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ammbr.model.AddressDto;
import com.ammbr.model.AddressRequestDto;
import com.ammbr.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{
	private  Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GenerateAddressService utitity;
	
	@Override
	public AddressDto getUniqueEthereumAddressForAmmbr(AddressRequestDto dto) {
		LOGGER.info("calling utility to generate unique address for "+ dto.getEmail());
		AddressDto addressdto = 	utitity.getAddress( dto);
		
		LOGGER.info("response from utility for  unique address : "+addressdto.getUniqueAddress() );
		return addressdto;
	}
	
	

}
