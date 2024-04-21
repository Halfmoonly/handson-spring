package org.lyflexi.frameworktest;

import org.lyflexi.framework.beans.BeansException;
import org.lyflexi.framework.beans.factory.BeanFactory;
import org.lyflexi.framework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

	@Override
	public void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println(".........MyBeanFactoryPostProcessor...........");
		
	}

}
