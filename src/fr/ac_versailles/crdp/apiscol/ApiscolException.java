package fr.ac_versailles.crdp.apiscol;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.ac_versailles.crdp.apiscol.utils.LogUtility;

public class ApiscolException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger;

	public ApiscolException(String message) {
		super(message);
		createLogger();
		logger.error(message);
	}

	public ApiscolException(String message, boolean displayError) {
		super(message);
		createLogger();
		logger.error(message);
		if (displayError)
			printStackTrace();
	}

	private void createLogger() {
		if (logger == null)
			logger = LogUtility
					.createLogger(this.getClass().getCanonicalName());

	}

	public Document getXMLMessage() {
		Document response = createXMLDocument();
		Element rootElement = response.createElement("error");
		response.appendChild(rootElement);
		Element messageElement = response.createElement("message");
		messageElement.setTextContent(getMessage());
		rootElement.appendChild(messageElement);
		return response;
	}

	private static Document createXMLDocument() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		docFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Document doc = docBuilder.newDocument();
		return doc;
	}
}
