package com.ammbr.service;

import com.ammbr.model.AddressDto;
import com.ammbr.model.AddressRequestDto;

public interface AddressService {

	//AddressDto getUniqueEthereumAddressForAmmbr();

	AddressDto getUniqueEthereumAddressForAmmbr(AddressRequestDto dto);

}
