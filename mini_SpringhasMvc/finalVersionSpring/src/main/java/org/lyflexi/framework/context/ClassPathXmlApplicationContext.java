package org.lyflexi.framework.context;

import java.util.ArrayList;
import java.util.List;

import org.lyflexi.framework.beans.BeansException;
import org.lyflexi.framework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.lyflexi.framework.beans.factory.config.AbstractAutowireCapableBeanFactory;
import org.lyflexi.framework.beans.factory.config.BeanDefinition;
import org.lyflexi.framework.beans.factory.config.BeanFactoryPostProcessor;
import org.lyflexi.framework.beans.factory.config.BeanPostProcessor;
import org.lyflexi.framework.beans.factory.config.ConfigurableListableBeanFactory;
import org.lyflexi.framework.beans.factory.support.DefaultListableBeanFactory;
import org.lyflexi.framework.beans.factory.xml.XmlBeanDefinitionReader;
import org.lyflexi.framework.core.ClassPathXmlResource;
import org.lyflexi.framework.core.Resource;
import org.lyflexi.framework.core.env.Environment;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext{
	DefaultListableBeanFactory beanFactory;
	private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors =
			new ArrayList<BeanFactoryPostProcessor>();	

    public ClassPathXmlApplicationContext(String fileName){
    	this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
    	Resource res = new ClassPathXmlResource(fileName);
    	DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(res);
        
        this.beanFactory = bf;
        
        if (isRefresh) {
            try {
				refresh();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (BeansException e) {
				e.printStackTrace();
			}
        }
    }

	@Override
	public
	void registerListeners() {
		String[] bdNames = this.beanFactory.getBeanDefinitionNames();
		for (String bdName : bdNames) {
			Object bean = null;
			try {
				bean = getBean(bdName);
			} catch (BeansException e1) {
				e1.printStackTrace();
			}

			if (bean instanceof ApplicationListener) {
				this.getApplicationEventPublisher().addApplicationListener((ApplicationListener<?>) bean);
			}
		}

	}

	@Override
	public
	void initApplicationEventPublisher() {
		ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
		this.setApplicationEventPublisher(aep);
	}

	@Override
	public
	void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
		
		String[] bdNames = this.beanFactory.getBeanDefinitionNames();
		for (String bdName : bdNames) {
			BeanDefinition bd = this.beanFactory.getBeanDefinition(bdName);
			String clzName = bd.getClassName();
			Class<?> clz = null;
			try {
				clz = Class.forName(clzName);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			if (BeanFactoryPostProcessor.class.isAssignableFrom(clz)) {
					try {
						this.beanFactoryPostProcessors.add((BeanFactoryPostProcessor) clz.newInstance());
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
			}
		}
		for (BeanFactoryPostProcessor processor : this.beanFactoryPostProcessors) {
			try {
				processor.postProcessBeanFactory(bf);
			} catch (BeansException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public
	void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
System.out.println("try to registerBeanPostProcessors");		
		String[] bdNames = this.beanFactory.getBeanDefinitionNames();
		for (String bdName : bdNames) {
			BeanDefinition bd = this.beanFactory.getBeanDefinition(bdName);
			String clzName = bd.getClassName();
			Class<?> clz = null;
			try {
				clz = Class.forName(clzName);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			if (BeanPostProcessor.class.isAssignableFrom(clz)) {
System.out.println(" registerBeanPostProcessors : " + clzName);		
					try {
						//this.beanFactory.addBeanPostProcessor((BeanPostProcessor) clz.newInstance());
						this.beanFactory.addBeanPostProcessor((BeanPostProcessor)(this.beanFactory.getBean(bdName)));
					} catch (BeansException e) {
						e.printStackTrace();
					}
			}
		}
	}

	@Override
	public
	void onRefresh() {
		this.beanFactory.refresh();
	}

	@Override
	public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
		return this.beanFactory;
	}

	@Override
	public void addApplicationListener(ApplicationListener<?> listener) {
		this.getApplicationEventPublisher().addApplicationListener(listener);
		
	}

	@Override
	public
	void finishRefresh() {
		publishEvent(new ContextRefreshedEvent(this));
		
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		this.getApplicationEventPublisher().publishEvent(event);
		
	}
   
    
}
