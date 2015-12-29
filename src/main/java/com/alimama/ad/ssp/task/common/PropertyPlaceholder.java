package com.alimama.ad.ssp.task.common;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyPlaceholder extends PropertyPlaceholderConfigurer {
	private static Properties properties = new Properties();

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		for(String k : props.stringPropertyNames()){
			properties.put(k, props.get(k));
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
