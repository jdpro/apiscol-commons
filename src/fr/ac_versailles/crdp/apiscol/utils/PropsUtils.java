package fr.ac_versailles.crdp.apiscol.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import fr.ac_versailles.crdp.apiscol.ParametersKeys;

public class PropsUtils {
	private static Logger logger;

	public static Properties loadProperties(ServletContext context) {
		createLogger();

		Properties props = new Properties();
		loadPropertiesFile(props, ParametersKeys.commonPropertiesFilePath,
				context);
		loadPropertiesFile(props, ParametersKeys.specificPropertiesFilePath,
				context);

		return props;
	}

	private static void loadPropertiesFile(Properties props,
			ParametersKeys property, ServletContext context) {
		String fileName = context.getInitParameter(property.toString());
		logger.info("The requested properties file is " + fileName);
		logger.info("The directory context of the applet is "
				+ PropsUtils.class.getResource("/"));
		InputStream stream = context.getResourceAsStream(fileName);
		if (stream == null)
			logger.error("The properties file   is null for file "+fileName);

		try {
			props.load(stream);
		} catch (IOException e) {
			logger.warn("Unable to load properties file :" + fileName);
			e.printStackTrace();
		}

	}

	private static void createLogger() {
		if (logger == null)
			logger = LogUtility.createLogger(PropsUtils.class
					.getCanonicalName());
	}
}
