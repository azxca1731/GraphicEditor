package menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GeConstants;
import constants.GeConstants.EColorMenuItems;
import frames.GeDrawingPanel;

public class GEMenuColor extends JMenu {
	private GeDrawingPanel drawingPanel;
	private ColorMenuHandler colorMenuHandler;
	public GEMenuColor(String s) {
		// TODO Auto-generated constructor stub
		super(s);
		
		colorMenuHandler = new ColorMenuHandler();
		
		for(EColorMenuItems btn : EColorMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.setActionCommand(btn.toString());
			menuItem.addActionListener(colorMenuHandler);
			this.add(menuItem);
		}
	}
	
	public void init(GeDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void setLineColor() {
		Color lineColor = JColorChooser.showDialog(
				null, GeConstants.TITLE_LINECOLOR, null);
		drawingPanel.setLineColor(lineColor);
	}
	
	public void setFillColor() {
		Color fillColor = JColorChooser.showDialog(
				null, GeConstants.TITLE_FILLCOLOR, null);
		drawingPanel.setFillColor(fillColor);
	}
	
	public void clearLineColor() {
		drawingPanel.setLineColor(GeConstants.DEFAULT_LINE_COLOR);
	}
	
	public void clearFillColor() {
		drawingPanel.setFillColor(GeConstants.DEFAULT_FILL_COLOR);
	}
	
	private class ColorMenuHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(EColorMenuItems.valueOf(e.getActionCommand())) {
			case SetLineColor:
				setLineColor();
				break;
			case SetFillColor:
				setFillColor();
				break;
			case ClearLineColor:
				clearLineColor();
				break;
			case ClearFillColor:
				clearFillColor();
				break;
				
			}
		}
	}
}
