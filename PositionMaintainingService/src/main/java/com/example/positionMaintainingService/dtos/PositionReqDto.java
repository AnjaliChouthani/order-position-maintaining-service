package com.example.positionMaintainingService.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PositionReqDto {
	
	@NotBlank
	private String eventId;
	@NotBlank
	private String symbol;
	
	@NotNull
	@Pattern(regexp = "BUY|SELL")
	private String transactionType;
	@Positive
	@NotNull
	private Long quantity;

}
