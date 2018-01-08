package com.ammbr.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ammbr.constants.ResponseCode;
import com.ammbr.entity.AddressIndex;
import com.ammbr.entity.Index;
import com.ammbr.model.AddressDto;
import com.ammbr.model.AddressRequestDto;
import com.ammbr.model.IndexDto;
import com.ammbr.properties.MessageProperties;
import com.ammbr.properties.UtilityProperties;
import com.ammbr.repo.AddressIndexRepo;
import com.ammbr.repo.IndexRepo;

@Service
public final class GenerateAddressService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private UtilityProperties utilityProperty;
	@Autowired
	private RestTemplate template;
	@Autowired
	private AddressIndexRepo addressIndexRepo;
	@Autowired
	private MessageProperties prop;
	@Autowired
	private IndexRepo indexRepo;

	private static ReentrantLock lock;

	@Autowired
	public GenerateAddressService(RestTemplate template) {
		this.template = template;
		lock = new ReentrantLock();
	}

	public AddressDto getAddress(AddressRequestDto dto) {
		BigInteger index = null;

		lock.lock();
		LOGGER.info("Reading index after lock");
		Index lastIndex = indexRepo.findOne(1);
		if (lastIndex == null) {
			lastIndex = new Index();
			lastIndex.setId(1);
			lastIndex.setIndex(BigInteger.valueOf(0));
			LOGGER.info("Index not found at 1st id");
		}
		index = lastIndex.getIndex().add(BigInteger.valueOf(1));
		LOGGER.info("set index : " + index.toString());
		lastIndex.setIndex(index);
		indexRepo.save(lastIndex);

		lock.unlock();

		IndexDto reqdto = new IndexDto();
		reqdto.setIndex(index);
		LOGGER.info("Resting address  ");
		AddressDto resultDto = null;
		try {
			resultDto = template.postForObject(utilityProperty.url + utilityProperty.address, reqdto, AddressDto.class);
			LOGGER.info("Response address  " + resultDto.getUniqueAddress());
			AddressIndex addressIndex = new AddressIndex();
			addressIndex.setAddress(resultDto.getUniqueAddress());
			addressIndex.setIndex(resultDto.getIndex());
			addressIndex.setEmail(dto.getEmail());
			addressIndex.setPaymentRecieved(false);
			addressIndex.setCreationDate(new Date());
			LOGGER.info("Saving address  " + resultDto.getUniqueAddress());
			addressIndexRepo.save(addressIndex);
			resultDto.setEmail(dto.getEmail());
			resultDto.setIndex(null);
			resultDto.setCode(ResponseCode.SUCCESS.getCode());
			resultDto.setDescription(ResponseCode.SUCCESS.name());
			resultDto.setValue(ResponseCode.SUCCESS.name());

		} catch (RuntimeException ex) {
			LOGGER.info(ExceptionUtils.getStackTrace(ex));
			LOGGER.error(ExceptionUtils.getStackTrace(ex));
			resultDto = new AddressDto(ResponseCode.ADDRESS_GENERATION_FAIL);
			resultDto.setDescription(prop.addressGenerationFail);
		}
		return resultDto;

	}

	/**
	 * IndexDto reqdto = new IndexDto(); reqdto.setIndex(index);
	 * LOGGER.info("Resting address "); KeyDto keyDto =
	 * generateKeyService.getKey(reqdto); Credentials credentials =
	 * Credentials.create(keyDto.getKey()); ;
	 * 
	 * //AddressDto resultDto = template.postForObject(utilityProperty.url+
	 * utilityProperty.address, reqdto, AddressDto.class); LOGGER.info("Response
	 * address "+ credentials.getAddress()); AddressIndex addressIndex = new
	 * AddressIndex(); addressIndex.setAddress(credentials.getAddress());
	 * addressIndex.setIndex(keyDto.getIndex());
	 * addressIndex.setPaymentRecieved(false); LOGGER.info("Saving address "+
	 * credentials.getAddress()); addressIndexRepo.save(addressIndex);
	 * AddressDto resultDto = new AddressDto();
	 * 
	 * resultDto.setUniqueAddress(credentials.getAddress());
	 * 
	 * return resultDto;
	 */

}
