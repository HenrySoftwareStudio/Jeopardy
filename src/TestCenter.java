import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import jeopardy.readWrite.Reader;

public class TestCenter
{

	public static void main(String[] args)
	{
		System.out.println("Currently testing Reader.Read()");
		try
		{
			Reader.Read(new File("C:\\Users\\also me\\source\\eclipse-workspace\\jeopardy\\src\\jeopardy\\readWrite\\macros\\Macro.xml"));
		}
		catch (SAXException | IOException | ParserConfigurationException e)
		{
			e.printStackTrace();
		}
	}

}
