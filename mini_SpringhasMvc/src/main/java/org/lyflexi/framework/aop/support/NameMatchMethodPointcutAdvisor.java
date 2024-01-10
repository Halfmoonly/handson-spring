package org.lyflexi.framework.aop.support;

import org.lyflexi.framework.aop.Advice;
import org.lyflexi.framework.aop.AfterAdvice;
import org.lyflexi.framework.aop.AfterReturningAdvice;
import org.lyflexi.framework.aop.BeforeAdvice;
import org.lyflexi.framework.aop.MethodBeforeAdvice;
import org.lyflexi.framework.aop.MethodInterceptor;
import org.lyflexi.framework.aop.Pointcut;
import org.lyflexi.framework.aop.PointcutAdvisor;
import org.lyflexi.framework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.lyflexi.framework.aop.framework.adapter.MethodBeforeAdviceInterceptor;

public class NameMatchMethodPointcutAdvisor implements PointcutAdvisor{
	private Advice advice = null;
	private MethodInterceptor methodInterceptor;
	private String mappedName;
	private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();

	public NameMatchMethodPointcutAdvisor() {
	}

	public NameMatchMethodPointcutAdvisor(Advice advice) {
		this.advice = advice;
	}
	
	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}

	public MethodInterceptor getMethodInterceptor() {
		return this.methodInterceptor;
	}


	public void setAdvice(Advice advice) {
		this.advice = advice;

		MethodInterceptor mi = null;

		if (advice instanceof BeforeAdvice) {
			mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice)advice);
		}
		else if (advice instanceof AfterAdvice){
			mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice)advice);
		}
		else if (advice instanceof MethodInterceptor) {
			mi = (MethodInterceptor)advice;
		}
		
		setMethodInterceptor(mi);
	}

	@Override
	public Advice getAdvice() {
		return this.advice;
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}
	
	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
		this.pointcut.setMappedName(this.mappedName);
	}

}
