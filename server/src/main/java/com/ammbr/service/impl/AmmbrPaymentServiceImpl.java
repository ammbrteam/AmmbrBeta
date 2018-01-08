package com.ammbr.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.utils.Numeric;

import com.ammbr.constants.ResponseCode;
import com.ammbr.contract.AmmbrContract;
import com.ammbr.contract.WalletToPaymentAddressService;
import com.ammbr.entity.AddressIndex;
import com.ammbr.entity.EtherTransferTransaction;
import com.ammbr.model.KeyDto;
import com.ammbr.model.PaymentReqDto;
import com.ammbr.model.PaymentResponseDto;
import com.ammbr.model.TransactionDto;
import com.ammbr.model.TransactionStatusDto;
import com.ammbr.properties.ContractProperties;
import com.ammbr.properties.MessageProperties;
import com.ammbr.properties.UtilityProperties;
import com.ammbr.repo.AddressIndexRepo;
import com.ammbr.repo.EtherTransferTransactionRepo;
import com.ammbr.service.AmmbrPaymentService;
import com.ammbr.service.WalletToPayeeService;

@Service
public class AmmbrPaymentServiceImpl implements AmmbrPaymentService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Autowired
	private GenerateKeyService service;
	@Autowired
	private ContractProperties contractProperties;
	@Autowired
	private Web3j web3j;
	@Autowired
	private MessageProperties prop;
	@Autowired
	private WalletToPayeeService walletToPayeeService;
	@Autowired
	private WalletToPaymentAddressService paymentAddressService;
	@Autowired
	private AddressIndexRepo addressIndexRepo;
	@Autowired
	private EtherTransferTransactionRepo etherTransferTransactionRepo;
	@Autowired private RestTemplate restTemplate;
	@Autowired
	private UtilityProperties utilityProperty;

	@Override
	public TransactionDto tranferAmmbrToWallet(AddressIndex addressIndex) {
		final KeyDto dto = service.getKey(addressIndex);
		AmmbrContract contract = new AmmbrContract(contractProperties.contractBinary,
				contractProperties.contractAddress, web3j, dto.getKey());

		TransactionReceipt receipt = null;
		BigInteger addressBalance = getBalanceOfAddress(addressIndex.getAddress(), contract);

		
		TransactionDto transactionDto = null;

		if (addressBalance.compareTo(BigInteger.valueOf(0)) > 0) {
			BigInteger ex = null;
			BigInteger etherBalanceForTransaction = null;
//			try {
				ex = contract.estimate(contractProperties.walletAddress, addressBalance);//AmmbrContract.estimateGas(web3j, addressIndex.getAddress(), contractProperties.contractAddress, contractProperties.walletAddress, addressBalance);
				 if(ex.compareTo(BigInteger.ZERO) < 1 && ex.compareTo(AmmbrContract.GAS_LIMIT) > 0){
						return null;
					} 
					 etherBalanceForTransaction = ex.multiply(AmmbrContract.GAS_PRICE);
			/*} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.info(addressIndex.getAddress() + "***** Error while estimation*******" + ExceptionUtils.getStackTrace(e));
				LOGGER.info(addressIndex.getAddress() + "***** Error while estimation*******" + ExceptionUtils.getStackTrace(e));
			}//
*/					// //AmmbrContract.GAS_LIMIT; // contract.estimateGas(addressIndex.getAddress(),addressBalance);;
			
			final BigInteger limit = ex;
			
			EtherTransferTransaction lastEtherTransferTransaction = etherTransferTransactionRepo
					.findTop1ByUniqueAddressAndMiningStateOrderByEtherTransferStateIdDesc(contract.getFromAddress(),
							false);

			try {
				BigInteger etherBalanceInAccount  = contract.etherBalance();
				if (lastEtherTransferTransaction == null
					//	|| etherBalanceInAccount.compareTo(lastEtherTransferTransaction.getTransferEther()) < 0
						|| etherBalanceInAccount.subtract(etherBalanceForTransaction).compareTo(BigInteger.ZERO) < 0) {
					if (lastEtherTransferTransaction != null) {
						// update lastEtherTransferTransaction state 
						lastEtherTransferTransaction.setMiningState(true);
						etherTransferTransactionRepo.save(lastEtherTransferTransaction);
					}
					try {
						// Tranfer Ether from Central or Ammbr Wallet to unique address
						Credentials credential = Credentials.create(contractProperties.privateKey);
						BigInteger nonce = web3j
								.ethGetTransactionCount(credential.getAddress(), DefaultBlockParameterName.LATEST)
								.send().getTransactionCount();
						BigInteger etherSend = etherBalanceForTransaction.subtract(etherBalanceInAccount);
						RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,
								AmmbrContract.GAS_PRICE, AmmbrContract.GAS_LIMIT, addressIndex.getAddress(),
								etherSend);

						byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credential);
						String hexValue = Numeric.toHexString(signedMessage);
						EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
						

						LOGGER.info("Transfer Ether  *********" + etherSend + " to address " + etherBalanceForTransaction);
						LOGGER.info("Transfer Ether transaction *********" + ethSendTransaction.getTransactionHash());
						EtherTransferTransaction etherTransferTransaction = new EtherTransferTransaction();
						etherTransferTransaction.setHash(ethSendTransaction.getTransactionHash());
						etherTransferTransaction.setUniqueAddress(contract.getFromAddress());
						etherTransferTransaction.setCreationDate(new Date());
						etherTransferTransaction.setTransferEther(etherBalanceForTransaction.toString());
						etherTransferTransaction.setMiningState(false);
						etherTransferTransactionRepo.save(etherTransferTransaction);
						return null;

					} catch (InterruptedException | ExecutionException | IOException e) {

						LOGGER.info("Saving address  " + addressIndex.getAddress() + "\n"
								+ ExceptionUtils.getStackTrace(e));
					}

				}
			} catch (IOException e) {
				 
				LOGGER.info(addressIndex.getAddress() + "\n" + ExceptionUtils.getStackTrace(e));
				LOGGER.error(addressIndex.getAddress() + "\n" + ExceptionUtils.getStackTrace(e));
			}
			// update lastEtherTransferTransaction state 
			//etherTransferTransactionRepo.save(lastEtherTransferTransaction);
			
			// Recreate ammbr contract object with estimated gas limit  
			contract = new AmmbrContract(contractProperties.contractBinary,
					contractProperties.contractAddress, web3j, dto.getKey(), limit);
			// Transfer Ammbr token to Ammbr wallet or say central wallet
			receipt = tranferToAddress(contractProperties.walletAddress, contract, addressBalance);

	
			
			// 		Check is transaction successfull and save into database
			if (receipt != null && !receipt.getTransactionHash().isEmpty() && isTransactionSuccess(receipt.getTransactionHash())) {
				transactionDto = new TransactionDto();
				transactionDto.setTransactionReceipt(receipt);
				transactionDto.setTokens(addressBalance);
				transactionDto.setAddress(addressIndex.getAddress());
				addressIndex.setPaymentRecieved(true);
				LOGGER.info("Saving address  " + addressIndex.getAddress());
				addressIndexRepo.save(addressIndex);
			}

		}

		return transactionDto;
	}
/*
 * status: QUANTITY either 1 (success) or 0 (failure)
 */
	private boolean isTransactionSuccess(String transactionHash) {
		TransactionStatusDto dto = new TransactionStatusDto(transactionHash);
		 dto= 	restTemplate.postForObject(utilityProperty.url+ utilityProperty.transaction, dto, TransactionStatusDto.class);
		return (dto!= null && dto.getStatus().equals( 1));
		 
		
	}

	private TransactionReceipt tranferToAddress(String toAddress, AmmbrContract contract, BigInteger addressBalance) {
		try {
			TransactionReceipt receipt = contract.transfer(new Address(toAddress), new Uint256(addressBalance));
			LOGGER.info(contract.getFromAddress() + " address : transfer " + receipt.getTransactionHash());
			return receipt;
		} catch (InterruptedException | IOException | TransactionTimeoutException e) {
			LOGGER.info(contract.getFromAddress() + " address to transfer: " + ExceptionUtils.getStackTrace(e));
			LOGGER.error(contract.getFromAddress() + " address to transfer: " + ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	private BigInteger getBalanceOfAddress(String address, AmmbrContract contract) {
		BigInteger addressBalance = BigInteger.ZERO;
		try {

			addressBalance = contract.balanceOf(address);
			LOGGER.info(address + " address : balance check done");

		} catch (ExecutionException | InterruptedException | IOException | TransactionTimeoutException e) {
			LOGGER.info(address + " address : " + ExceptionUtils.getStackTrace(e));
			LOGGER.error(address + " address : " + ExceptionUtils.getStackTrace(e));
		}
		return addressBalance;
	}

	@Override
	public PaymentResponseDto payTokensToAddress(PaymentReqDto paymentDTO) {

		BigInteger addressBalance = getBalanceOfAddress(contractProperties.walletAddress, paymentAddressService);
		PaymentResponseDto responseDto = new PaymentResponseDto(ResponseCode.SUCCESS);
		LOGGER.info("Wallet address : ammbr balance check done");
		if (addressBalance.compareTo(BigInteger.ZERO) > 0 && addressBalance.compareTo(paymentDTO.getTokens()) >= 0) {

			try {
				if (paymentAddressService.etherBalance().compareTo(BigInteger.valueOf(10000000000L)) < 0) {
					LOGGER.info("Wallet address : ether balance check done " + prop.insufficentEther);

					responseDto = new PaymentResponseDto(ResponseCode.ERROR);
					responseDto.setDescription(prop.insufficentEther);

				} else {
					LOGGER.info("Wallet address : ether balance check done");

					TransactionReceipt receipt = tranferToAddress(paymentDTO.getPayeeAddress(), paymentAddressService,
							paymentDTO.getTokens());
					responseDto.setTransactionHash(receipt.getTransactionHash());
					responseDto.setBlockNumber(receipt.getBlockNumber());
					responseDto.setCumulativeGasUsed(receipt.getCumulativeGasUsed());
					responseDto.setTransactionSuccess(isTransactionSuccess(receipt.getTransactionHash()));
					responseDto.setGasUsed(receipt.getGasUsed());
					if(!responseDto.isTransactionSuccess()){
						responseDto.setCode(ResponseCode.TRANSFER_TRANSACTION_FAIL.getCode());
						responseDto.setDescription("Transaction failure");
						responseDto.setValue(ResponseCode.TRANSFER_TRANSACTION_FAIL.name());
					}
					walletToPayeeService.save(paymentDTO.getPayeeAddress(), receipt.getTransactionHash(),
							receipt.getBlockNumber(), receipt.getCumulativeGasUsed(), receipt.getGasUsed(), paymentDTO.getEmail(), responseDto.isTransactionSuccess(), paymentDTO.getTokens());
					LOGGER.info("Saved Wallet To Payee info");
				}

			} catch (IOException e) {
				LOGGER.info(ExceptionUtils.getStackTrace(e));
				LOGGER.error(ExceptionUtils.getStackTrace(e));
				responseDto = new PaymentResponseDto(ResponseCode.FAIL);
			}

		} else {
			LOGGER.info("Wallet address : ammbr balance check done " + prop.insufficentBalance);
			responseDto = new PaymentResponseDto(ResponseCode.ERROR);
			responseDto.setDescription(prop.insufficentBalance);
		}
		return responseDto;
	}

}
