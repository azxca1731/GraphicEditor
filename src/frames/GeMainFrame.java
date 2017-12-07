package frames;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.border.Border;

import constants.GEConstants;
import menus.GEMenuBar;

public class GeMainFrame extends JFrame {
	//Singletern 패턴
	private static GeMainFrame uniqueMainFrame = 
			new GeMainFrame(GEConstants.TITLE_MAINFFRAME);
	private GeDrawingPanel drawingPanel;
	private GEMenuBar menuBar;
	private GEToolbar toolBar;
	
	private GeMainFrame(String title) {
		super(title);
		drawingPanel= new GeDrawingPanel();
		menuBar = new GEMenuBar();
		toolBar = new GEToolbar(GEConstants.TITLE_TOOLBAR);
		
		this.setJMenuBar(menuBar);
		this.add(drawingPanel);
		this.add(BorderLayout.NORTH,toolBar);
	}

	public static GeMainFrame getInstance() {
		return uniqueMainFrame;
	}
	
	public void init() {
		toolBar.init(drawingPanel);
		menuBar.init(drawingPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(GEConstants.WIDTH_MAINFRAME,GEConstants.HEIGHT_MAINFRAME);
		this.setVisible(true);
	}

}
