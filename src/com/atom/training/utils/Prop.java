package com.atom.training.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class Prop {
	private static final Properties prop = new Properties();

	public static String getPropValue(String propName) {
		String configFile = "application.properties";
		InputStream inputStream = Prop.class.getClassLoader().getResourceAsStream(configFile);
		if (inputStream != null) {
			try {
				prop.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
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
