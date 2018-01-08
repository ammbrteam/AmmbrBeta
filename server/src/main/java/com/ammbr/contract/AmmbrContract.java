package com.ammbr.contract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.tx.Contract;

public class AmmbrContract extends Contract {

	private String fromAddress;
	private Web3j web3j;
	public  static final BigInteger GAS_LIMIT = BigInteger.valueOf(100000);
	private static final Logger LOGGER = LoggerFactory.getLogger(AmmbrContract.class);
	
	public AmmbrContract(String contractBinary, String contractAddress, Web3j web3j, String privateKey) {
		super(contractBinary, contractAddress, web3j, Credentials.create(privateKey), GAS_PRICE, GAS_LIMIT);
		this.web3j = web3j;
		fromAddress = Credentials.create(privateKey).getAddress();

	}
	public AmmbrContract(String contractBinary, String contractAddress, Web3j web3j, String privateKey, BigInteger gasLimit ) {
		super(contractBinary, contractAddress, web3j, Credentials.create(privateKey), GAS_PRICE, gasLimit);
		this.web3j = web3j;
		fromAddress = Credentials.create(privateKey).getAddress();

	}



	public TransactionReceipt executeTransaction(Function function)
			throws InterruptedException, IOException, TransactionTimeoutException {
		
		return super.executeTransaction((Function) function);
	}

	public TransactionReceipt transfer(Address address, Uint256 uint)
			throws InterruptedException, IOException, TransactionTimeoutException {
		Function function = transferFunction(address, uint);
		return executeTransaction(function);
	}

	private static Function transferFunction(Address address, Uint256 uint) {
		Function function = new Function("transfer", Arrays.<Type>asList(address, uint),
				Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
				}));
		return function;
	}

	public BigInteger balanceOf(Address address)
			throws ExecutionException, InterruptedException, IOException, TransactionTimeoutException {
		Function function = new Function("balanceOf", Arrays.<Type>asList(address),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint>() {
				}));
		
		Uint returnBalance = executeCallSingleValueReturn(function);
		if(returnBalance != null)
		return  (BigInteger) returnBalance.getValue();
		else
			return BigInteger.ZERO;
	}

	public BigInteger balanceOf(String address)
			throws ExecutionException, InterruptedException, IOException, TransactionTimeoutException {
		return balanceOf(new Address(address));
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public BigInteger etherBalance() throws IOException {
		return web3j.ethGetBalance(fromAddress, DefaultBlockParameterName.LATEST).send().getBalance();

	}
	
	
	public static BigInteger estimateGas(Web3j web3j, String fromAddress, String contractAddress ,String to, BigInteger amount) throws IOException{/*
		  String encodedFunction = FunctionEncoder.encode(transferFunction(new Address(fromAddress), new Uint256(ammount)));
	      
	    //  Transaction.createEthCallTransaction(transactionManager.getFromAddress(), this.getContractAddress(), encodedFunction);
	      

	        
		Transaction transaction = Transaction.createFunctionCallTransaction(encodedFunction, BigInteger.ZERO, null, null, to, ammount , null);
		EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
		return ethEstimateGas.getAmountUsed();
	*/
		
		String data = FunctionEncoder.encode(transferFunction( new Address(to ),   new Uint256(amount)));
		// From address contains who want to send transaction and to address contains contract address
		Transaction transaction = Transaction.createEthCallTransaction(fromAddress, contractAddress,  data);
		try {
			EthGasPrice price = web3j.ethGasPrice().send();
			EthEstimateGas gas = 	web3j.ethEstimateGas(transaction ).send();
			LOGGER.info(gas.getAmountUsed().divide(price.getGasPrice()).toString());
			LOGGER.info(""+gas.getResult());
			LOGGER.info(""+gas.getAmountUsed());
		
			return gas.getAmountUsed();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.info(ExceptionUtils.getStackTrace(e));
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			return BigInteger.ZERO;
		}
	}

	/*public boolean isTransactionMined(String transactionHash) throws IOException {
		EthTransaction ethTransaction = web3j.ethGetTransactionByHash(transactionHash).send();
		if(ethTransaction != null)
			ethTransaction.getTransaction().
		return false;
	}*/
	

public BigInteger estimate(String toAddress, BigInteger amount){
	// Address to send Ammbr token
	String data = FunctionEncoder.encode(transferFunction( new Address(toAddress),   new Uint256(amount)));
	// From address contains who want to send transaction and to address contains contract address
	Transaction transaction = Transaction.createEthCallTransaction(fromAddress, super.getContractAddress(),  data);
	try {
		EthGasPrice price = web3j.ethGasPrice().send();
		EthEstimateGas gas = 	web3j.ethEstimateGas(transaction ).send();
		System.out.println(gas.getAmountUsed().divide(price.getGasPrice()));
		System.out.println(""+gas.getResult());
		System.out.println(""+gas.getAmountUsed());
	
		return gas.getAmountUsed();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		LOGGER.info(ExceptionUtils.getStackTrace(e));
		LOGGER.error(ExceptionUtils.getStackTrace(e));
		return BigInteger.ZERO;
	}
}
}
