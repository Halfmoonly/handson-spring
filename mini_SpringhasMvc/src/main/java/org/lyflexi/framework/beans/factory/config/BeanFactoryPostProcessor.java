package org.lyflexi.framework.beans.factory.config;

import org.lyflexi.framework.beans.BeansException;
import org.lyflexi.framework.beans.factory.BeanFactory;

public interface BeanFactoryPostProcessor {
	void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
