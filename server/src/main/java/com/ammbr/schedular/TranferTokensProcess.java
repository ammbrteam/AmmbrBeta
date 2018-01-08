package com.ammbr.schedular;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import com.ammbr.entity.AddressIndex;
import com.ammbr.entity.PayToWallet;
import com.ammbr.model.TransactionDto;
import com.ammbr.properties.WebserviceProperties;
import com.ammbr.repo.AddressIndexRepo;
import com.ammbr.service.AmmbrPaymentService;
import com.ammbr.service.PayToWalletService;

@Component
public class TranferTokensProcess {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AddressIndexRepo addressIndexRepo;
	
	@Autowired
	private AmmbrPaymentService ammbrPaymentService;
	
	@Autowired
	private PayToWalletService service;
	
	@Autowired
	private WebserviceProperties webserviceProperties;
	

	@Scheduled(cron = "${schedule.time}")
	public void runJob() {

		LOGGER.info("Process Allocation runJob()");
		Iterable<AddressIndex> addressIndexIterable = addressIndexRepo.findAll();

		Consumer<AddressIndex> consumerAssocations = addressIndex -> {

			try {
				TransactionDto transactionDto = ammbrPaymentService.tranferAmmbrToWallet(addressIndex);
				if (transactionDto != null && transactionDto.getTransactionReceipt() != null) {
				
//					addressIndex.setPaymentRecieved(true);
//					addressIndexRepo.save(addressIndex);

					PayToWallet payToWallet = service.save(null, addressIndex.getAddress(), transactionDto.getTransactionReceipt().getTransactionHash(), transactionDto.getTransactionReceipt().getBlockNumber(),
							transactionDto.getTransactionReceipt().getCumulativeGasUsed(), transactionDto.getTransactionReceipt().getGasUsed(), transactionDto.getTokens(), null, addressIndex.getEmail());
					
					 
					HttpHeaders headers = new HttpHeaders();
					 headers.setContentType(MediaType.APPLICATION_JSON);
					 String contractKey = webserviceProperties.md5SmartContract;
					 String md5 = DigestUtils.md5DigestAsHex((contractKey+ addressIndex.getAddress()).getBytes());
					 LOGGER.info(md5 + " Hash for "+ addressIndex.getAddress());
					 
					 headers.add("Ammbr-authorization", md5);
					 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
					 headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
					  
					 Map<String, Object> map= new HashMap<String, Object>();
					 map.put("tokens", Arrays.asList(transactionDto.getTokens()));
					 map.put("payment_address", transactionDto.getAddress());
					 RestTemplate restTemplate = new RestTemplate();
					 HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(map, headers);
					 
					 String url = webserviceProperties.notificationAddress+webserviceProperties.apiVersion+webserviceProperties.codeCreate;
					 
					 ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
					 /*ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
					    System.out.println(res);*/
					 LOGGER.info(addressIndex.getAddress() + " reponse " +  response);
					
					
					 payToWallet = service.save(payToWallet.getId(), addressIndex.getAddress(), transactionDto.getTransactionReceipt().getTransactionHash(), transactionDto.getTransactionReceipt().getBlockNumber(),
								transactionDto.getTransactionReceipt().getCumulativeGasUsed(), transactionDto.getTransactionReceipt().getGasUsed(), transactionDto.getTokens(), response.getBody(), addressIndex.getEmail());
					 
				}
			} catch (RuntimeException e) {
				LOGGER.debug(ExceptionUtils.getStackTrace(e));
				LOGGER.info(ExceptionUtils.getStackTrace(e));
			}

		};

		addressIndexIterable.forEach(consumerAssocations);

	}
}