package menus;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GeConstants.EFileMenuItems;

public class GEMenuFile extends JMenu {

	public GEMenuFile(String s) {
		super(s);
		// TODO Auto-generated constructor stub
		for(EFileMenuItems btn : EFileMenuItems.values()) {
			JMenuItem menuItem= new JMenuItem(btn.toString());
			this.add(menuItem);
		}
	}

}