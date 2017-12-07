package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GeConstants.EEditMenuItems;
import frames.GeDrawingPanel;

public class GEMenuEdit extends JMenu {
	
	private GeDrawingPanel drawingPanel;
	private EditMenuHandler editMenuHandler;
	
	public GEMenuEdit(String s) {
		super(s);
		
		editMenuHandler = new EditMenuHandler();
		
		for(EEditMenuItems btn : EEditMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.setActionCommand(btn.toString());
			menuItem.addActionListener(editMenuHandler);
			this.add(menuItem);
		}
	}
	
	public void init(GeDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void undo() {
		drawingPanel.undo();
	}
	
	public void redo() {
		drawingPanel.redo();
	}
	
	private class EditMenuHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(EEditMenuItems.valueOf(e.getActionCommand()) ) {
			case Undo :
				undo();
				break;
			case Redo :
				redo();
				break;
			}
		}
		
	}
}
