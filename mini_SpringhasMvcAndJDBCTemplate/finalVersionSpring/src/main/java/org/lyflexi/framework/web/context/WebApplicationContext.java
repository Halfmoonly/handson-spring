package org.lyflexi.framework.web.context;

import javax.servlet.ServletContext;

import org.lyflexi.framework.context.ApplicationContext;

public interface WebApplicationContext extends ApplicationContext {
	String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

	ServletContext getServletContext();
	void setServletContext(ServletContext servletContext);
}
