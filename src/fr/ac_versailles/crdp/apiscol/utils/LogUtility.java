package fr.ac_versailles.crdp.apiscol.utils;

import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogUtility {
	private static Level logLevel = Level.DEBUG;

	/**
	 * Returns true if it appears that log4j have been previously configured.
	 * This code checks to see if there are any appenders defined for log4j
	 * which is the definitive way to tell if log4j is already initialized
	 */
	public static boolean log4JisConfigured() {
		@SuppressWarnings("unchecked")
		Enumeration<Appender> appenders = Logger.getRootLogger()
				.getAllAppenders();
		if (appenders.hasMoreElements()) {
			return true;
		} else {
			@SuppressWarnings("unchecked")
			Enumeration<Appender> loggers = LogManager.getCurrentLoggers();
			while (loggers.hasMoreElements()) {
				Logger c = (Logger) loggers.nextElement();
				if (c.getAllAppenders().hasMoreElements())
					return true;
			}
		}
		return false;
	}

	public static Logger createLogger(String className, Level level) {
		if (!LogUtility.log4JisConfigured())
			BasicConfigurator.configure();
		Logger logger = Logger.getLogger(className);
		logger.setLevel(logLevel);
		return logger;
	}

	public static Logger createLogger(String className) {
		return createLogger(className, logLevel);
	}

	public static void setLoglevel(Level level) {
		logLevel = level;
	}
}
