package com.willbsoon.na.api.controller;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.willbsoon.na.api.advice.exception.CEmailSigninFailedException;
import com.willbsoon.na.api.config.security.JwtTokenProvider;
import com.willbsoon.na.api.entity.User;
import com.willbsoon.na.api.model.response.CommonResult;
import com.willbsoon.na.api.model.response.SingleResult;
import com.willbsoon.na.api.repo.UserJpaRepo;
import com.willbsoon.na.api.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags={"1.Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1")
public class SignController {
	private final UserJpaRepo userJpaRepo;
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;
	private final PasswordEncoder passwordEncoder;
	
	@ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다")
	@PostMapping(value = "/signin")
	public SingleResult<String> signin(
			@ApiParam(value = "회원id : 이메일", required = true) @RequestParam String uid,
			@ApiParam(value = "패스워드", required = true) @RequestParam String password){
		User user = userJpaRepo.findByUid(uid).orElseThrow(CEmailSigninFailedException::new);
		if(!passwordEncoder.matches(password, user.getPassword()))
			throw new CEmailSigninFailedException();
		
		return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
	}

	@ApiOperation(value = "가입", notes = "회원가입을 한다")
	@PostMapping(value = "/signup")
	public CommonResult signup(
			@ApiParam(value = "회원id : 이메일", required = true) @RequestParam String uid,
			@ApiParam(value = "패스워드", required = true) @RequestParam String password,
			@ApiParam(value = "이름", required = true) @RequestParam String name){
		userJpaRepo.save(User.builder()
				.uid(uid)
				.password(passwordEncoder.encode(password))
				.name(name)
				.roles(Collections.singletonList("ROLE_USER"))
				.build());
		return responseService.getSuccessResult();
	}
	
}
