package jeopardy.game.bugHandler;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.*;

import java.awt.Toolkit;

import jeopardy.toolkit.Tools;

public class BugHandler
{
	private Exception e;
	
	public BugHandler(final Exception e, final boolean Chime)
	{
		this.e=e;
		if (Chime)
		{
			Toolkit.getDefaultToolkit().beep();
		}
		JOptionPane.showOptionDialog(null, buildMessage(), "ERROR", OK_OPTION, ERROR_MESSAGE, null, new String[] {"OK"}, null);
	}
	
	private String buildMessage()
	{
		return Tools.exceptionToString(e);
	}
}
