package shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class GESelect extends GERectangle {

	@Override
	public void setCoordinate(Point currentP) {
		Rectangle tempRectangle = (Rectangle)myShape;
		tempRectangle.setFrameFromDiagonal(startP.x, startP.y, currentP.x, currentP.y);

	}

}
