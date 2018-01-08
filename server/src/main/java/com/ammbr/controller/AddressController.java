package com.ammbr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ammbr.constants.ResponseCode;
import com.ammbr.model.AddressDto;
import com.ammbr.model.AddressRequestDto;
import com.ammbr.properties.MessageProperties;
import com.ammbr.service.AddressService;
import com.ammbr.utils.AuthenticationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/address")
@Api
public class AddressController {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private AddressService addressService;

	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private MessageProperties prop;

	@RequestMapping(value = "/generate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Address address generate for user", tags = "Address generate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public AddressDto getUniquePaymentAddress(@RequestBody AddressRequestDto dto,
			@RequestHeader(required = true, value = "Ammbr-authorization") String authorization) {
//	String 	authorization = "1813c317b3b320b5ece6099ee8a96e67";
		LOGGER.info("Accept request to generate unique address");
		if (authenticationService.authenticate(authorization, dto.getEmail()))
			return addressService.getUniqueEthereumAddressForAmmbr(dto);

		else {
			LOGGER.info("Request for email address " + dto.getEmail() + " rejected due to bad request");
			AddressDto responseDto = new AddressDto(ResponseCode.FAIL);
			responseDto.setDescription(prop.badRequest);
			return responseDto;
		}
	}
}
