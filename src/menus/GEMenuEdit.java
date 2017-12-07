package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GEConstants.EEditMenuItems;
import frames.GeDrawingPanel;
import shapes.GEGroup;

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
	
	public void delete() {
		drawingPanel.ShapeDelete();
	}
	
	public void cut() {
		drawingPanel.ShapeCut();
	}
	
	public void copy() {
		drawingPanel.ShapeCopy();
	}
	
	public void paste() {
		drawingPanel.ShapePaste();
	}
	public void group() {
		drawingPanel.group(new GEGroup());
	}
	
	public void ungroup() {
		drawingPanel.unGroup();
	}
	
	private class EditMenuHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(EEditMenuItems.valueOf(e.getActionCommand()) ) {
			case Undo :undo();break;
			case Redo :redo();break;
			case 삭제 :delete();break;
			case 잘라내기 :cut();break;
			case 복사 :copy();break;
			case 붙이기 :paste();break;
			case Group:group();break;
			case Ungroup:ungroup();break;
			}
		}
		
	}
}
