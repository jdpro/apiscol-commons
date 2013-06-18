package fr.ac_versailles.crdp.apiscol.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class JSonUtils {

	public static String convertXMLToJson(Document xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(XMLUtils.XMLToString(xml));
		StringWriter writer = new StringWriter();
		json.write(writer);
		return writer.toString();
	}

	public static void convertHtmlFileToJson(String htmlFilePath,
			String jsonpFileName) {
		createLogger();
		StringBuilder sb = new StringBuilder("preview({\"html\":\"");
		try {
			String htmlContent = FileUtils.readFileAsString(htmlFilePath);

			sb.append(htmlContent.replace("\"", "\\\""));
		} catch (IOException e) {
			logger.error(String
					.format("Trying to convert inexistent html file %s to Jsonp. Abort",
							htmlFilePath));
			e.printStackTrace();
			return;
		}
		sb.append("\"});");
		String jsFilePath = new File(htmlFilePath).getParentFile()
				.getAbsolutePath() + "/" + jsonpFileName;
		String jsonpString = sb.toString();
		jsonpString = jsonpString.replaceAll("[\\\n\\\r\\\t]", " ");
		try {

			FileUtils.writeStringToFile(jsFilePath, jsonpString);
		} catch (IOException e) {
			logger.error(String
					.format("Trying to write jsonp string to file file %s to Jsonp. Abort",
							jsFilePath));
			e.printStackTrace();
			return;
		}
	}

	private static Logger logger;

	private static void createLogger() {
		if (logger == null)
			logger = LogUtility
					.createLogger(JSonUtils.class.getCanonicalName());
	}

}
