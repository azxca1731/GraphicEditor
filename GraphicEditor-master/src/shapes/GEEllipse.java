package shapes;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class GEEllipse extends GEShape{
	
	public GEEllipse(){
		super(new Ellipse2D.Double());
	}
	
	public void initDraw(Point startP) {
		this.startP=startP;
	}
	
	public void setCoordinate(Point currentP) {
		Ellipse2D.Double ellipse=(Ellipse2D.Double)myShape;
		ellipse.setFrame(startP.x,startP.y,
				currentP.x-startP.x,currentP.y-startP.y);
		if(anchorList!=null) {
			anchorList.setPosition(myShape.getBounds());
		}
	}

	@Override
	public GEShape clone() {
		// TODO Auto-generated method stub
		return new GEEllipse();
	}

	@Override
	public GEShape deepCopy() {
		// TODO Auto-generated method stub
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GEEllipse shape = new GEEllipse();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}
	
	
}
