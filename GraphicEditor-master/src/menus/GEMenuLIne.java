package menus;

import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GeConstants;
import constants.GeConstants.EColorMenuItems;
import constants.GeConstants.ELineMenuItems;
import frames.GeDrawingPanel;

public class GEMenuLIne extends JMenu{
	private GeDrawingPanel drawingPanel;
	private LineMenuHandler lineMenuHandler;
	public GEMenuLIne(String s) {
		super(s);
		lineMenuHandler = new LineMenuHandler();
		for(ELineMenuItems btn : ELineMenuItems.values()) {
			JMenuItem menuItems = new JMenuItem(btn.toString());
			menuItems.setActionCommand(btn.toString());
			menuItems.addActionListener(lineMenuHandler);
			this.add(menuItems);
		}
	}
	
	public void init(GeDrawingPanel drawingPanel) {
		this.drawingPanel=drawingPanel;
	}
	
	public void setSolidStroke() {
		drawingPanel.setStroke(new BasicStroke(GeConstants.DEFAULT_LINE_WIDTH));
	}
	
	public void setDottedStroke() {

		float dashes[] = {GeConstants.DEFAULT_DASH_OFFSET};
		drawingPanel.setStroke(new BasicStroke(
				GeConstants.DEFAULT_LINE_WIDTH,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 10, dashes, 0));
	}
	public void setRoundStroke() {
		drawingPanel.setStroke(new BasicStroke(
				GeConstants.DEFAULT_LINE_WIDTH,
				BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
	}
	public void setEdgeStroke() {
		drawingPanel.setStroke(new BasicStroke(
				GeConstants.DEFAULT_LINE_WIDTH,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER));
	}
	public void setBoldStroke() {
		drawingPanel.setStroke(new BasicStroke(GeConstants.DEFAULT_BOLDLINE_WIDTH));
	}
	public void setFineStroke() {
		drawingPanel.setStroke(new BasicStroke(GeConstants.DEFAULT_FINELINE_WIDTH));
	}
private class LineMenuHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(ELineMenuItems.valueOf(e.getActionCommand())) {
			case Solid:
				setSolidStroke();
				break;
			case Dotted:
				setDottedStroke();
				break;
			case Round:
				setRoundStroke();
				break;
			case Edge:
				setEdgeStroke();
				break;
			case Bold:
				setBoldStroke();
				break;
			case Fine:
				setFineStroke();
			break;
				
			}
		}
	}
}
