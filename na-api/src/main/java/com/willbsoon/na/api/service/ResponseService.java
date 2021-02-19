package com.willbsoon.na.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.willbsoon.na.api.model.response.CommonResult;
import com.willbsoon.na.api.model.response.ListResult;
import com.willbsoon.na.api.model.response.SingleResult;

@Service
public class ResponseService {
	public enum CommonResponse{
		SUCCESS(0,"성공하였습니다."),
		FAIL(-1,"실패하였습니다.");
		
		int code;
		String msg;
		
		CommonResponse(int code, String msg){
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return this.code;
		}
		public String getMsg() {
			return this.msg;
		}
	}
	
	public <T> SingleResult<T> getSingleResult(T data){
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		setSuccessResult(result);
		return result;
	}
	public <T> ListResult<T> getListResult(List<T> list){
		ListResult<T> result = new ListResult<>();
		result.setList(list);
		setSuccessResult(result);
		return result;
	}
	public CommonResult getSuccessResult() {
		CommonResult result = new CommonResult();
		setSuccessResult(result);
		return result;
	}
//	public CommonResult getFailResult() {
//		CommonResult result = new CommonResult();
//		result.setSuccess(false);
//		result.setCode(CommonResponse.FAIL.getCode());
//		result.setMsg(CommonResponse.FAIL.getMsg());
//		return result;
//	}
	public CommonResult getFailResult(int code, String msg) {
	    CommonResult result = new CommonResult();
	    result.setSuccess(false);
	    result.setCode(code);
	    result.setMsg(msg);
	    return result;
	}
	public CommonResult setSuccessResult(CommonResult result) {
		result.setSuccess(true);
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMsg(CommonResponse.SUCCESS.getMsg());
		return result;
	}

}
