package org.lyflexi.framework.aop;

public interface Advisor {
	MethodInterceptor getMethodInterceptor();
	void setMethodInterceptor(MethodInterceptor methodInterceptor);
	Advice getAdvice();
}
