package org.lyflexi.framework.beans.factory;

import org.lyflexi.framework.beans.BeansException;

public interface BeanFactory {
    Object getBean(String name) throws BeansException;
	boolean containsBean(String name);
	boolean isSingleton(String name);
	boolean isPrototype(String name);
	Class<?> getType(String name);

}
