package org.lyflexi.framework.beans.factory.config;

import org.lyflexi.framework.beans.BeansException;
import org.lyflexi.framework.beans.factory.BeanFactory;

public interface BeanPostProcessor {
	Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

	Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
	
	void setBeanFactory(BeanFactory beanFactory);

}
