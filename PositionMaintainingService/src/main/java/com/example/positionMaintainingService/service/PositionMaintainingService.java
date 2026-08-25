package com.example.positionMaintainingService.service;

import java.util.HashMap;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.positionMaintainingService.dtos.PositionReqDto;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PositionMaintainingService {
	
	private final Map<String,Long> result=new ConcurrentHashMap<>();
	
	private final Set<String> eventIdList =
            ConcurrentHashMap.newKeySet();
	
	

	
public ResponseEntity<String> handleNetPositioning(PositionReqDto reqDto){
		
	if(reqDto==null) {
		
		return ResponseEntity.badRequest().body("Request body cannot null");
	}
	
	//check eventIdList is unique or not 
	
	
		if(!eventIdList.add(reqDto.getEventId())) {
			return ResponseEntity.ok().body("!!!! Duplicate Event Id !!!! ");
			
		}
	
	long positionChange;
	if(reqDto.getTransactionType().equals("SELL")) {
		positionChange=-reqDto.getQuantity();
	}
	else {
		positionChange= reqDto.getQuantity();
	}
	
	result.merge(reqDto.getSymbol(), positionChange, Long::sum);

	return ResponseEntity.ok("Position Maintained Successfully ");
		
	}








public Map<String,Long> getAllPosition() {
	log.info("finding current position for all symbol ");
	
	return new HashMap<>(result);
}

}
