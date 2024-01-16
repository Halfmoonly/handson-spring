package org.lyflexi.framework.scheduling.annotation;

import org.lyflexi.framework.aop.Advice;
import org.lyflexi.framework.aop.Advisor;
import org.lyflexi.framework.aop.AsyncExecutionInterceptor;
import org.lyflexi.framework.aop.MethodInterceptor;

public class AsyncAnnotationAdvisor  implements Advisor{
	MethodInterceptor methodInterceptor;

	@Override
	public MethodInterceptor getMethodInterceptor() {
		return this.methodInterceptor;
	}

	@Override
	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		this.methodInterceptor= methodInterceptor;
	}

	@Override
	public Advice getAdvice() {
		// TODO Auto-generated method stub
		return null;
	}

}
