/*
 * BishopWare Software
 * 
 * File: XMLOutputer.java
 * 
 * Copyright: 2009 Kym Hines and BishopWare
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bishopware.util.xml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class KXMLWriter {

    public static String getStringFromElement(Element elt)
            throws UnsupportedEncodingException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        KXMLWriter outputer = new KXMLWriter(os, true);
        outputer.printElement(elt, 0);
        outputer.close();
        return os.toString();
    }

    private final boolean m_displayEmptyAttributes;

    private final PrintWriter m_printWriter;

    public KXMLWriter(OutputStream outputStream, boolean displayEmptyAttributes)
            throws UnsupportedEncodingException {
        m_printWriter = new PrintWriter(new OutputStreamWriter(outputStream,
                "UTF-8"));
        m_displayEmptyAttributes = displayEmptyAttributes;
    }

    private String getTabs(int tabLevel) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < tabLevel; ++i) {
            buffer.append(" ");
        }
        return buffer.toString();
    }

    private void printCData(CDATASection text, int tabLevel) {
        String tabs = getTabs(tabLevel);
        m_printWriter.println(tabs + "<![CDATA[\n" + text.getData() + "\n"
                + tabs + "]]>");
    }

    public void close() {
        m_printWriter.close();
    }

    public void complete() {
        m_printWriter.flush();
    }

    public void output(Document document) {
        m_printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        Element root = document.getDocumentElement();
        printElement(root, 0);
    }

    public void printElement(Element element, int tabLevel) {
        Map<String, String> attributes = null;
        if (m_displayEmptyAttributes) {
            attributes = XMLUtilities.buildSortedMapFromAttributes(element
                    .getAttributes());
        }
        else {
            attributes = XMLUtilities
                    .buildSortedNonEmptyMapFromAttributes(element
                            .getAttributes());
        }
        String tabs = getTabs(tabLevel);
        String close = element.hasChildNodes() ? ">" : "/>";
        m_printWriter.print(tabs + "<" + element.getNodeName());
        if (attributes.size() == 0) {
            m_printWriter.println(close);
        }
        else {
            m_printWriter.println();
            for (Entry<String, String> entry : attributes.entrySet()) {
                String escapedString = XMLUtilities.escapeString(entry
                        .getValue(), true);
                m_printWriter.println(tabs + "    " + entry.getKey() + "=\""
                        + escapedString + "\"");

            }
            m_printWriter.println(tabs + close);
        }
        if (element.hasChildNodes()) {
            NodeList children = element.getChildNodes();
            int usedChildCount = 0;
            for (int i = 0; i < children.getLength(); ++i) {
                Node child = children.item(i);
                if (child instanceof Element) {
                    usedChildCount++;
                    printElement((Element) child, tabLevel + 2);
                }
                else if (child instanceof CDATASection) {
                    usedChildCount++;
                    printCData((CDATASection) child, tabLevel + 2);
                }
            }
            if (usedChildCount == 0) {
                String escapedString = XMLUtilities.escapeString(element
                        .getTextContent(), false);
                m_printWriter.print(escapedString);
            }
            m_printWriter.println(tabs + "</" + element.getNodeName() + ">");

        }
    }
}
