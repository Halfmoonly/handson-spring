package com.minis.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
	private Map<String, MappingValue> mappingValues;
	private Map<String, Class<?>> mappingClz = new HashMap<>();
	private Map<String, Object> mappingObjs = new HashMap<>();

	private String sContextConfigLocation;

    public DispatcherServlet() {
        super();
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		sContextConfigLocation = config.getInitParameter("contextConfigLocation");
		URL xmlPath = null;
		try {
			xmlPath = this.getServletContext().getResource(sContextConfigLocation);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Resource rs = new ClassPathXmlResource(xmlPath);
		XmlConfigReader reader = new XmlConfigReader();
		mappingValues = reader.loadConfig(rs);
		Refresh();
	}

	//对所有的mappingValues中注册的类进行实例化，默认构造函数
	protected void Refresh() {
		for (Map.Entry<String,MappingValue> entry : mappingValues.entrySet()) {
			String id = entry.getKey();
			String className = entry.getValue().getClz();
			Object obj = null;
			Class<?> clz = null;
			try {
				clz = Class.forName(className);
				obj = clz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mappingClz.put(id, clz);
			mappingObjs.put(id, obj);
		}
	}







	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sPath = request.getServletPath(); //获取请求的path
		if (this.mappingValues.get(sPath) == null) {
			return;
		}

		Class<?> clz = this.mappingClz.get(sPath); //获取bean类定义
		Object obj = this.mappingObjs.get(sPath);  //获取bean实例
		String methodName = this.mappingValues.get(sPath).getMethod(); //获取调用方法名
		Object objResult = null;
		try {
			Method method = clz.getMethod(methodName);
			objResult = method.invoke(obj); //方法调用
		} catch (Exception e) {
		}
		//将方法返回值写入response
		response.getWriter().append(objResult.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
