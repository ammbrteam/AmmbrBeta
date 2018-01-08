package com.ammbr.service;

import com.ammbr.entity.AddressIndex;
import com.ammbr.model.PaymentReqDto;
import com.ammbr.model.PaymentResponseDto;
import com.ammbr.model.TransactionDto;

public interface AmmbrPaymentService {

	TransactionDto tranferAmmbrToWallet(AddressIndex addressIndex);


	PaymentResponseDto payTokensToAddress(PaymentReqDto paymentDTO);

}
