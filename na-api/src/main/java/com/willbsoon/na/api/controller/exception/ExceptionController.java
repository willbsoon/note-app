package com.willbsoon.na.api.controller.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willbsoon.na.api.advice.exception.CAccessDeniedException;
import com.willbsoon.na.api.advice.exception.CAuthenticationEntryPointException;
import com.willbsoon.na.api.model.response.CommonResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/exception")
public class ExceptionController {
	
	@GetMapping(value = "/entrypoint")
	public CommonResult entrypointException() {
		throw new CAuthenticationEntryPointException();
	}
	@GetMapping(value = "/accessdenied")
	public CommonResult accessdeniedException() {
		throw new CAccessDeniedException();
	}
	
}


