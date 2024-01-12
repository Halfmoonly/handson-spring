package org.lyflexi.frameworktest;

import org.lyflexi.framework.context.ApplicationListener;
import org.lyflexi.framework.context.ContextRefreshedEvent;

public class MyListener implements ApplicationListener<ContextRefreshedEvent> {
	   @Override
	   public void onApplicationEvent(ContextRefreshedEvent event) {
	      System.out.println(".........refreshed.........beans count : " + event.getApplicationContext().getBeanDefinitionCount());
	   }

}

