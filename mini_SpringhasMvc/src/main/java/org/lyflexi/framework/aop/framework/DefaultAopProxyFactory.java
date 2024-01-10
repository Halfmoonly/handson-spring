package org.lyflexi.framework.aop.framework;

import org.lyflexi.framework.aop.Advisor;
import org.lyflexi.framework.aop.PointcutAdvisor;

public class DefaultAopProxyFactory implements AopProxyFactory {

	@Override
	public AopProxy createAopProxy(Object target, Advisor advisor) {
		return new JdkDynamicAopProxy(target, advisor);
	}
}
