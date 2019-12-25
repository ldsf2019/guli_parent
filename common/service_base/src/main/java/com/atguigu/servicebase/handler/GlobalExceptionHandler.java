package com.atguigu.servicebase.handler;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类，处理 500 异常，使用 AOP，在出现异常后拦截异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	//拦截所有异常并返回异常信息，而后继续运行程序（全局异常处理）
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public R error(Exception e){
		e.printStackTrace();
		return R.error();
	}

	//特定异常处理
	@ExceptionHandler(ArithmeticException.class)
	@ResponseBody
	public R error(ArithmeticException e){
		e.printStackTrace();
		return R.error().message("执行了 ArithmeticException 异常处理");
	}

	//自定义异常处理
	@ExceptionHandler(GuliException.class)
	@ResponseBody
	public R error(GuliException e){
		e.printStackTrace();
		//返回异常状态，错误状态码和信息
		return R.error().code(e.getCode()).message(e.getMsg());
	}


}