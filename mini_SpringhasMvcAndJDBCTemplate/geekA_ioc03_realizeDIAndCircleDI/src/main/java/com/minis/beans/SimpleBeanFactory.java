package com.minis.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory,BeanDefinitionRegistry{
    private Map<String,BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames=new ArrayList<>();

	//earlySingletonObjects相当于二级缓存，用于解决循环依赖
	private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

    public SimpleBeanFactory() {
    }

	/*可以看出，在 Spring 体系中，创建依赖对象的时候，Bean 是结合在一起同时创建完毕的。
	为了减少它内部的复杂性，Spring 对外提供了一个很重要的包装方法：refresh()
	refresh()对所有的 Bean 调用了一次 getBean()，利用 getBean() 方法中的 createBean() 创建 Bean 实例，就可以只用一个方法把容器中所有的 Bean 的实例创建出来了。
	*/
    public void refresh() {
    	for (String beanName : beanDefinitionNames) {
    		try {
				getBean(beanName);
			} catch (BeansException e) {
				e.printStackTrace();
			}
    	}
    }

    public Object getBean(String beanName) throws BeansException{
		//先尝试直接从容器（singletonObjects）中获取bean实例
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
			//如果没有实例，则尝试从毛胚实例中获取
        	singleton = this.earlySingletonObjects.get(beanName);
        	if (singleton == null) {
        		System.out.println("get bean null -------------- " + beanName);
				//如果连毛胚都没有，则创建bean实例并注册到毛坯缓存earlySingletonObjects
        		BeanDefinition bd = beanDefinitionMap.get(beanName);
            	singleton=createBean(bd);
				//注册到毛坯缓存earlySingletonObjects之后，再次注册到成品缓存singletonObjects中
				this.registerBean(beanName, singleton);

				// 预留beanpostprocessor位置
				// step 1: postProcessBeforeInitialization
				// invokeInitMethod{
				// step 2: afterPropertiesSet，需要实现InitializingBean接口
				// step 3: init-method，需要配置init-method标签
				//}
				// step 4: postProcessAfterInitialization
        	}
				
        }
        if (singleton == null) {
        	throw new BeansException("bean is null.");
        }
        return singleton;
    }
	@Override
	public boolean containsBean(String name) {
		return containsSingleton(name);
	}

	public void registerBean(String beanName, Object obj) {
		this.registerSingleton(beanName, obj);
		
		//beanpostprocessor
	}

	@Override
	public void registerBeanDefinition(String name, BeanDefinition bd) {
    	this.beanDefinitionMap.put(name,bd);
    	this.beanDefinitionNames.add(name);
    	if (!bd.isLazyInit()) {
        	try {
				getBean(name);
			} catch (BeansException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}

	@Override
	public void removeBeanDefinition(String name) {
		this.beanDefinitionMap.remove(name);
		this.beanDefinitionNames.remove(name);
		this.removeSingleton(name);
		
	}

	@Override
	public BeanDefinition getBeanDefinition(String name) {
		return this.beanDefinitionMap.get(name);
	}

	@Override
	public boolean containsBeanDefinition(String name) {
		return this.beanDefinitionMap.containsKey(name);
	}

	@Override
	public boolean isSingleton(String name) {
		return this.beanDefinitionMap.get(name).isSingleton();
	}

	@Override
	public boolean isPrototype(String name) {
		return this.beanDefinitionMap.get(name).isPrototype();
	}

	@Override
	public Class<?> getType(String name) {
		return this.beanDefinitionMap.get(name).getClass();
	}
	
	private Object createBean(BeanDefinition bd) {
		Class<?> clz = null;
		//创建毛胚bean实例
		Object obj = doCreateBean(bd);
		//存放到毛胚实例到毛坯缓存earlySingletonObjects中
		this.earlySingletonObjects.put(bd.getId(), obj);
		
		try {
			clz = Class.forName(bd.getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//毛坯对象通过doCreateBean创建好之后，再调用handleProperties来补齐属性PropertieValues
		handleProperties(bd, clz, obj);
		
		return obj;
		
		
	}

	//doCreateBean创建毛胚实例，仅仅调用构造方法没有进行属性处理,因此上面拆分出了handleProperties方法
	private Object doCreateBean(BeanDefinition bd) {
		Class<?> clz = null;
		Object obj = null;
		Constructor<?> con = null;

		try {
    		clz = Class.forName(bd.getClassName());
    		
    		//handle constructor
    		ArgumentValues argumentValues = bd.getConstructorArgumentValues();
    		if (!argumentValues.isEmpty()) {
        		Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
        		Object[] paramValues =   new Object[argumentValues.getArgumentCount()];  
    			for (int i=0; i<argumentValues.getArgumentCount(); i++) {
    				ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
    				if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
    					paramTypes[i] = String.class;
        				paramValues[i] = argumentValue.getValue();
    				}
    				else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
    					paramTypes[i] = Integer.class;
        				paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
    				}
    				else if ("int".equals(argumentValue.getType())) {
    					paramTypes[i] = int.class;
        				paramValues[i] = Integer.valueOf((String) argumentValue.getValue()).intValue();
    				}
    				else {
    					paramTypes[i] = String.class;
        				paramValues[i] = argumentValue.getValue();    					
    				}
    			}
				try {
					con = clz.getConstructor(paramTypes);
					obj = con.newInstance(paramValues);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}  
    		}
    		else {
    			obj = clz.newInstance();
    		}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(bd.getId() + " bean created. " + bd.getClassName() + " : " + obj.toString());
		
		return obj;

	}
    
	private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
		//handle properties
		System.out.println("handle properties for bean : " + bd.getId());
		PropertyValues propertyValues = bd.getPropertyValues();
		if (!propertyValues.isEmpty()) {
			for (int i=0; i<propertyValues.size(); i++) {
				PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
				String pName = propertyValue.getName();
				String pType = propertyValue.getType();
    			Object pValue = propertyValue.getValue();
    			boolean isRef = propertyValue.getIsRef();
    			Class<?>[] paramTypes = new Class<?>[1];    			
				Object[] paramValues =   new Object[1];  
    			if (!isRef) {
					if ("String".equals(pType) || "java.lang.String".equals(pType)) {
						paramTypes[0] = String.class;
					}
					else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
						paramTypes[0] = Integer.class;
					}
					else if ("int".equals(pType)) {
						paramTypes[0] = int.class;
					}
					else {
						paramTypes[0] = String.class;
					}
					
					paramValues[0] = pValue;
    			}
    			else { //is ref, create the dependent beans
    				try {
						paramTypes[0] = Class.forName(pType);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
    				try {
						paramValues[0] = getBean((String)pValue);
					} catch (BeansException e) {
						e.printStackTrace();
					}
    			}

    			String methodName = "set" + pName.substring(0,1).toUpperCase() + pName.substring(1);
				    			
    			Method method = null;
				try {
					method = clz.getMethod(methodName, paramTypes);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
    			try {
					method.invoke(obj, paramValues);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
    			
    			
			}
		}
		
	}
}