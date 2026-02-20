package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Aspect
@Log4j2
@Component
public class LogAspect {
	
	// 특정 객체의 메소드가 실행될 때 전달되는 파라미터를 자동으로 로깅
	// @Before: 타겟 메소드의 실행 전에 동작할 것을 명시
	// execution(): 포인트컷 표현식 중 하나로 문자열로 포인트컷 설정을 지정 
	// * org.zerock.service.*.*(..): 
	// 리턴타입 패키지이름.클래스.메소드(매개변수):
	// org.zerock.service 패키지 안에 있는 모든 클래스의 모든 메소드 실행 시 해당 어노테이션을 가진 메소드 실행
	@Before("execution(* org.zerock.service.*.*(..))")
	public void logParam(JoinPoint jp) { // JoinPoint: 현재 실행 중인 메소드 정보, 전달 된 파라미터, 타겟 객체 등
		log.info("---------------------");
		log.info("logParam()");

		Object[] params = jp.getArgs();
		log.info(Arrays.toString(params));
		log.info(jp.getTarget());
		
		log.info("---------------------");
	}
	
	@Around("execution(* org.zerock.service.*.*(..))")
	// @Around는 JoinPoint가 아니라 ProceedingJoinPoint를 사용해야함
	public Object logTime(ProceedingJoinPoint pjp) throws Throwable {
		log.info("---------------------");
		log.info("logTime()");
		
		long start = System.currentTimeMillis();
		Object ob = pjp.proceed();	// @Around는 이 코드를 직접 호출해야 타겟 메소드가 실제로 실행됨
		
		long end = System.currentTimeMillis();
		long spendTime = end - start;
		
		log.info("ProcessTime: " + spendTime + "MS");
		
		log.info("---------------------");
		return ob;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
