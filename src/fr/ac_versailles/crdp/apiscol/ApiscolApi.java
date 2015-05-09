package fr.ac_versailles.crdp.apiscol;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LogLevel;
import org.apache.naming.NamingContext;

import fr.ac_versailles.crdp.apiscol.RequestHandler;
import fr.ac_versailles.crdp.apiscol.transactions.KeyLock;
import fr.ac_versailles.crdp.apiscol.transactions.KeyLockManager;
import fr.ac_versailles.crdp.apiscol.utils.LogUtility;
import fr.ac_versailles.crdp.apiscol.utils.PropsUtils;

public class ApiscolApi {
	private static Properties properties;
	protected static Logger logger;
	protected static String version;
	private static InitialContext c;
	private static NamingContext initialContextContainer;
	protected KeyLockManager keyLockManager;

	public ApiscolApi(@Context ServletContext context) {
		createLogger();
		createKeyLockManager();
		loadProperties(context);
		LogUtility.setLoglevel(Level.toLevel(properties
				.getProperty(ParametersKeys.logLevel.toString())));
	}

	private void createLogger() {
		if (logger == null)
			logger = LogUtility
					.createLogger(this.getClass().getCanonicalName());
	}

	private void createKeyLockManager() {
		keyLockManager = KeyLockManager.getInstance();
	}

	protected void takeAndReleaseGlobalLock() {
		KeyLock keyLock = null;
		try {
			keyLock = keyLockManager.getLock(KeyLockManager.GLOBAL_LOCK_KEY);
			keyLock.lock();
			try {
				logger.info(String
						.format("Passing through mutual exclusion for all the content service"));
			} finally {
				keyLock.unlock();

			}
		} finally {
			if (keyLock != null) {
				keyLock.release();
			}
		}
	}

	protected static void loadProperties(ServletContext context) {
		if (properties == null)
			properties = PropsUtils.loadProperties(context);
		if (properties == null) {
			logger.error("Properties file whas not correctly loaded !!");

		} else {
			version = properties.getProperty(ParametersKeys.apiscolVersion
					.toString());
		}

		if (StringUtils.isEmpty(version)) {
			logger.error("There is no version number in this apiscol web service, version will be 0.0.0 by default");
			version = "0.0.0";
			return;
		}
		Pattern p = Pattern.compile("^\\d+\\.\\d+\\.\\d+$");
		Matcher m = p.matcher(version);
		if (!m.matches())
			logger.error("Version number " + version
					+ " does not have the standard pattern");
	}

	public static String getProperty(ParametersKeys key, ServletContext context) {
		String property = getPropertyFromInitialContext(key);
		if (property != null) {
			logger.debug("The following property was overriden by host initial xml context : "
					+ key.toString());
			return property;
		} else
			logger.debug("The following property was not found in  host initial xml context : "
					+ key.toString());

		loadProperties(context);

		if (!properties.containsKey(key.toString())) {
			logger.error("The configuration files of this web service does not contain any value for parameter : "
					+ key.toString());
		}
		return properties.getProperty(key.toString());
	}

	public static String getPropertyFromInitialContext(final ParametersKeys key) {

		if (key == null)
			return null;

		try {
			final Object object = getHostContext().lookup(key.toString());
			if (object != null)
				return object.toString();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
		return null;

	}

	private static NamingContext getHostContext() throws NamingException {
		if (initialContextContainer == null)
			initialContextContainer = (NamingContext) new InitialContext()
					.lookup("java:comp/env");
		return initialContextContainer;
	}

	protected String guessRequestedFormat(HttpServletRequest request,
			String format) {
		// TODO mettre un format par défaut
		if (format == null)
			return RequestHandler.extractAcceptHeader(request);
		else
			return RequestHandler.convertFormatQueryParam(format);
	}

	protected String realPath(ServletContext context) {
		return context.getRealPath("");
	}

}
