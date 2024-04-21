package org.lyflexi.framework.beans.factory.support;

import org.lyflexi.framework.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
	void registerBeanDefinition(String name, BeanDefinition bd);
	void removeBeanDefinition(String name);
	BeanDefinition getBeanDefinition(String name);
	boolean containsBeanDefinition(String name);
}
