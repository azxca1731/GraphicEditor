package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import constants.GeConstants;
import constants.GeConstants.EToolBarButtons;
import shapes.GEEllipse;
import shapes.GELine;
import shapes.GEPolygon;
import shapes.GERectangle;

public class GEToolbar extends JToolBar {
	private GeDrawingPanel drawingPanel;
	private GEToolBarHandler toolbarHandler;
	public GEToolbar(String label) {
		// TODO Auto-generated constructor stub
		super(label);
		
		toolbarHandler=new GEToolBarHandler();
		
		ButtonGroup buttonGroup= new ButtonGroup();
		JRadioButton rButton = null;
		
		for(EToolBarButtons btn : EToolBarButtons.values()) {
			rButton= new JRadioButton();
			rButton.setIcon(new ImageIcon(GeConstants.IMG_URL+btn.toString()+GeConstants.TOOLBAR_BTN));
			rButton.setSelectedIcon(new ImageIcon(GeConstants.IMG_URL+btn.toString()+GeConstants.TOOLBAR_BTN_SLT));
			rButton.addActionListener(toolbarHandler);
			rButton.setActionCommand(btn.toString());
			this.add(rButton);
			buttonGroup.add(rButton);
		}
	}
	
	public void init(GeDrawingPanel darawingPanel) {
		this.drawingPanel=darawingPanel;
		this.clickDefaultButton();
	}
	
	public void clickDefaultButton() {
		JRadioButton rButton = (JRadioButton)this.getComponent(EToolBarButtons.Rectangle.ordinal());
		rButton.doClick();
	}
	
	private class GEToolBarHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JRadioButton rButton = (JRadioButton)e.getSource();
			if(rButton.getActionCommand().equals(EToolBarButtons.Rectangle.name())) {
				drawingPanel.setCurrentShape(new GERectangle());
			}else if(rButton.getActionCommand().equals(EToolBarButtons.Ellipse.name())) {
				drawingPanel.setCurrentShape(new GEEllipse());
			}else if(rButton.getActionCommand().equals(EToolBarButtons.Line.name())) {
				drawingPanel.setCurrentShape(new GELine());
			}else if(rButton.getActionCommand().equals(EToolBarButtons.Polygon.name())) {
				drawingPanel.setCurrentShape(new GEPolygon());
			}else if(rButton.getActionCommand().equals(EToolBarButtons.Select.name())) {
				drawingPanel.setCurrentShape(null);
			}
		}
	}
}
