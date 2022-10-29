package com.yama.demo.mysql.annotation;

import com.yama.demo.mysql.pojo.OperationLog;
import com.yama.demo.mysql.service.OperationLogService;
import com.yama.demo.mysql.util.DataUtils;
import com.yama.demo.mysql.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class OperateAdvice {
	
	@Autowired
	private OperationLogService operationLogService;
	

	@Around("execution(* com..controller.*.*(..)) && @annotation(operateLog)")
	public Object insertLogAround(ProceedingJoinPoint pjp , OperateLog operateLog) throws Throwable{
		System.out.println(" *********************************** 记录日志 [start]  ****************************** ");
		
		OperationLog op = new OperationLog();

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		op.setOperateTime(sdf.format(new Date()));
		op.setOperateUser(DataUtils.getRandStr(8));// 从Session中获取当前登录用户 .
		
		op.setOperateClass(pjp.getTarget().getClass().getName());
		op.setOperateMethod(pjp.getSignature().getName());
		
		Object[] args = pjp.getArgs();
		op.setParamAndValue(Arrays.toString(args));



		long start_time = System.currentTimeMillis();

		//明确执行切入点方法
		Object object = pjp.proceed();

		long end_time = System.currentTimeMillis();
		op.setCostTime(end_time - start_time);



		if(object != null){
			op.setReturnClass(object.getClass().getName());
			op.setReturnValue(object.toString());
		}else{
			op.setReturnClass("java.lang.Object");
			op.setReturnValue("void");
		}

		log.error(JsonUtils.obj2JsonString(op));

		operationLogService.insert(op);
		
		System.out.println(" *********************************** 记录日志 [end]  ****************************** ");
		
		return object;
	}
	
}
