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
import com.ammbr.model.PaymentReqDto;
import com.ammbr.model.PaymentResponseDto;
import com.ammbr.properties.MessageProperties;
import com.ammbr.service.AmmbrPaymentService;
import com.ammbr.utils.AuthenticationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/payment")
@Api
public class PaymentTranferController {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private AmmbrPaymentService paymentService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private MessageProperties prop;

	@RequestMapping(value = "/transfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Payment Transfer from wallet to user wallet", tags = "Payment transfer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PaymentResponseDto sendPaymentToAddress(@RequestBody PaymentReqDto paymentDTO,
			@RequestHeader(required = true, value = "Ammbr-authorization") String authorization) {
		LOGGER.info("Accept request to payee address" + paymentDTO.getPayeeAddress());
//		String 	authorization = "1813c317b3b320b5ece6099ee8a96e67";
		if (authenticationService.authenticate(authorization, paymentDTO.getEmail()))

			return paymentService.payTokensToAddress(paymentDTO);

		else {
			LOGGER.info(
					"Accept request to payee address " + paymentDTO.getPayeeAddress() + " rejected due to bad request");
			PaymentResponseDto responseDto = new PaymentResponseDto(ResponseCode.FAIL);
			responseDto.setDescription(prop.badRequest);
			return responseDto;
		}

	}
}
