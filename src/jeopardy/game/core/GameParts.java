package jeopardy.game.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import jeopardy.toolkit.StringSystem;

import static java.awt.GridBagConstraints.*;

public class GameParts
{
	public static class LobbyParts
	{
		public static JFrame LobbyWindow;
		public static JButton StartGameFromLoadButton;
		public static JMenuBar ToolBar;
		public static JLabel PlaceHolderLabel;
		
		public static class LobbyWindowConfig extends ComponentAdapter
		{
			public static String Title="Jeopardy! Lobby";
			public static int ClosingOsperation=JFrame.EXIT_ON_CLOSE;
			public static GridLayout Layout=new GridLayout(0x3, 0x3, 0x0, 0x0);
			public static Dimension MinSize=new Dimension(0x12C, 0x12c);
			public static Dimension MaxSize=new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
			public static Dimension PreferSize=new Dimension(0x258, 0x258);
			
			@Override
			public void componentResized(ComponentEvent e)
			{
				Layout.setVgap((int) Math.round(LobbyWindow.getHeight()*0.8));		
			}
		}
		public static class StartGameFromLoadButtonConfig implements ActionListener
		{
			public static String Name="Start New Game";
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
					try
					{
						Game.initGame();
					}
					catch (LineUnavailableException | UnsupportedAudioFileException | IOException | SAXException | ParserConfigurationException e1)
					{
						e1.printStackTrace(System.err);
					}
			}
		}
	}
	
	public static class BoardParts
	{
		public static JFrame BoardWindow;
		public static JLabel[] Grid;
		public static JPanel Board;
		public static JPanel CheckAnswer;
		public static JButton CheckAnswerButton;
		public static JLabel AnswerLabel;
		public static Clip Music;
		public static class BoardWindowConfig extends WindowAdapter
		{
			public static String Title="Jeopardy! Game In Session";
			public static int ClosingOsperation=JFrame.HIDE_ON_CLOSE;
			public static GridBagLayout Layout=new GridBagLayout();
			public static Dimension MinSize=new Dimension(0x12C, 0x12C);
			public static Dimension MaxSize=new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
			public static Dimension PreferSize=new Dimension(0x258, 0x258);

			@Override
			public void windowClosing(WindowEvent e)
			{
				if (Music != null)
				{
					Music.stop();
					Music.flush();
				}
			}
		}
		public static class BoardConfig
		{
			public static GridBagConstraints Constraints=new GridBagConstraints(0x1, 0x0, 0x1, 0x1, 0x1, .85, CENTER, BOTH, new Insets(0x0, 0x0, 0x0, 0x0), 0x0, 0x0);
			public static Color BackgroundColor=Color.BLUE;
			public static GridLayout InternalLayout=new GridLayout(0x6, 0x6);
		}
		public static class GridElementsShared extends MouseAdapter
		{
			public static class States
			{
				public static final int READY=0x0;
				public static final int USED=0x1;
			}
			public static boolean Opaque=false;
			public static Color ForegroundColor=Color.WHITE;
			public static Border Border=new LineBorder(Color.ORANGE);
			/**
			 * Index On Look Up, State
			 */
			public static String NameFormat="@%d;$%d";
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Object RawSource=e.getSource();
				JLabel Source=null;
				if(RawSource instanceof JLabel)
				{
					Source=(JLabel) RawSource;
					OnClickPostProcess(Source);
				}
				else
				{
					return;
				}	
			}
			
			private void OnClickPostProcess(final JLabel Source)
			{
				int Pos=Integer.parseInt(StringSystem.SubString(Source.getName(), "@", ";"));
				int State=Integer.parseInt(StringSystem.SubString(Source.getName(), "$"));
				
				JLabel AquairedFromGrid=Grid[Pos];
				
				if(State==States.READY)
				{
					AquairedFromGrid.setName(String.format(NameFormat, Pos, States.USED));
					GameValues.LookingAt=Pos;
					AquairedFromGrid.setText(GameValues.Tokens[Pos-6].getQuestion());
					System.out.println(AquairedFromGrid.getName());
					
					boolean IsAllUsed=true;
					for (int i = 0; i < Grid.length; i++)
					{
						IsAllUsed=CheckUsed(Grid[i]);
					}
					if (IsAllUsed)
					{
						//TODO implement Final Jeopardy
					}
				}
			}
			
			private boolean CheckUsed(final JLabel Source)
			{
				return Integer.parseInt(StringSystem.SubString(Source.getName(), "$"))==States.USED;
			}
		}
		public static class CheckAnswerButtonConfig implements ActionListener
		{
			public static String Text="Check Answer";
			public static GridBagConstraints Constraints=new GridBagConstraints(0x1, 0x0, 0x1, 0x1, 0x1, .5, CENTER, BOTH, new Insets(0x0, 0x0, 0x0, 0x0), 0x0, 0x0);

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Press");
				AnswerLabel.setText(GameValues.Tokens[GameValues.LookingAt].getAnswer());
				Grid[GameValues.LookingAt].setText(" ");
			}
			
		}
		public static class AnswerLabelConfig
		{
			public static int TextVAlinement=JLabel.CENTER;
			public static int TextHAlinement=JLabel.CENTER;
			public static GridBagConstraints Constraints=new GridBagConstraints(0x1, RELATIVE, 0x1, 0x1, 0x1, .5, CENTER, BOTH, new Insets(0x0, 0x0, 0x0, 0x0), 0x0, 0x0);
		}
		public static class CheckAnswerConfig
		{
			public static GridBagLayout Layout=new GridBagLayout();
			public static GridBagConstraints Constraints=new GridBagConstraints(0x1, RELATIVE, 0x1, 0x1, 0x1, .15, CENTER, BOTH, new Insets(0x0, 0x0, 0x0, 0x0), 0x0, 0x0);
		}
	}
}
