/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author gabor_hanacsek
 */
public class xmlfeldolg {

    //public URL url;
    //private Object RowData[][];
    public Object xmlfeldolg(URL url, String nodelist, ArrayList<String> lista) throws IOException, ParserConfigurationException, SAXException {

        URLConnection connection = null;
        try {
            connection = url.openConnection();

        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();

        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(connection.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        }
        try {
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        }

        NodeList nList = doc.getElementsByTagName(nodelist);
        Object RowData[][] = new String[nList.getLength()][lista.size()];

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = (Node) nList.item(temp);
            Element eElement = (Element) nNode;
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                for (int i = 0; i < lista.size(); i++) {

                    RowData[temp][i] = new String(eElement
                            .getElementsByTagName(lista.get(i)).item(0)
                            .getTextContent());
                    //System.out.println("d");

                }

            }

        }

        return RowData;
    }

}
