package org.lyflexi.framework.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.lyflexi.framework.aop.Advisor;
import org.lyflexi.framework.aop.MethodInterceptor;
import org.lyflexi.framework.aop.MethodInvocation;
import org.lyflexi.framework.aop.PointcutAdvisor;
import org.lyflexi.framework.scheduling.annotation.AsyncAnnotationAdvisor;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
	Object target;
	Advisor advisor;  //should be a list to support multiple advisors
	
	public JdkDynamicAopProxy(Object target, Advisor advisor) {
		this.target = target;
		this.advisor = advisor;
	}

	@Override
	public Object getProxy() {
		Object obj = Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
		return obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Class<?> targetClass = (target != null ? target.getClass() : null);
		
		if (this.advisor instanceof PointcutAdvisor) {
			if (((PointcutAdvisor)this.advisor).getPointcut().getMethodMatcher().matches(method, targetClass)) {
				//if (method.getName().equals("doAction")) {
				MethodInterceptor interceptor = this.advisor.getMethodInterceptor();
				MethodInvocation invocation =
							new ReflectiveMethodInvocation(proxy, target, method, args, targetClass);
				return interceptor.invoke(invocation);
			}
		}
		if (this.advisor instanceof AsyncAnnotationAdvisor) {
			MethodInterceptor interceptor = this.advisor.getMethodInterceptor();
			
			MethodInvocation invocation =
						new ReflectiveMethodInvocation(proxy, target, method, args, targetClass);
			return interceptor.invoke(invocation);			
		}
		return null;
	}
}
