package fr.ac_versailles.crdp.apiscol.utils;

import org.w3c.dom.Document;

public class HTMLUtils {
	private static final String HTML_ROOT = "<!DOCTYPE html><!--[if lt IE 7]>      <html class=\"no-js lt-ie9 lt-ie8 lt-ie7\"> <![endif]--><!--[if IE 7]>         <html class=\"no-js lt-ie9 lt-ie8\"> <![endif]--><!--[if IE 8]>         <html class=\"no-js lt-ie9\"> <![endif]--><!--[if gt IE 8]><!--> <html class=\"no-js\"> <!--<![endif]-->[CONTENT]</html>";

	public static String WrapInHTML5Headers(Document pageContent) {
		return HTML_ROOT.replace("[CONTENT]", XMLUtils.XMLToString(pageContent)
				.replaceFirst("^<html[^>]+>", "").replaceFirst("</html>$", ""));

	}
}
