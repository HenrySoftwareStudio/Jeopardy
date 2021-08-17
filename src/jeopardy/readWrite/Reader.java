package jeopardy.readWrite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jeopardy.game.core.CategoryToken;
import jeopardy.game.core.GameValues;
import jeopardy.game.core.QuestionToken;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

public class Reader
{
	public static void Read(final File At) throws SAXException, IOException, ParserConfigurationException
	{
		if(At==null)
		{
			return;
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(At);
        doc.getDocumentElement().normalize();
        NodeList MainGameNodeList = doc.getElementsByTagName("MainGame");
        for (int temp = 0; temp < MainGameNodeList.getLength(); temp++) 
        {
        	Node node = MainGameNodeList.item(temp);
        	if (node.getNodeType() == Node.ELEMENT_NODE) 
	        {
        		Element element = (Element) node;
        		NodeList Questions=element.getElementsByTagName("Question");
        		GameValues.Tokens=new QuestionToken[Questions.getLength()];
        		GameValues.Categories=new CategoryToken[] {null, null, null, null, null, null};
        		for (int i = 0; i < Questions.getLength(); i++)
				{
        			Element Question=(Element) element.getElementsByTagName("Question").item(i);
					String QuestionText=Questions.item(i).getTextContent();        		
					String Category=((Element) Question).getAttribute("Category");
					CategoryToken categoryToken=new CategoryToken(Category);
					System.out.println(categoryToken);
					if(i<6)
					{
						GameValues.Categories[i]=categoryToken;
					}
					
        			String QuestionValue=((Element) Question).getAttribute("Value");
					String QuestionAnswer=((Element) Question).getAttribute("Answer");
					GameValues.Tokens[i]=new QuestionToken(QuestionText, QuestionAnswer, Integer.parseInt(QuestionValue), Category);
				}      		
	        }
        }
        NodeList FinalNodeList = doc.getElementsByTagName("Final");
        for (int temp = 0; temp < FinalNodeList.getLength(); temp++) 
        {
        	Element node = (Element) FinalNodeList.item(temp);
        	if (node.getNodeType() == Node.ELEMENT_NODE) 
	        {
    			Element Question=(Element) node.getElementsByTagName("Question").item(0);
				String QuestionText=Question.getTextContent();        		
				String Category=((Element) Question).getAttribute("Category");
				String QuestionAnswer=((Element) Question).getAttribute("Answer");
				GameValues.FinalJeopardy=new QuestionToken(QuestionText, QuestionAnswer, Category);
	        }
        }
	}
}
