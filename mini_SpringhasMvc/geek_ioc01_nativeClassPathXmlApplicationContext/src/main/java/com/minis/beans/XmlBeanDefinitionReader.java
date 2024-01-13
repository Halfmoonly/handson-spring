package com.minis.beans;

import com.minis.core.Resource;
import org.dom4j.Element;

public class XmlBeanDefinitionReader {
	BeanFactory bf;
	public XmlBeanDefinitionReader(BeanFactory bf) {
		this.bf = bf;
	}
	public void loadBeanDefinitions(Resource res) {
        while (res.hasNext()) {
        	Element element = (Element)res.next();
            String beanID=element.attributeValue("id");
            String beanClassName=element.attributeValue("class");
            BeanDefinition beanDefinition=new BeanDefinition(beanID,beanClassName);
            this.bf.registerBeanDefinition(beanDefinition);
        }
		
	}
	


}
