package com.elminster.common.util;

import static com.elminster.common.constants.Constants.StringConstants.EMPTY_STRING;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.elminster.common.constants.Constants.EncodingConstants;
import com.elminster.common.util.Messages.Message;

/**
 * The XML utility.
 *
 * @author jgu
 * @version 1.0
 */
abstract public class XMLUtil {

  /** the default encoding. */
  public static final String DEFAULT_ENCODING = EncodingConstants.UTF8;
  /** the XML format. */
  public static final String XML_FORMAT = "XML"; //$NON-NLS-1$
  /** the XML extension. */
  public static final String XML_EXTENSION = ".XML"; //$NON-NLS-1$
  /** the XPath. */
  private static final XPath xpath;
  /** the text node. */
  private static final String TEXT_NODE = "#text";
  /** the attribute node. */
  private static final String ATTRIBUTE_NODE = "#attribute";

  /**
   * static initialization.
   */
  static {
    xpath = XPathFactory.newInstance().newXPath();
  }

  /**
   * Create document.
   *
   * @return the document
   * @throws Exception
   *     on error
   */
  public static Document createDocument() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    Document document;
    try {
      builder = factory.newDocumentBuilder();
      document = builder.newDocument();
    } catch (Exception e) {
      throw e;
    }
    return document;
  }

  /**
   * Create document with namespace, qualified name and doc type.
   *
   * @param namespaceURI
   *     the namespace URI
   * @param qualifiedName
   *     the qualified name
   * @param doctype
   *     the document type
   * @return the document
   * @throws Exception
   *     on error
   */
  public static Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype)
      throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    Document document;
    try {
      builder = factory.newDocumentBuilder();
      document = builder.getDOMImplementation().createDocument(namespaceURI, qualifiedName, doctype);
    } catch (Exception e) {
      throw e;
    }
    return document;
  }

  /**
   * Convert a input source to document.
   *
   * @param is
   *     the input source
   * @return the document
   * @throws Exception
   *     on error
   */
  public static Document convertToDocument(InputSource is) throws Exception {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    docFactory.setValidating(false);
    // docFactory.setFeature("http://xml.org/sax/features/namespaces",
    // false);
    // docFactory.setFeature("http://xml.org/sax/features/validation",
    // false);
    // docFactory.setFeature("http://apache.org/xml/features/validation/dynamic",
    // false);
    // docFactory.setFeature(
    // "http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
    // false);
    // docFactory
    // .setFeature(
    // "http://apache.org/xml/features/nonvalidating/load-external-dtd",
    // false);
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    docBuilder.setEntityResolver(new EntityResolver() {

      @Override
      public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        return new InputSource(new StringReader(EMPTY_STRING));
      }

    });
    Document document = docBuilder.parse(is);
    return document;
  }

  /**
   * Convert a input stream to document.
   *
   * @param inputStream
   *     the input stream
   * @return the document
   * @throws Exception
   *     on error
   */
  public static Document convert2Document(InputStream inputStream) throws Exception {
    InputSource is = new InputSource(inputStream);
    return convertToDocument(is);
  }

  /**
   * Convert a file to document.
   *
   * @param fileName
   *     file name
   * @return the document
   * @throws Exception
   *     on error
   */
  public static Document convert2Document(String fileName) throws Exception {
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(fileName);
      InputSource is = new InputSource(fis);
      return convertToDocument(is);
    } finally {
      if (null != fis) {
        fis.close();
      }
    }
  }

  /**
   * Get fisrt specified child node by tag name.
   *
   * @param parent
   *     the parent node
   * @param tagName
   *     the tag name
   * @return the child node
   * @throws Exception
   *     on error
   */
  public static Node getFirstChildNode(Node parent, String tagName) throws Exception {
    if (null != parent) {
      NodeList childrenList = parent.getChildNodes();
      int childrenCnt = childrenList.getLength();
      for (int i = 0; i < childrenCnt; i++) {
        Node child = childrenList.item(i);
        if (child.getNodeName().equals(tagName)) {
          return child;
        }
      }
    }
    return null;
  }

  /**
   * Get specified child nodes by tag name.
   *
   * @param parent
   *     the parent node
   * @param tagName
   *     the tag name
   * @param searchDeeper
   *     search deeper?
   * @return the child node list
   * @throws Exception
   *     on error
   */
  public static List<Node> getChildNode(Node parent, String tagName, boolean searchDeeper) throws Exception {
    List<Node> list = new ArrayList<Node>();
    if (null != parent) {
      NodeList childrenList = parent.getChildNodes();
      int childrenCnt = childrenList.getLength();
      for (int i = 0; i < childrenCnt; i++) {
        Node child = childrenList.item(i);
        if (child.getNodeName().equals(tagName)) {
          list.add(child);
        }
        if (searchDeeper) {
          list.addAll(getChildNode(child, tagName, searchDeeper));
        }
      }
    }
    return list;
  }

  /**
   * Get the value of the specified child node. (only first value will be
   * returned, and will return EMPTY_STRING if the tag exist but no value, null
   * if the tag not exist)
   *
   * @param parent
   *     the parent node
   * @param tagName
   *     the tag name
   * @param searchDeeper
   *     search deeper?
   * @return the value of specified child node
   * @throws Exception
   *     on error
   */
  public static String getChildNodeValue(Node parent, String tagName, boolean searchDeeper) throws Exception {
    if (null == parent) {
      return null;
    }
    String value = null;
    NodeList childrenList = parent.getChildNodes();
    int childrenCnt = childrenList.getLength();
    for (int i = 0; i < childrenCnt; i++) {
      Node child = childrenList.item(i);
      if (child.getNodeName().equals(tagName)) {
        Node element = child.getFirstChild();
        if (null != element) {
          value = (null == element.getNodeValue() ? EMPTY_STRING : element.getNodeValue());
        } else {
          return EMPTY_STRING;
        }
      } else {
        if (searchDeeper) {
          value = getChildNodeValue(child, tagName, searchDeeper);
        }
      }
      if (null != value) {
        break;
      }
    }
    return value;
  }

  /**
   * Get the specified attribute value from child node. (only first value will
   * be returned)
   *
   * @param parent
   *     the parent node
   * @param tagName
   *     the tag name
   * @param attributeName
   *     the attribute name
   * @param searchDeeper
   *     search deeper?
   * @return the attribute value
   * @throws Exception
   *     on error
   */
  public static String getChildNodeAttribute(Node parent, String tagName, String attributeName, boolean searchDeeper)
      throws Exception {
    if (null == parent) {
      return null;
    }
    String value = null;
    NodeList childrenList = parent.getChildNodes();
    int childrenCnt = childrenList.getLength();
    for (int i = 0; i < childrenCnt; i++) {
      Node child = childrenList.item(i);
      if (child.getNodeName().equals(tagName)) {
        Node node = child.getAttributes().getNamedItem(attributeName);
        if (null == node) {
          value = EMPTY_STRING;
        } else {
          value = node.getNodeValue();
        }
      } else {
        if (searchDeeper) {
          value = getChildNodeValue(child, tagName, searchDeeper);
        }
      }
      if (null != value) {
        break;
      }
    }
    return value;
  }

  /**
   * Create the text node of specified node.
   *
   * @param document
   *     the document
   * @param nodeName
   *     the node name
   * @param nodeText
   *     the node text
   * @return the created text node
   * @throws Exception
   *     on error
   */
  public static Element createTextNode(Document document, String nodeName, String nodeText) throws Exception {
    if (null == document) {
      throw new IllegalArgumentException();
    }
    Element element = document.createElement(nodeName);
    Text textNode = document.createTextNode(null == nodeText ? EMPTY_STRING : nodeText);
    element.appendChild(textNode);
    return element;
  }

  /**
   * Convert specified node to String
   *
   * @param node
   *     the node
   * @param withHeader
   *     with header or not
   * @return the converted String
   * @throws Exception
   *     on error
   */
  public static String convertToString(Node node, boolean withHeader) throws Exception {
    return convertToString(node, DEFAULT_ENCODING, withHeader);
  }

  /**
   * Convert specified node to String
   *
   * @param node
   *     the node
   * @param encoding
   *     the encoding
   * @param withHeader
   *     with header or not
   * @return the converted String
   * @throws Exception
   *     on error
   */
  public static String convertToString(Node node, String encoding, boolean withHeader) throws Exception {
    String str = null;
    StringWriter out = null;
    if (StringUtil.isEmpty(encoding)) {
      encoding = DEFAULT_ENCODING;
    }
    try {
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer trans = factory.newTransformer();
      trans.setOutputProperty(OutputKeys.ENCODING, encoding);
      trans.setOutputProperty(OutputKeys.INDENT, Boolean.TRUE.toString());
      if (!withHeader) {
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); //$NON-NLS-1$
      }
      out = new StringWriter();
      StreamResult result = new StreamResult(out);
      DOMSource xmlSource = new DOMSource(node);
      trans.transform(xmlSource, result);
      str = out.toString();
      out.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      out.close();
    }
    return str;
  }

  /**
   * Write specified document to a output file.
   *
   * @param document
   *     the document
   * @param outputFilePath
   *     the output file path
   * @throws Exception
   *     on error
   */
  public static void writeFile(Document document, String outputFilePath) throws Exception {
    writeFile(document, outputFilePath, DEFAULT_ENCODING);
  }

  /**
   * Write specified document to a output file.
   *
   * @param document
   *     the document
   * @param outputFilePath
   *     the output file path
   * @param encoding
   *     encoding
   * @throws Exception
   *     on error
   */
  public static void writeFile(Document document, String outputFilePath, String encoding) throws Exception {
    writeFile(document, new File(outputFilePath), encoding, true);
  }

  /**
   * Write specified document to a output file.
   *
   * @param document
   *     the document
   * @param file
   *     the output file
   * @throws Exception
   *     on error
   */
  public static void writeFile(Document document, File file) throws Exception {
    writeFile(document, file, DEFAULT_ENCODING, true);
  }

  /**
   * Write specified document to a output file.
   *
   * @param document
   *     the document
   * @param file
   *     the output file
   * @param encoding
   *     the encoding
   * @param indent
   *     use indent or not?
   * @throws Exception
   *     on error
   */
  public static void writeFile(Document document, File file, String encoding, boolean indent) throws Exception {
    if (StringUtil.isEmpty(encoding)) {
      encoding = DEFAULT_ENCODING;
    }
    FileOutputStream fos = null;

    try {
      fos = new FileOutputStream(file);
      TransformerFactory transFactory = TransformerFactory.newInstance();
      // transFactory.setAttribute("indent-number", 2);
      Transformer trans = transFactory.newTransformer();
      trans.setOutputProperty(OutputKeys.ENCODING, encoding);
      trans.setOutputProperty(OutputKeys.METHOD, XML_FORMAT);
      if (indent) {
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
      }
      DOMSource input = new DOMSource(document);
      StreamResult output = new StreamResult(fos);
      trans.transform(input, output);
    } finally {
      if (null != fos) {
        fos.close();
      }
    }
  }

  /**
   * Convert the document's encoding.
   *
   * @param document
   *     the document
   * @param convertEncoding
   *     the encoding to convert
   * @return the converted encoding document
   * @throws Exception
   *     on error
   */
  public static Document convertEncoding(Document document, String convertEncoding) throws Exception {
    String xmlEncoding = document.getXmlEncoding();
    if (StringUtil.isEmpty(xmlEncoding)) {
      throw new IllegalStateException(Messages.getString(Message.XML_ENCODING_IS_NULL));
    }
    if (!xmlEncoding.equalsIgnoreCase(convertEncoding)) {
      long now = System.currentTimeMillis();
      File tempFile = File.createTempFile(String.valueOf(now), XML_EXTENSION);
      writeFile(document, tempFile, convertEncoding, true);
      InputSource is = new InputSource(new FileInputStream(tempFile));
      document = convertToDocument(is);
    }
    return document;
  }

  /**
   * Get the attribute value from specified node.
   *
   * @param node
   *     the node
   * @param attributeName
   *     the attribute name
   * @return the attribut value
   */
  public static String getNodeAttribute(Node node, String attributeName) {
    if (null == node) {
      return null;
    }
    NamedNodeMap attributes = node.getAttributes();
    if (null == attributes) {
      return null;
    }
    Node n = attributes.getNamedItem(attributeName);
    if (null == n) {
      return null;
    }
    return n.getNodeValue();
  }

  /**
   * Get the value of specified node.
   *
   * @param node
   *     the node
   * @return the value of the node
   */
  public static String getNodeValue(Node node) {
    if (null == node) {
      return null;
    }
    if (null == node.getFirstChild()) {
      return null;
    }
    return node.getFirstChild().getNodeValue();
  }

  /**
   * Set node's attribute.
   *
   * @param node
   *     the node
   * @param attributeName
   *     the attribute name
   * @param value
   *     the value
   */
  public static void setNodeAttributeValue(Node node, String attributeName, String value) {
    if (null == node) {
      // Do Nothing
      return;
    }
    NamedNodeMap attributes = node.getAttributes();
    if (null == attributes) {
      // Do Nothing
      return;
    }
    Node n = attributes.getNamedItem(attributeName);
    if (null == n) {
      // Do Nothing
      return;
    }
    n.setNodeValue(value);
  }

  /**
   * Set or add node attribute.
   *
   * @param node
   *     the node
   * @param attributeName
   *     the attribute name
   * @param attributeValue
   *     the attribute value
   * @param doc
   *     the document
   */
  public static void setAddNodeAttribute(Node node, String attributeName, String attributeValue, Document doc) {
    if (null == node) {
      return;
    }
    NamedNodeMap attributes = node.getAttributes();
    Element element = (Element) node;
    if (null == attributes) {
      element.setAttributeNode(getAttribute(attributeName, attributeValue, doc));
    } else {
      Node n = attributes.getNamedItem(attributeName);
      if (null == n) {
        element.setAttributeNode(getAttribute(attributeName, attributeValue, doc));
      } else {
        n.setNodeValue(attributeValue);
      }
    }
  }

  /**
   * Get the attribute.
   *
   * @param attributeName
   *     the attribute name
   * @param attributeValue
   *     the attribute value
   * @param doc
   *     the document
   * @return the attribute
   */
  private static Attr getAttribute(String attributeName, String attributeValue, Document doc) {
    Attr attr = doc.createAttribute(attributeName);
    attr.setNodeValue(attributeValue);
    return attr;
  }

  /**
   * Set or add the node text value.
   *
   * @param node
   *     the node
   * @param value
   *     the value
   * @param document
   *     the document
   */
  public static void setNodeTextValue(Node node, String value, Document document) {
    if (null == node) {
      return;
    }
    Node textNode = node.getFirstChild();
    if (null != textNode) {
      textNode.setNodeValue(value);
    } else {
      textNode = document.createTextNode(value);
      node.appendChild(textNode);
    }
  }

  /**
   * Get the encoding of specified XML file.
   *
   * @param fileName
   *     the file name
   * @return the encoding of the file
   * @throws Exception
   *     on error
   */
  public static String getEncoding(String fileName) throws Exception {
    FileInputStream fis = new FileInputStream(fileName);
    InputSource is = new InputSource(fis);
    Document doucument = convertToDocument(is);
    return doucument.getXmlEncoding();
  }

  /**
   * Create a node with tag name.
   *
   * @param document
   *     the document
   * @param parent
   *     the parent node
   * @param tagName
   *     the tag name
   * @return the created node
   */
  public static Element createNode(Document document, Node parent, String tagName) {
    Element node = document.createElement(tagName);
    parent.appendChild(node);
    return node;
  }

  /**
   * Replace the old node with the new node.
   *
   * @param parent
   *     the node parent
   * @param oldNode
   *     the old node
   * @param newNode
   *     the new node
   */
  public static void replaceNode(Node parent, Node oldNode, Node newNode) {
    parent.replaceChild(newNode, oldNode);
  }

  /**
   * Using XPath to evaluate a Node from expression.
   *
   * @param expression
   *     the expression
   * @param node
   *     parent node
   * @return the node
   * @throws Exception
   *     exception
   */
  public static Node xpathEvaluateNode(String expression, Node node) throws Exception {
    return (Node) xpath.evaluate(expression, node, XPathConstants.NODE);
  }

  /**
   * Using XPath to evaluate a NodeList from expression.
   *
   * @param expression
   *     the expression
   * @param node
   *     parent node
   * @return the node list
   * @throws Exception
   *     exception
   */
  public static NodeList xpathEvaluateNodeList(String expression, Node node) throws Exception {
    return (NodeList) xpath.evaluate(expression, node, XPathConstants.NODESET);
  }

  /**
   * Using XPath to evaluate a String from expression.
   *
   * @param expression
   *     the expression
   * @param node
   *     parent node
   * @return the String
   * @throws Exception
   *     exception
   */
  public static String xpathEvaluateString(String expression, Node node) throws Exception {
    return (String) xpath.evaluate(expression, node, XPathConstants.STRING);
  }

  /**
   * Convert specified Node to Map.
   *
   * @param node
   *     the node
   * @return the map
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Object> node2Map(Node node) {
    Map<String, Object> map = new HashMap<String, Object>();
    if (node == null) {
      return map;
    }
    NodeList children = node.getChildNodes();
    int len = children.getLength();

    for (int i = 0; i < len; i++) {
      Node child = children.item(i);
      String childName = child.getNodeName();
      if (TEXT_NODE.equals(childName)) {
        continue;
      }
      List<Object> cl = (List<Object>) map.get(childName);
      if (null == cl) {
        cl = new ArrayList<Object>();
      }
      cl.add(node2Map(child));
      map.put(child.getNodeName(), cl);
    }
    String nodeValue = XMLUtil.getNodeValue(node);
    if (null != nodeValue && StringUtil.isNotEmpty(nodeValue.trim())) {
      map.put(TEXT_NODE, nodeValue);
    }
    NamedNodeMap attributeMap = node.getAttributes();
    if (null != attributeMap) {
      int attLen = attributeMap.getLength();
      if (attLen > 0) {
        Map<String, Object> attMap = new HashMap<String, Object>();
        for (int i = 0; i < attLen; i++) {
          Node att = attributeMap.item(i);
          attMap.put(att.getNodeName(), att.getNodeValue());
        }
        map.put(ATTRIBUTE_NODE, attMap);
      }
    }
    return map;
  }

  /**
   * Convert specified DOM to Map.
   *
   * @param doc
   *     the DOM
   * @return the map
   */
  public static Map<String, Object> dom2Map(Document doc) {
    Map<String, Object> map = new HashMap<String, Object>();
    if (doc == null) {
      return map;
    }
    Element root = doc.getDocumentElement();
    map.put(root.getNodeName(), node2Map(root));
    return map;
  }
}
