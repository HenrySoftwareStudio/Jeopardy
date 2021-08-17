package jeopardy.toolkit;

public class ChimeManager
{
	@SuppressWarnings("unused")
	private static final Exception[] CHIMEDEXCEPTIONS= new Exception[] {};
	
	public static boolean needChiming(Exception e)
	{
		return !(e instanceof RuntimeException);
	}
}
