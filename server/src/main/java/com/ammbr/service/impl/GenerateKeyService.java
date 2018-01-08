package com.ammbr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ammbr.entity.AddressIndex;
import com.ammbr.model.IndexDto;
import com.ammbr.model.KeyDto;
import com.ammbr.properties.UtilityProperties;

@Service
public final class GenerateKeyService {

	private  Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UtilityProperties utilityProperty;
	@Autowired
	private RestTemplate  template ;
	
	
  
	@Autowired
	public GenerateKeyService(RestTemplate template){
		this.template = template;
	}
	
public KeyDto getKey(AddressIndex addressIndex){
		
		
		IndexDto reqdto = new IndexDto();
		reqdto.setIndex(addressIndex.getIndex());
		LOGGER.info("Resting address  "+ addressIndex.getAddress());
		KeyDto resultDto = template.postForObject(utilityProperty.url+ utilityProperty.key, reqdto, KeyDto.class);
		LOGGER.info("Response address  "+ addressIndex.getAddress());
		/*addressIndex.setPaymentRecieved(true);
		LOGGER.info("Saving address  "+ addressIndex.getAddress());
		addressIndexRepo.save(addressIndex);*/
		
		resultDto.setIndex(null);
		
		return resultDto;
		
		
	}
public KeyDto getKey(IndexDto reqdto){
		
		
		reqdto.setIndex(reqdto.getIndex());
		//LOGGER.info("Resting address  "+ addressIndex.getAddress());
		KeyDto resultDto = template.postForObject(utilityProperty.url+ utilityProperty.key, reqdto, KeyDto.class);
		//LOGGER.info("Response address  "+ addressIndex.getAddress());
		/*addressIndex.setPaymentRecieved(true);
		LOGGER.info("Saving address  "+ addressIndex.getAddress());
		addressIndexRepo.save(addressIndex);*/
		
//		resultDto.setIndex(null);
		
		return resultDto;
		
		
	}
	
	 

}
