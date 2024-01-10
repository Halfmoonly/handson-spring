package org.lyflexi.framework.web.context.support;

import javax.servlet.ServletContext;
import org.lyflexi.framework.context.ClassPathXmlApplicationContext;
import org.lyflexi.framework.web.context.WebApplicationContext;

public class XmlWebApplicationContext 
					extends ClassPathXmlApplicationContext implements WebApplicationContext{
	private ServletContext servletContext;
	
	public XmlWebApplicationContext(String fileName) {
		super(fileName);
	}

	@Override
	public ServletContext getServletContext() {
		return this.servletContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
