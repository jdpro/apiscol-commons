package fr.ac_versailles.crdp.apiscol;

import javax.ws.rs.core.MediaType;

public class CustomMediaType {
	public static final MediaType SCORM_XML = new MediaType("application", "scorm+xml");
	public static final MediaType JSONP = new MediaType("application", "javascript");
}
