package fr.ac_versailles.crdp.apiscol;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

public class RequestHandler {

	public static String extractAcceptHeader(HttpServletRequest request) {
		return request.getHeader("accept").trim().toLowerCase();

	}

	public static String convertFormatQueryParam(String format) {
		if (format.trim().toLowerCase().contains("json"))
			return MediaType.APPLICATION_JSON;
		else if (format.trim().toLowerCase().contains("javascript"))
			return CustomMediaType.JSONP.toString();
		else if (format.trim().toLowerCase().contains("html"))
			return MediaType.APPLICATION_XHTML_XML;
		return MediaType.APPLICATION_XML;
	}

}
