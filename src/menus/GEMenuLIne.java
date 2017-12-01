package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GeConstants.ELineMenuItems;

public class GEMenuLIne extends JMenu{
	public GEMenuLIne(String s) {
		super(s);
		for(ELineMenuItems btn : ELineMenuItems.values()) {
			JMenuItem menuItems = new JMenuItem(btn.toString());
			this.add(menuItems);
		}
	}
}
