package com.atom.training.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Prop {
	private static final Properties prop = new Properties();

	public static String getPropValue(String propName) {
		String configFile = "application.properties";
		InputStream inputStream = Prop.class.getClassLoader().getResourceAsStream(configFile);
		if (inputStream != null) {
			try {
				prop.load(inputStream);
				return prop.getProperty(propName);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
