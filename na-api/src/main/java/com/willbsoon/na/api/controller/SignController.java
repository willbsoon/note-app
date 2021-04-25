package com.willbsoon.na.api.controller;

import java.util.Collections;
import java.util.Optional;

import com.willbsoon.na.api.advice.exception.CUserExistException;
import com.willbsoon.na.api.advice.exception.CUserNotFoundException;
import com.willbsoon.na.api.entity.KakaoProfile;
import com.willbsoon.na.api.service.user.KakaoService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
	private final KakaoService kakaoService;
	
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

	@ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
	@PostMapping(value = "/signin/{provider}")
	public SingleResult<String> signinByProvider(
			@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
			@ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {
		KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
		User user = userJpaRepo.findByUidAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(CUserNotFoundException::new);
		return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
	}

	@ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
	@PostMapping(value = "/signup/{provider}")
	public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
									   @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
									   @ApiParam(value = "이름", required = true) @RequestParam String name) {
		KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
		Optional<User> user = userJpaRepo.findByUidAndProvider(String.valueOf(profile.getId()), provider);
		if(user.isPresent())
			throw new CUserExistException();
		userJpaRepo.save(User.builder()
				.uid(String.valueOf(profile.getId()))
				.provider(provider)
				.name(name)
				.roles(Collections.singletonList("ROLE_USER"))
				.build());
		return responseService.getSuccessResult();
	}
}
