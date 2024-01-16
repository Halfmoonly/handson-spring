package com.minis.aop.framework;

import com.minis.aop.Advisor;

public class DefaultAopProxyFactory implements AopProxyFactory {

	@Override
	public AopProxy createAopProxy(Object target, Advisor advisor) {
		return new JdkDynamicAopProxy(target, advisor);
	}
}
