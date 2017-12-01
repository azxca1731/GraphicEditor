package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GeConstants.EEditMenuItems;

public class GEMenuEdit extends JMenu {
	public GEMenuEdit(String s) {
		super(s);
		for(EEditMenuItems btn : EEditMenuItems.values()) {
			JMenuItem menuItems = new JMenuItem(btn.toString());
			this.add(menuItems);
		}
	}
}
