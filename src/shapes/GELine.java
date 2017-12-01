package shapes;

import java.awt.Point;
import java.awt.geom.Line2D;

public class GELine extends GEShape{
	
	public GELine() {
		super(new Line2D.Double());
	}
	
	public void initDraw(Point startP) {
		this.startP =startP;
	}
	
	public void setCoordinate(Point currentP) {
		Line2D.Double line = (Line2D.Double)myShape;
		line.setLine(startP.x,startP.y,currentP.x,currentP.y);
		if(anchorList!=null) {
			anchorList.setPosition(myShape.getBounds());
		}
	}

	@Override
	public GEShape clone() {
		// TODO Auto-generated method stub
		return new GELine();
	}
	
	
}
