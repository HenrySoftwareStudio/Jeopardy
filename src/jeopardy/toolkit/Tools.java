package jeopardy.toolkit;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Tools
{
	public static String exceptionToString(Exception e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
