package jeopardy.toolkit;

public final class StringSystem
{
	/**A special SubString that is exclusive both end
	 * 
	 * @see String#substring(int, int) 
	 * @param Source
	 * @param BeginningMark
	 * @param EndMark
	 * @return
	 */
	public static String SubString(final String Source, final String BeginningMark, final String EndMark)
	{
		return Source.substring(Source.indexOf(BeginningMark)+BeginningMark.length(), Source.indexOf(EndMark));
	}
	
	/**A special SubString that is exclusive both end
	 * 
	 * @param Source
	 * @param BeginningMark
	 * @return
	 */
	public static String SubString(final String Source, final String BeginningMark)
	{
		return Source.substring(Source.indexOf(BeginningMark)+BeginningMark.length());
	}
}
