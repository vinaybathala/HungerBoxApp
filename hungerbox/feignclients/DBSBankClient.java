package com.hungerbox.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hungerbox.dto.UserTransactionRequestDto;

@FeignClient(name = "http://DBSBANK-SERVICE/bank/dbs")

//  @FeignClient(value ="bank-service", url = "http://localhost:8089/bank/dbs")

public interface DBSBankClient {

	@PostMapping("/transfer")
	public String fundTransfer(@RequestBody UserTransactionRequestDto transactionRequestDto);

	@GetMapping("/port")
	public String getInfo();

}
