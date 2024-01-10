package org.lyflexi.framework.context;

import org.lyflexi.framework.beans.BeansException;
import org.lyflexi.framework.beans.factory.ListableBeanFactory;
import org.lyflexi.framework.beans.factory.config.BeanFactoryPostProcessor;
import org.lyflexi.framework.beans.factory.config.ConfigurableBeanFactory;
import org.lyflexi.framework.beans.factory.config.ConfigurableListableBeanFactory;
import org.lyflexi.framework.core.env.Environment;
import org.lyflexi.framework.core.env.EnvironmentCapable;

public interface ApplicationContext 
		extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher{
	String getApplicationName();
	long getStartupDate();
	ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
	void setEnvironment(Environment environment);
	Environment getEnvironment();
	void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);
	void refresh() throws BeansException, IllegalStateException;
	void close();
	boolean isActive();

}
