package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;
import shapes.GEShape;
import constants.GEConstants;

public class GEGrouper extends GETransformer{

	public GEGrouper(GEShape shape) {
		super(shape);
	}
	
	public void init(Point p){
		shape.initDraw(p);
	}
	
	public void transformer(Graphics2D g2D, Point p){
		g2D.setXORMode(GEConstants.BACKGROUND_COLOR);
		g2D.setStroke(dashedLineStroke);
		shape.draw(g2D);
		shape.setCoordinate(p);
		shape.draw(g2D);
	}
	
	public void finalize(ArrayList<GEShape> shapeList){
		for(GEShape tempShape : shapeList){
			if(shape.getBounds().intersects(tempShape.getBounds())){
				tempShape.setSelected(true);
			}
		}
	}
}
