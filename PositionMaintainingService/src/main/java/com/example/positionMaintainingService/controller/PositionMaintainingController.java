package com.example.positionMaintainingService.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.positionMaintainingService.dtos.PositionReqDto;
import com.example.positionMaintainingService.service.PositionMaintainingService;

import jakarta.validation.Valid;

@RestController
public class PositionMaintainingController {
	
	
	
	@Autowired
	PositionMaintainingService positionMaintainingService;
	
	
	@PostMapping("/position/maintaining")
	public ResponseEntity<String> handleNetPositioning(@Valid @RequestBody PositionReqDto reqDto){
		return positionMaintainingService.handleNetPositioning(reqDto);
		
		
	}
	
	
	
	@GetMapping("/position")
	public ResponseEntity<Map<String,Long>>getAllPosition(){
		return ResponseEntity.ok(positionMaintainingService.getAllPosition());
	}

}
