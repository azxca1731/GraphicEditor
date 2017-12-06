package menus;

import javax.swing.JMenuBar;

import constants.GeConstants;
import frames.GeDrawingPanel;

public class GEMenuBar extends JMenuBar {
	private GEMenuFile fileMenu;
	private GEMenuColor colorMenu;
	private GEMenuEdit editMenu;
	private GEMenuLIne lineMenu;
	public GEMenuBar() {
		// TODO Auto-generated constructor stub
		fileMenu = new GEMenuFile(GeConstants.TITLE_FILEMUNU);
		editMenu = new GEMenuEdit(GeConstants.TITLE_EDITMENU);
		colorMenu = new GEMenuColor(GeConstants.TITLE_COLORMENU);
		lineMenu = new GEMenuLIne(GeConstants.TITLE_LINEMENU);
		this.add(fileMenu);
		this.add(editMenu);
		this.add(colorMenu);
		this.add(lineMenu);
	}
	
	public void init(GeDrawingPanel drawingPanel) {
		colorMenu.init(drawingPanel);
		lineMenu.init(drawingPanel);
		editMenu.init(drawingPanel);
	}
}
