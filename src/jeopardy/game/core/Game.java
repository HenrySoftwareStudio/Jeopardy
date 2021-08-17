package jeopardy.game.core;

import static jeopardy.game.core.GameParts.LobbyParts.*;
import static jeopardy.game.core.GameParts.BoardParts.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import jeopardy.readWrite.Reader;

@SuppressWarnings("unused")
public class Game
{
	public static void initLobby()
	{
		//initialize and set up LobbyWindow
		LobbyWindow=new JFrame();
		LobbyWindow.setLayout(LobbyWindowConfig.Layout);
		LobbyWindow.setTitle(LobbyWindowConfig.Title);
		LobbyWindow.setDefaultCloseOperation(LobbyWindowConfig.ClosingOsperation);
		LobbyWindow.setMinimumSize(LobbyWindowConfig.MinSize);
		LobbyWindow.setPreferredSize(LobbyWindowConfig.PreferSize);		
		LobbyWindow.setMaximumSize(LobbyWindowConfig.MaxSize);
		LobbyWindow.setSize(LobbyWindowConfig.PreferSize);
		LobbyWindowConfig.Layout.setVgap((int) Math.round(LobbyWindowConfig.PreferSize.getHeight()*0.02));	
		
		//initialize placeholder
		PlaceHolderLabel=new JLabel();
		
		//initialize Toolbar
		ToolBar=new JMenuBar();
		
		//initialize StartFromLoad
		StartGameFromLoadButton=new JButton(StartGameFromLoadButtonConfig.Name);
		StartGameFromLoadButton.addActionListener(new StartGameFromLoadButtonConfig());
		
		//add elements to LobbyWindow
		LobbyWindow.setJMenuBar(ToolBar);
		LobbyWindow.add(PlaceHolderLabel);
		LobbyWindow.add(StartGameFromLoadButton);
		
		//set LobbyWindow visible
		LobbyWindow.setVisible(true);
	}
	
	public static void initGame() throws LineUnavailableException, UnsupportedAudioFileException, IOException, SAXException, ParserConfigurationException
	{
		// Pre start up stuff
		JFileChooser JFC=new JFileChooser();
		JFC.setDialogType(JFileChooser.OPEN_DIALOG);
		JFC.showOpenDialog(LobbyWindow);
	//	Reader.Read(JFC.getSelectedFile());
		Reader.Read(new File("C:\\Users\\also me\\source\\eclipse-workspace\\jeopardy\\src\\jeopardy\\readWrite\\macros\\Macro.xml"));
		
		//initialize and set up BoardWindow
		BoardWindow=new JFrame();
		BoardWindow.addWindowListener(new BoardWindowConfig());
		BoardWindow.setLayout(BoardWindowConfig.Layout);
		BoardWindow.setTitle(BoardWindowConfig.Title);
		BoardWindow.setDefaultCloseOperation(BoardWindowConfig.ClosingOsperation);
		BoardWindow.setMinimumSize(BoardWindowConfig.MinSize);
		BoardWindow.setPreferredSize(BoardWindowConfig.PreferSize);		
		BoardWindow.setMaximumSize(BoardWindowConfig.MaxSize);
		BoardWindow.setSize(BoardWindowConfig.PreferSize);		

		//initialize Labels
		Grid=new JLabel[36];
		for (int i = 0; i < Grid.length; i++)
		{
			Grid[i]=new JLabel();
			Grid[i].addMouseListener(new GridElementsShared());
			
			if (i < 6)
			{
				Grid[i].setName(String.format(GridElementsShared.NameFormat, i, GridElementsShared.States.USED));
				Grid[i].setText(GameValues.Categories[i].getName());
			}
			else
			{
				Grid[i].setName(String.format(GridElementsShared.NameFormat, i-6, GridElementsShared.States.READY));
				Grid[i].setText(GameValues.Tokens[i-6].getValue()+"");
			}
			Grid[i].setOpaque(GridElementsShared.Opaque);
			Grid[i].setForeground(GridElementsShared.ForegroundColor);
			Grid[i].setBorder(GridElementsShared.Border);
		}
		
		//initialize Answer Label
		AnswerLabel=new JLabel();
		AnswerLabel.setVerticalAlignment(AnswerLabelConfig.TextVAlinement);
		AnswerLabel.setHorizontalAlignment(AnswerLabelConfig.TextHAlinement);
		AnswerLabel.setBorder(new LineBorder(Color.BLACK));
		
		//initialize Check Answer Button
		CheckAnswerButton=new JButton(CheckAnswerButtonConfig.Text);
		CheckAnswerButton.addActionListener(new CheckAnswerButtonConfig());
		
		//initialize Check Answer Panel
		CheckAnswer=new JPanel(CheckAnswerConfig.Layout);
		CheckAnswer.add(CheckAnswerButton, CheckAnswerButtonConfig.Constraints);
		CheckAnswer.add(AnswerLabel, AnswerLabelConfig.Constraints);
		
		//initialize Boards
		Board=new JPanel(BoardConfig.InternalLayout);
		Board.setBackground(Color.BLACK);
		Board.setBackground(BoardConfig.BackgroundColor);
		for (JLabel jLabel : Grid)
		{
			Board.add(jLabel);
		}
		
		//add elements to BoardWindow
		BoardWindow.add(Board,BoardConfig.Constraints);
		BoardWindow.add(CheckAnswer, CheckAnswerConfig.Constraints);
		
		//final preBoardWindow visible Touchup
		startMucis("resources\\Jeopardy-theme-song.wav");
		
		//set BoardWindow visible
		BoardWindow.setVisible(true);
	}
	
	private static void startMucis(final String Location) throws LineUnavailableException, UnsupportedAudioFileException, IOException
	{
		if(!GameValues.DisableForTesting)
		{
			Music=AudioSystem.getClip();
			Music.open(AudioSystem.getAudioInputStream(new File(Location)));
			Music.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
}
