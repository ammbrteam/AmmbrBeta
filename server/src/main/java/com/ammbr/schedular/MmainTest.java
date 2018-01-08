package com.ammbr.schedular;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import org.web3j.abi.FunctionEncoder;
import org.web3j.protocol.Web3j;

public class MmainTest {
private Web3j web3j;
public static void main(String[] args) {/*
	HttpHeaders headers = new HttpHeaders();
	 headers.setContentType(MediaType.APPLICATION_JSON);
	 headers.add("Ammbr-authorization", "8b936a5c7a5cb64d23106209bb4643d0");
	 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	  headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
	  
	 Map<String, Object> map= new HashMap<String, Object>();
	 map.put("tokens", Arrays.asList("10000000000000000"));
	 map.put("payment_address", "0x5adf598059c5a84ce74808be91e583aac34a0968");

	 HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(map, headers);
	 RestTemplate restTemplate = new RestTemplate();
	 String url = "http://blog.ammbr.com/_api/v1/code/create";
	 ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
	 ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
	    System.out.println(res);
	 System.out.println(response);
*/
	
System.out.println(DigestUtils.md5DigestAsHex("th00IAWhMQUFHwmqfRQrahul.bhalla@ireslab.com".getBytes()));

}



}
