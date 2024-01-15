package org.lyflexi.framework.beans.factory.config;

import org.lyflexi.framework.beans.BeansException;
import org.lyflexi.framework.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory  extends BeanFactory{
	int AUTOWIRE_NO = 0;
	int AUTOWIRE_BY_NAME = 1;
	int AUTOWIRE_BY_TYPE = 2;

	Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
			throws BeansException;

	Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
			throws BeansException;

}
