package shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GERectangle extends GEShape{
	
	public GERectangle(){
		super(new Rectangle());
	}
	
	public void initDraw(Point startP) {
		this.startP=startP;
	}
	
	public void setCoordinate(Point currentP) {
		Rectangle rectangle=(Rectangle)myShape;
		rectangle.setFrame(startP.x,startP.y,
				currentP.x-startP.x,currentP.y-startP.y);
		if(anchorList!=null) {
			anchorList.setPosition(myShape.getBounds());
		}
	}

	@Override
	public GEShape clone() {
		// TODO Auto-generated method stub
		return new GERectangle();
	}

	@Override
	public GEShape deepCopy() {
		// TODO Auto-generated method stub
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GERectangle shape = new GERectangle();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}	
	
}
