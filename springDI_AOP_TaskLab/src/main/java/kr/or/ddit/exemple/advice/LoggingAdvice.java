package kr.or.ddit.exemple.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAdvice {
		private static final Logger logger = 
				LoggerFactory.getLogger(LoggingAdvice.class);
		
		@Pointcut("execution(* kr.or.ddit.exemple..service.*.*(..))")
		public void dummy() {}
		
		@Before("dummy()")
		public void beforeAdvice(JoinPoint joinPoint) { 
			// 하나의 타겟이 있을때 그 하나의 타겟을 대상으로 weaving 한다면 그 전에 할지 그 후에할지 판단은 joinpoint / method 호출 joinpoint 만 지원
			// method 호출 이전 weaving : beforeAdvice 
			// method 호출 이후 weaving : post?Advice 
			Object target = joinPoint.getTarget(); 
			String className = target.getClass().getName();
			Signature signature = joinPoint.getSignature(); //메서드 선언부 
			String methodName = signature.getName();
			Object[] args = joinPoint.getArgs();
			logger.info("로직({}.{})을 대상으로 전달된 파라미터 : {}"
						, className, methodName, args);
		}
		
		@AfterReturning(pointcut = "dummy()", returning = "retValue")
		public void afterAdvice(JoinPoint joinPoint, Object retValue) {
			Object target = joinPoint.getTarget(); 
			String className = target.getClass().getName();
			Signature signature = joinPoint.getSignature(); //메서드 선언부 
			String methodName = signature.getName();
			Object[] args = joinPoint.getArgs();
			logger.info("{}.{}({}) 의 실행 결과 : {}", className, methodName, args);
			
		}
		@AfterThrowing(pointcut = "dummy()", throwing ="e")
		public void afterTrowingAdvice(JoinPoint joinPoint, Throwable e) {
			Object target = joinPoint.getTarget(); 
			String className = target.getClass().getName();
			Signature signature = joinPoint.getSignature(); //메서드 선언부 
			String methodName = signature.getName();
			Object[] args = joinPoint.getArgs();
			logger.error(String.format("%s.%s(%s)"
								, className, methodName, args),e);
		}
		@Around("dummy()")
		public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
			Object target = joinPoint.getTarget(); 
			String className = target.getClass().getName();
			Signature signature = joinPoint.getSignature(); //메서드 선언부 
			String methodName = signature.getName();
			Object[] args = joinPoint.getArgs();
			
			long start = System.currentTimeMillis();
			
			Object retValue;
			try {
				retValue = joinPoint.proceed(args);
				long end = System.currentTimeMillis();
				
				 logger.info("{}.{}({})의 실행 결과 : {} 소요시간 : {}ms",className,methodName,args,retValue,(end-start));
				
				return retValue;
			} catch (Throwable e) {
				logger.error(String.format("%s.%s(%s)"
						, className, methodName, args),e);
				throw e;
			} 			
			
		}
		
}
