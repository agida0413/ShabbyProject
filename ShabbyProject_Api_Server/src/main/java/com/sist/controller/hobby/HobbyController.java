package com.sist.controller.hobby;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dto.api.ResponseDTO;
import com.sist.dto.hobby.HobbyDTO;
import com.sist.dto.hobby.ResponseHobby;
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
	
	@GetMapping("/{keyword}")
	public  ResponseEntity<ResponseDTO<ResponseHobby>> findHobby(@PathVariable String keyword){
		return hobbyService.findHobby(keyword);
	}
}
