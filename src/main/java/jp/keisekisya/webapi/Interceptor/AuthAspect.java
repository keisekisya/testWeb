package jp.keisekisya.webapi.Interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AuthAspect {

	@Before("execution(* jp.keisekisya.webapi.ctrl..*(..))")
	public void beforeMethod(JoinPoint joinPoint) {
		Signature method = joinPoint.getSignature();
		log.info("[start] Auth method {}", method.getName());
	}

	@After("execution(* jp.keisekisya.webapi.ctrl..*(..))")
	public void afterMethod(JoinPoint joinPoint) {
		Signature method = joinPoint.getSignature();
		log.info("[end] Auth method {}", method.getName());
	}

	@Around("execution(* jp.keisekisya.webapi.ctrl..*(..))")
	public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long end = System.currentTimeMillis();
		// 経過時間を表示
		log.info("Execution time: " + (end - start) + " ms");
		return result;
	}
}
