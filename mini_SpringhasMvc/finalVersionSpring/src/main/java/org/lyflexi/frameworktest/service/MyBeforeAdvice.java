package org.lyflexi.frameworktest.service;

import org.lyflexi.framework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MyBeforeAdvice implements MethodBeforeAdvice{

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("----------my interceptor befor method call----------");
	}

}
