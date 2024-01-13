package org.lyflexi.framework.web.method.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lyflexi.framework.beans.BeansException;
import org.lyflexi.framework.context.ApplicationContext;
import org.lyflexi.framework.context.ApplicationContextAware;
import org.lyflexi.framework.http.converter.HttpMessageConverter;
import org.lyflexi.framework.web.bind.WebDataBinder;
import org.lyflexi.framework.web.bind.annotation.PathVariable;
import org.lyflexi.framework.web.bind.annotation.ResponseBody;
import org.lyflexi.framework.web.bind.support.WebBindingInitializer;
import org.lyflexi.framework.web.bind.support.WebDataBinderFactory;
import org.lyflexi.framework.web.context.WebApplicationContext;
import org.lyflexi.framework.web.method.HandlerMethod;
import org.lyflexi.framework.web.servlet.HandlerAdapter;
import org.lyflexi.framework.web.servlet.ModelAndView;

public class RequestMappingHandlerAdapter implements HandlerAdapter,ApplicationContextAware {
	private ApplicationContext applicationContext= null;
	private WebBindingInitializer webBindingInitializer = null;
	private HttpMessageConverter messageConverter = null;

	public HttpMessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(HttpMessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	public RequestMappingHandlerAdapter() {
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return handleInternal(request, response, (HandlerMethod) handler);
	}

	private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handler) {
		ModelAndView mv = null;
		
		try {
			 mv = invokeHandlerMethod(request, response, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;

	}
	
	protected ModelAndView invokeHandlerMethod(HttpServletRequest request,
			HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {

			WebDataBinderFactory binderFactory = new WebDataBinderFactory();
			
			Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
			Object[] methodParamObjs = new Object[methodParameters.length];
			
			int i = 0;
			for (Parameter methodParameter : methodParameters) {
				if (methodParameter.getType()==HttpServletRequest.class) {
					methodParamObjs[i] = request;					
				}
				else if (methodParameter.getType()==HttpServletResponse.class) {
					methodParamObjs[i] = response;					
				}
				else if (methodParameter.isAnnotationPresent(PathVariable.class)) {
					String sServletPath = request.getServletPath();
					int index = sServletPath.lastIndexOf("/");
					String sParam = sServletPath.substring(index+1);
					if (int.class.isAssignableFrom(methodParameter.getType())) {
					    methodParamObjs[i] = Integer.parseInt(sParam);
					} else if (String.class.isAssignableFrom(methodParameter.getType())) {
					    methodParamObjs[i] = sParam;						
					}
				}
				else if (methodParameter.getType()!=HttpServletRequest.class && methodParameter.getType()!=HttpServletResponse.class) {
					Object methodParamObj = methodParameter.getType().newInstance();
					WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
					webBindingInitializer.initBinder(wdb);
					wdb.bind(request);
					methodParamObjs[i] = methodParamObj;
				}
				
				i++;
			}
			
			Method invocableMethod = handlerMethod.getMethod();
			Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
			Class<?> returnType = invocableMethod.getReturnType();
					
			ModelAndView mav = null;
			if (invocableMethod.isAnnotationPresent(ResponseBody.class)){ //ResponseBody
		        this.messageConverter.write(returnObj, response);
			}
			else if (returnType == void.class) {
				
			}
			else {
				if (returnObj instanceof ModelAndView) {
					mav = (ModelAndView)returnObj;
				}
				else if(returnObj instanceof String) {
					String sTarget = (String)returnObj;
					mav = new ModelAndView();
					mav.setViewName(sTarget);
				}
			}
			
			return mav;
	}


	public WebBindingInitializer getWebBindingInitializer() {
		return webBindingInitializer;
	}

	public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
		this.webBindingInitializer = webBindingInitializer;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}


}
