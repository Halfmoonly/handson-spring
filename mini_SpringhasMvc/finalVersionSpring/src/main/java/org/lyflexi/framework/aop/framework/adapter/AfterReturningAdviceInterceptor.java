package org.lyflexi.framework.aop.framework.adapter;

import org.lyflexi.framework.aop.AfterAdvice;
import org.lyflexi.framework.aop.AfterReturningAdvice;
import org.lyflexi.framework.aop.MethodInterceptor;
import org.lyflexi.framework.aop.MethodInvocation;

public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice {

	private final AfterReturningAdvice advice;

	public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
		this.advice = advice;
	}

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		Object retVal = mi.proceed();
		this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
		return retVal;
	}

}
