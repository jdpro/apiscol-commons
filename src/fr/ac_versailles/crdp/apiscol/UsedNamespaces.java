package fr.ac_versailles.crdp.apiscol;

public enum UsedNamespaces {
	APISCOL("apiscol", "http://www.crdp.ac-versailles.fr/2012/apiscol"), ATOM(
			"atom", "http://www.w3.org/2005/Atom"), LOM("lom",
			"http://ltsc.ieee.org/xsd/LOM"), OPENSEARCH("opensearch",
			"http://a9.com/-/spec/opensearchrss/1.0/"), SVG("svg",
			"http://www.w3.org/2000/svg"), XHTML("xhtml",
			"http://www.w3.org/1999/xhtml");
	private String shorthand;
	private String uri;

	private UsedNamespaces(String shorthand, String uri) {
		this.uri = uri;
		this.shorthand = shorthand;
	}

	@Override
	public String toString() {
		return uri;
	}

	public String getShortHand() {
		return shorthand;
	}

	public String getUri() {
		return uri;
	}

}
