package com.oracle.casb;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created By : abhijsri
 * Date  : 29/05/18
 **/
public class ReadXMLFile {

    public static void main(String argv[]) {
        ReadXMLFile rxf = new ReadXMLFile();
        //rxf.readXmlFile();
        rxf.readXmlFile(new File("/Users/abhijsri/tools/tomcat9/webapps/SecloreWebApp/config/config.xml"));

    }

    private void readXmlFile() {
        String directoryName = "/Users/abhijsri/tools/tomcat9/webapps/SecloreWebApp/data";
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList){
            System.out.println(file.getPath() + '/' + file.getName());
            if (readXmlFile(file)) {
                System.out.println(file.getName() +" is valid  file");
            } else {
                System.out.println(file.getName() +" is not a valid zip file");
            }
        }

    }

    private  boolean readXmlFile(File fXmlFile) {
        boolean isValid = true;
        try {

            //File fXmlFile = new File("/Users/mkyong/staff.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        } catch (Exception e) {
            isValid = false;
            e.printStackTrace();
        }
        return isValid;
    }
}
