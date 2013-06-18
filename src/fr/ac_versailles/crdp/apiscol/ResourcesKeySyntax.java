package fr.ac_versailles.crdp.apiscol;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourcesKeySyntax {
	private final static String uidPortion = "\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}";
	private final static String url = "(https?://)([^:^/]*)(:\\d*)?/(.+)";
	private final static Pattern apiscolResourceIdSchema = Pattern
			.compile(String.format("^(%s)$", uidPortion));
	private final static Pattern solrResultIdForFileOrUrlSchema = Pattern
			.compile(String.format("^(%s)@(.+)$", uidPortion));
	private final static Pattern urlSchema = Pattern.compile(String.format(
			"^%s$", url));
	private final static Pattern urnSchema = Pattern.compile(String.format(
			"^urn:apiscol:[^:]+:[^:]+:[^:]+:%s$", uidPortion));

	public static List<String> analyseSolrResultId(String resultId) {
		Matcher matcher = solrResultIdForFileOrUrlSchema.matcher(resultId);
		matcher.find();
		List<String> list = new ArrayList<String>();
		if (matcher.groupCount() > 0)
			list.add(0, matcher.group(1));
		if (matcher.groupCount() > 1)
			list.add(1, matcher.group(2));
		return list;
	}

	public static boolean resourceIdIsCorrect(String resourceId) {
		Matcher matcher = apiscolResourceIdSchema.matcher(resourceId);
		return matcher.matches();
	}

	public static boolean isUrn(String urn) {
		Matcher matcher = urnSchema.matcher(urn);
		return matcher.matches();
	}

	public static String extractResourceIdFromUrn(String resourceUrn) {
		int index = resourceUrn.lastIndexOf(":");
		if (index == -1)
			return "";
		CharSequence id = resourceUrn.substring(index + 1);
		if (!apiscolResourceIdSchema.matcher(id).matches())
			return "";
		return id.toString();
	}

	public static String removeSSL(String url) {
		Matcher matcher = urlSchema.matcher(url);
		if (!matcher.matches())
			return url;
		matcher.find();
		if (matcher.groupCount() < 5)
			return url;
		String domain = matcher.group(2);
		String path = matcher.group(4);
		return new StringBuilder().append("http://").append(domain).append('/')
				.append(path).toString().trim().toLowerCase();
	}
}
