package com.willbsoon.na.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.willbsoon.na.api.advice.exception.CUserNotFoundException;
import com.willbsoon.na.api.entity.User;
import com.willbsoon.na.api.model.response.CommonResult;
import com.willbsoon.na.api.model.response.ListResult;
import com.willbsoon.na.api.model.response.SingleResult;
import com.willbsoon.na.api.repo.UserJpaRepo;
import com.willbsoon.na.api.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

@Api(tags= {"2. User"})
@RestController
@AllArgsConstructor
@RequestMapping(value="/v1")
public class UserController {
	private final UserJpaRepo userJpaRepo;
	private final ResponseService responseService;
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", 
				required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value="회원조회", notes="모든 회원을 조회한다")
	@GetMapping(value="/user")
	public ListResult<User> findAllUser(){
		return responseService.getListResult(userJpaRepo.findAll());
	}
	
	@ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", 
        		required = false, dataType = "String", paramType = "header")
	})
	@ApiOperation(value="회원 단건 조회", notes="회원 번호로 한명의 회원을 조회한다")
	@GetMapping(value="/user/{msrl}")
	public SingleResult<User> findUserbyId(
			@ApiParam(value="회원 번호", required=true) @PathVariable Long msrl,
			@ApiParam(value="언어", defaultValue = "ko") @RequestParam String lang){
//		return responseService.getSingleResult(userJpaRepo.findById(msrl).orElse(null));
//		return responseService.getSingleResult(userJpaRepo.findById(msrl).orElseThrow(Exception::new));
		return responseService.getSingleResult(userJpaRepo.findById(msrl).
				orElseThrow(CUserNotFoundException::new));
	}
	
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", 
        		required = true, dataType = "String", paramType = "header")
    })	
	@ApiOperation(value="회원입력", notes="회원을 생성한다")
	@PostMapping(value="/user")
	public SingleResult<User> save(
					@ApiParam(value = "아이디", required = true)@RequestParam String uid,
					@ApiParam(value = "이름", required = true)@RequestParam String name){
		User user = new User().builder()
				.name(name)
				.uid(uid)
				.build();
		return responseService.getSingleResult(userJpaRepo.save(user));
	}
	
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", 
        		required = true, dataType = "String", paramType = "header")
    })
	@ApiOperation(value="회원정보 수정", notes = "회원의 정보를 수정한다")
	@PutMapping(value="/user")
	public SingleResult<User> modify(
			@ApiParam(value = "회원 번호", required = true)@RequestParam Long msrl,
			@ApiParam(value = "회원 아이디", required = true)@RequestParam String uid,
			@ApiParam(value = "회원 이름", required = true)@RequestParam String name ){
		User user = new User().builder()
				.msrl(msrl)
				.uid(uid)
				.name(name)
				.build();
		return responseService.getSingleResult(userJpaRepo.save(user));
	}
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", 
        		required = true, dataType = "String", paramType = "header")
    })
	@ApiOperation(value = "회원 삭제", notes = "회원 번호로 회원 정보를 삭제한다.")
	@DeleteMapping(value = "/user/{msrl}")
	public CommonResult delete(
			@ApiParam(value = "회원 번호", required = true)@PathVariable Long msrl) {
		userJpaRepo.deleteById(msrl);
		return responseService.getSuccessResult();
	}
	
	
	
	
}
