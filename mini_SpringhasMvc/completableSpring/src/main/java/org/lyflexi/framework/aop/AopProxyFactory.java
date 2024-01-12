package org.lyflexi.framework.aop;

public interface AopProxyFactory {
	AopProxy createAopProxy(Object target, PointcutAdvisor adviseor);
}
