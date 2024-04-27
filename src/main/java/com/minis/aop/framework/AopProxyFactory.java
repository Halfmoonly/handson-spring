package com.minis.aop.framework;

import com.minis.aop.Advisor;

public interface AopProxyFactory {
	AopProxy createAopProxy(Object target, Advisor adviseor);
}
