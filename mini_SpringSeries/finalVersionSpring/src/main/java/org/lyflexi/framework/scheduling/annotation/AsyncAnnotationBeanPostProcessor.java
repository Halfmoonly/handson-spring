package org.lyflexi.framework.scheduling.annotation;

import java.lang.reflect.Method;

import org.lyflexi.framework.aop.framework.AopProxyFactory;
import org.lyflexi.framework.aop.framework.DefaultAopProxyFactory;
import org.lyflexi.framework.aop.framework.ProxyFactoryBean;
import org.lyflexi.framework.beans.BeansException;
import org.lyflexi.framework.beans.factory.BeanFactory;
import org.lyflexi.framework.beans.factory.BeanFactoryAware;
import org.lyflexi.framework.beans.factory.annotation.Autowired;
import org.lyflexi.framework.beans.factory.config.BeanPostProcessor;
import org.lyflexi.framework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.lyflexi.framework.aop.Advisor;
import org.lyflexi.framework.aop.AsyncExecutionInterceptor;
import org.lyflexi.framework.aop.MethodInterceptor;

public class AsyncAnnotationBeanPostProcessor implements BeanPostProcessor,BeanFactoryAware{
	private BeanFactory beanFactory;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Object result = bean;
		
		Class<?> clazz = bean.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		if(methods!=null){
			for(Method method : methods){
				boolean isAsync = method.isAnnotationPresent(Async.class);
	
				if(isAsync){
					System.out.println("AsyncAnnotationBeanPostProcessor is Async. "); 
					AopProxyFactory proxyFactory = new DefaultAopProxyFactory();
					ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
					Advisor advisor = (Advisor)beanFactory.getBean("asyncAnnotationAdvisor");
					MethodInterceptor methodInterceptor = (AsyncExecutionInterceptor)beanFactory.getBean("asyncExecutionInterceptor");
					advisor.setMethodInterceptor(methodInterceptor);
					proxyFactoryBean.setTarget(bean);
					proxyFactoryBean.setBeanFactory(beanFactory);
					proxyFactoryBean.setAopProxyFactory(proxyFactory);
					proxyFactoryBean.setInterceptorName("asyncAnnotationAdvisor");
					bean = proxyFactoryBean;
					return proxyFactoryBean;

				}
			}
		}
		
		return result;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}


	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

}
