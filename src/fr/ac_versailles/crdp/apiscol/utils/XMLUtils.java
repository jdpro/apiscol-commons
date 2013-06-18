package fr.ac_versailles.crdp.apiscol.utils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import fr.ac_versailles.crdp.apiscol.UsedNamespaces;

public class XMLUtils {

	public static String XMLToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString().replaceAll("\n|\r", "");

	}

	public static void addNameSpaces(Document xmlTree,
			UsedNamespaces defaultNameSpace) {
		for (UsedNamespaces ns : UsedNamespaces.values()) {
			xmlTree.getDocumentElement().setAttributeNS(
					"http://www.w3.org/2000/xmlns/",
					(ns.equals(defaultNameSpace) ? "xmlns" : "xmlns:"
							+ ns.getShortHand()), ns.getUri());
		}

	}

	public static Document createXMLDocument() {
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

	public static Node xsltTransform(String styleSheetPath, Document response) {
		return xsltTransform(styleSheetPath, response,
				new HashMap<String, String>());
	}

	public static Node xsltTransform(String styleSheetPath, Document response,
			Map<String, String> params) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tFactory
					.newTransformer(new javax.xml.transform.stream.StreamSource(
							styleSheetPath));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator<String> iterator = params.keySet().iterator(); iterator
				.hasNext();) {
			String key = iterator.next();
			String value = params.get(key);
			transformer.setParameter(key, value);
		}

		Source xmlSource = new DOMSource(response);
		DOMResult result = new DOMResult();
		try {
			transformer.transform(xmlSource, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.getNode();
	}
}
