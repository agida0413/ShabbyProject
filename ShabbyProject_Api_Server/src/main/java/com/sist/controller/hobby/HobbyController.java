package com.sist.controller.hobby;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.hobby.HobbyDTO;
import com.sist.dto.hobby.ResponseHobbyDTO;
import com.sist.service.hobby.HobbyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hobby")
public class HobbyController {
	
	private final HobbyService hobbyService;
	
	@GetMapping
	public ResponseEntity<ResponseDTO<HobbyDTO>> allHobby(){
		return hobbyService.allHobby();
	}
	
	@GetMapping("/{keyword}/{page}")
	public  ResponseEntity<ResponseDTO<ResponseHobbyDTO>> findHobby(@PathVariable String keyword,@PathVariable int page){
		return hobbyService.findHobby(keyword,page);
	}
}
