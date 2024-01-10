package org.lyflexi.framework.web.servlet;

public interface ViewResolver {
	View resolveViewName(String viewName) throws Exception;

}
