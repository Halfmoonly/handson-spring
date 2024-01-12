package org.lyflexi.framework.aop;

import java.lang.reflect.Method;

public interface MethodInvocation {
	Method getMethod();
	Object[] getArguments();
	Object getThis();
	Object proceed() throws Throwable;
}
