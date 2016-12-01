package com.evercons.database.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanUtils {

	private ApplicationContext applicationContext;
	
	public SpringBeanUtils() {
		applicationContext = new ClassPathXmlApplicationContext("action-beans.xml");
	}
	
	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}
