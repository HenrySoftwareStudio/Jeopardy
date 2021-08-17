import jeopardy.game.bugHandler.BugHandler;
import jeopardy.game.core.Game;
import jeopardy.toolkit.ChimeManager;

public class Entry
{

	public static void main(String[] args)
	{
		try
		{
			Game.initLobby();
		}
		catch(Exception e)
		{
			new BugHandler(e, ChimeManager.needChiming(e));
		}
	}

}
