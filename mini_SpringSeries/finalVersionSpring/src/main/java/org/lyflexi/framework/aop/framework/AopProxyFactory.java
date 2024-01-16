package org.lyflexi.framework.aop.framework;

import org.lyflexi.framework.aop.Advisor;
import org.lyflexi.framework.aop.PointcutAdvisor;

public interface AopProxyFactory {
	AopProxy createAopProxy(Object target, Advisor adviseor);
}
