package com.minis.test;

import com.minis.beans.NoSuchBeanDefinitionException;
import com.minis.context.ClassPathXmlApplicationContext;

public class Test1 {

	public static void main(String[] args) throws NoSuchBeanDefinitionException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
	    AService aService=(AService)ctx.getBean("aservice");
	    aService.sayHello();
	}

}
