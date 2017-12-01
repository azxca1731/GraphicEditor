package transformer;

import java.awt.Graphics2D;
import java.awt.Point;

import shapes.GEShape;

public class GEMover extends GETransformer{
	
	private Point previousP;
	
	public GEMover(GEShape shape) {
		super(shape);
		previousP = new Point();
	}
	
	@Override
	public void transformer(Graphics2D g2d, Point p) {
		Point tempP = new Point(p.x - previousP.x, p.y - previousP.y);
		g2d.setXORMode(g2d.getBackground());
		g2d.setStroke(dashedLineStroke);
		shape.draw(g2d);
		shape.moveCoordinate(tempP);
		shape.draw(g2d);
		previousP = p;
	}
	
	@Override
	public void init(Point p) {
		previousP = p;
	}
}
