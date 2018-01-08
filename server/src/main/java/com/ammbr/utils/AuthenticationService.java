package com.ammbr.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.ammbr.properties.WebserviceProperties;
@Service
public  class AuthenticationService {

	@Autowired
	private WebserviceProperties webServiceProp;
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	public  boolean authenticate(String authorization, String email) {
		//LOGGER.info(webServiceProp.md5ForOurService + " " +DigestUtils.md5DigestAsHex((webServiceProp.md5ForOurService + email).getBytes()));
		String generatedHash = DigestUtils.md5DigestAsHex((webServiceProp.md5ForOurService + email).getBytes());
		LOGGER.info( email + " auth " + generatedHash.equals(authorization));
		return generatedHash.equals(authorization);
	}
	
	
	  public static void main(String[] args) { 
System.out.println(DigestUtils.md5DigestAsHex("bogdanpavel1022@gmail.comth00IAWhMQUFHwmqfRQ".getBytes()));
	 }
	 

}
