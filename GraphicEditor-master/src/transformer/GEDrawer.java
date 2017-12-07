package transformer;

import java.awt.Graphics2D;
import java.awt.Point;

import shapes.GEPolygon;
import shapes.GEShape;

public class GEDrawer extends GETransformer {
	
	public GEDrawer(GEShape shape) {
		// TODO Auto-generated constructor stub
		super(shape);
	}
	
	@Override
	public void transformer(Graphics2D g2d, Point p) {
		// TODO Auto-generated method stub
		g2d.setXORMode(g2d.getBackground());
		g2d.setStroke(dashedLineStroke);
		shape.draw(g2d);
		shape.setCoordinate(p);
		shape.draw(g2d);
	}

	@Override
	public void init(Point p) {
		// TODO Auto-generated method stub
		shape.initDraw(p);
	}

	public void continueDrawing(Point currentP) {
		// TODO Auto-generated method stub
		((GEPolygon)shape).continueDrawing(currentP);
	}
	
}
