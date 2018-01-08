package com.ammbr.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ammbr.entity.EtherTransferTransaction;

public interface EtherTransferTransactionRepo extends PagingAndSortingRepository< EtherTransferTransaction, Long>{

	EtherTransferTransaction findTop1ByUniqueAddressAndMiningStateOrderByEtherTransferStateIdDesc(String fromAddress, boolean isMined);

}
