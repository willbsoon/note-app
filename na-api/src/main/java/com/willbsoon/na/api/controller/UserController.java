package com.willbsoon.na.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.willbsoon.na.api.entity.User;
import com.willbsoon.na.api.repo.UserJpaRepo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value="/v1")
public class UserController {
	private final UserJpaRepo userJpaRepo;
	
	@ApiOperation(value="회원조회", notes="모든 회원을 조회한다")
	@GetMapping(value="/user")
	public List<User > findAllUser(){
		return userJpaRepo.findAll();
	}
	
	@ApiOperation(value="회원입력", notes="회원을 생성한다")
	@PostMapping(value="/user")
	public User save(@ApiParam(value = "아이디", required = true)@RequestParam String uid,
					@ApiParam(value = "이름", required = true)@RequestParam String name){
		User user = new User().builder()
				.name(name)
				.uid(uid)
				.build();
		return userJpaRepo.save(user);
	}
	
	@GetMapping(value="/user1")
	public String test(){
		return "test";
	}

}
