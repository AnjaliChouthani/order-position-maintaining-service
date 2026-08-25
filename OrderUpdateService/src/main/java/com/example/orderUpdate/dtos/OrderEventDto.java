package com.example.orderUpdate.dtos;

import com.example.orderUpdate.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderEventDto {
	
	@NotBlank
	private String eventId;
	@NotBlank
	private String symbol;
	
	@NotNull
	private TransactionType transactionType;
	@Positive
	@NotNull
	private Long quantity;
	
	

}
