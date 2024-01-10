package org.lyflexi.framework.beans.factory.support;

import org.lyflexi.framework.beans.BeansException;
import org.lyflexi.framework.beans.factory.FactoryBean;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

	protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
		return factoryBean.getObjectType();
	}

	protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
			Object object = doGetObjectFromFactoryBean(factory, beanName);
			try {
				object = postProcessObjectFromFactoryBean(object, beanName);
			} catch (BeansException e) {
				e.printStackTrace();
			}
			return object;
	}

	private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName) {

		Object object = null;
		try {
			object = factory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return object;
	}

	protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
		return object;
	}

	protected FactoryBean<?> getFactoryBean(String beanName, Object beanInstance) throws BeansException {
		if (!(beanInstance instanceof FactoryBean)) {
			throw new BeansException(
					"Bean instance of type [" + beanInstance.getClass() + "] is not a FactoryBean");
		}
		return (FactoryBean<?>) beanInstance;
	}
}
