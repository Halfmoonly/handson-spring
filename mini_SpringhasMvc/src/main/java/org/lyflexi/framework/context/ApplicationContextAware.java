package org.lyflexi.framework.context;

import org.lyflexi.framework.beans.BeansException;

public interface ApplicationContextAware {
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
