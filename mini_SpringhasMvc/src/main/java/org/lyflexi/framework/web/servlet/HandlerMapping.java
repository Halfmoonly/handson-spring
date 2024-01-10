package org.lyflexi.framework.web.servlet;

import javax.servlet.http.HttpServletRequest;

import org.lyflexi.framework.web.method.HandlerMethod;

public interface HandlerMapping {
	HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
