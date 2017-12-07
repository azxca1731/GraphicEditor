package menus;

import javax.swing.JMenuBar;

import constants.GEConstants;
import frames.GeDrawingPanel;

public class GEMenuBar extends JMenuBar {
	private GEMenuFile fileMenu;
	private GEMenuColor colorMenu;
	private GEMenuEdit editMenu;
	private GEMenuLIne lineMenu;
	public GEMenuBar() {
		// TODO Auto-generated constructor stub
		fileMenu = new GEMenuFile(GEConstants.TITLE_FILEMUNU);
		editMenu = new GEMenuEdit(GEConstants.TITLE_EDITMENU);
		colorMenu = new GEMenuColor(GEConstants.TITLE_COLORMENU);
		lineMenu = new GEMenuLIne(GEConstants.TITLE_LINEMENU);
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
