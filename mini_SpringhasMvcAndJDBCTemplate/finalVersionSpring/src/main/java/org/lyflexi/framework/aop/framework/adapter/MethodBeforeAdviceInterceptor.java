package org.lyflexi.framework.aop.framework.adapter;

import org.lyflexi.framework.aop.MethodBeforeAdvice;
import org.lyflexi.framework.aop.MethodInterceptor;
import org.lyflexi.framework.aop.MethodInvocation;

public class MethodBeforeAdviceInterceptor implements MethodInterceptor {
	private final MethodBeforeAdvice advice;

	public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
		this.advice = advice;
	}

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
		return mi.proceed();
	}
}
