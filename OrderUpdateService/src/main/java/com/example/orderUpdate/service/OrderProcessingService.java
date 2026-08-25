package com.example.orderUpdate.service;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import com.example.orderUpdate.TransactionType;
import com.example.orderUpdate.dtos.OrderEventDto;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderProcessingService {
	
	
	@Value("${file.path}")
	private String path;
	
	@Value("${position.maintained.service.url}")
	private String url;
	
	 @Autowired
	private RestTemplate restTemplate;
	

	public void processCSV() {
		
		
		try(BufferedReader reader =new BufferedReader(new FileReader(path))){
			
			reader.readLine();  
			
			String line;
			
			 int eventCount = 0;
		     long startTime = System.currentTimeMillis();
			while((line=reader.readLine())!=null) {
				
				log.info("Current Row data {} ",line );
				
				//create dtos
				
			String[] rowData=line.split(",",-1);
			if(!isValidate(rowData)) {
				continue;
			}
		OrderEventDto orderEvent=createDtos(rowData);
		log.info("Order Event Created {} ",orderEvent);
		 
		//call positioning api
		
		
	try {
	ResponseEntity<String> response=restTemplate.postForEntity(url, orderEvent, String.class);
	log.info("Event {} sent successfully", orderEvent.getEventId());
	log.info(response.getBody());
	log.info(response.getStatusCode().toString());
	}
	catch (Exception e) {
		log.error("Failed to send event {} ", orderEvent.getEventId(),e);
	}
		
		
	eventCount++;
	log.info("Event Id: {}  | count {} ",orderEvent.getEventId(),eventCount);
		if(eventCount==50) {
			long elapsedTime=System.currentTimeMillis()-startTime;
			log.info("50 event sent or processed {} ", elapsedTime);
			
			
			
			if(elapsedTime<1000) {
				long waitTime=1000-elapsedTime;
				log.info("Rate limit reached. Waiting {} ms",
                        waitTime);
				
				try {
					Thread.sleep(waitTime);
				}
				catch (InterruptedException e) {
					   Thread.currentThread().interrupt();
				        log.error("Processing interrupted", e);
				        break;
				}
			}
			
			eventCount = 0;
		    startTime = System.currentTimeMillis();

		    log.info("Starting next batch of 50 events");
		}
		
				
				
			}
		}
		catch(IOException ex) {
			log.error("Unable to read CSV file ",ex.getMessage());
		}
		
		
		
	}
	
	
	
	
	public OrderEventDto createDtos(String line[]) {
		
		OrderEventDto eventDto=new OrderEventDto();
		eventDto.setEventId(line[0]);
		eventDto.setSymbol(line[1]);
		eventDto.setTransactionType(TransactionType.valueOf(line[2]));
		eventDto.setQuantity(Long.valueOf(line[3]));
		return eventDto;
		
	}
	
	
	
	public boolean isValidate(String []rowData) {
		
		if(rowData.length!=4) {
			log.info("row data does not contain 4 column");
			return false;
		}
		
		if(rowData[0].isBlank()) {
			log.info("Event Id is missing ");
			return false;
		}
		if(rowData[1].isBlank()) {
			log.info("Symbol is missing "); return false;
		}
		
		if(!rowData[2].equals("BUY") && !rowData[2].equals("SELL")) {
			log.info("Transaction Type is Invalid {} ", rowData[2]);
			return false;
		}
		
		try {
		Long quantity=Long.valueOf(rowData[3]);
		if(quantity<=0) {
			log.info("Invalid Quantity, It must be positive {} ", quantity); return false;
		}
		
		}
		catch (NumberFormatException  e) {

log.error("Exception : Invalid quantity ",e.getMessage());
return false;
		}
		return true;
		
	}

}
