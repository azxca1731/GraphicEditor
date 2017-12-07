package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import constants.GEConstants.EAnchorTypes;
import shapes.GEShape;

public class GEResizer extends GETransformer {
	
	private Point resizeAnchor;
	private Point previousP;
	
	public GEResizer(GEShape shape) {
		super(shape);
		// TODO Auto-generated constructor stub
		previousP = new Point();
	}

	@Override
	public void transformer(Graphics2D g2d, Point p) {
		// TODO Auto-generated method stub
		g2d.setXORMode(g2d.getBackground());
		g2d.setStroke(dashedLineStroke);
		Point2D resizeFactor = computeResizeFactor(previousP, p);
		AffineTransform tempAffine = g2d.getTransform();
		g2d.translate(resizeAnchor.x, resizeAnchor.y);
		shape.draw(g2d);
		shape.resizeCoordinate(resizeFactor);
		shape.draw(g2d);
		g2d.setTransform(tempAffine);
		previousP = p;
	}

	@Override
	public void init(Point p) {
		// TODO Auto-generated method stub
		previousP = p;
		resizeAnchor = getResizeAnchor();
		shape.moveReverse(resizeAnchor);
	}
	
	private Point getResizeAnchor() {
		Point resizeAnchor = new Point();
		Ellipse2D.Double tempAnchor = null;
		if(shape.getSelectedAnchor() == EAnchorTypes.NW) {
			tempAnchor = shape.getAnchorList().getAnchors().get(EAnchorTypes.SE.ordinal());
		}else if(shape.getSelectedAnchor() == EAnchorTypes.NN) {
			tempAnchor = shape.getAnchorList().getAnchors().get(EAnchorTypes.SS.ordinal());
		}else if(shape.getSelectedAnchor() == EAnchorTypes.NE) {
			tempAnchor = shape.getAnchorList().getAnchors().get(EAnchorTypes.SW.ordinal());
		}else if(shape.getSelectedAnchor() == EAnchorTypes.WW) {
			tempAnchor = shape.getAnchorList().getAnchors().get(EAnchorTypes.EE.ordinal());
		}else if(shape.getSelectedAnchor() == EAnchorTypes.EE) {
			tempAnchor = shape.getAnchorList().getAnchors().get(EAnchorTypes.WW.ordinal());
		}else if(shape.getSelectedAnchor() == EAnchorTypes.SW) {
			tempAnchor = shape.getAnchorList().getAnchors().get(EAnchorTypes.NE.ordinal());
		}else if(shape.getSelectedAnchor() == EAnchorTypes.SS) {
			tempAnchor = shape.getAnchorList().getAnchors().get(EAnchorTypes.NN.ordinal());
		}else if(shape.getSelectedAnchor() == EAnchorTypes.SE) {
			tempAnchor = shape.getAnchorList().getAnchors().get(EAnchorTypes.NW.ordinal());
		}
		
		resizeAnchor.setLocation(tempAnchor.x, tempAnchor.y);
		return resizeAnchor;
	}
	
	private Point2D.Double computeResizeFactor(Point previousP, Point currentP){
		double deltaW = 0;
		double deltaH = 0;
		if(shape.getSelectedAnchor() == EAnchorTypes.NW) {
			deltaW =- (currentP.x - previousP.x);
			deltaH =- (currentP.y - previousP.y);
		} else if (shape.getSelectedAnchor() == EAnchorTypes.NN) {
			deltaW = 0;
			deltaH =- (currentP.y - previousP.y);
		} else if (shape.getSelectedAnchor() == EAnchorTypes.NE) {
			deltaW = currentP.x - previousP.x;
			deltaH =- (currentP.y - previousP.y);
		} else if (shape.getSelectedAnchor() == EAnchorTypes.WW) {
			deltaW =- (currentP.x - previousP.x);
			deltaH = 0;
		} else if (shape.getSelectedAnchor() == EAnchorTypes.EE) {
			deltaW = currentP.x - previousP.x;
			deltaH = 0;
		} else if (shape.getSelectedAnchor() == EAnchorTypes.SW) {
			deltaW =- (currentP.x - previousP.x);
			deltaH = currentP.y - previousP.y;
		} else if (shape.getSelectedAnchor() == EAnchorTypes.SS) {
			deltaW = 0;
			deltaH = currentP.y - previousP.y;
		} else if (shape.getSelectedAnchor() == EAnchorTypes.SE) {
			deltaW = currentP.x - previousP.x;
			deltaH = currentP.y - previousP.y;
		} 
		double currentW = shape.getBounds().getWidth();
		double currentH = shape.getBounds().getHeight();
		double xFactor = 1.0;
		if(currentW > 0.0) {
			xFactor = (1.0 + deltaW / currentW);
		}
		double yFactor = 1.0;
		if(currentH > 0.0) {
			yFactor = (1.0 + deltaH / currentH);
		}
		return new Point2D.Double(xFactor, yFactor);
	}
	
	public void finalize(Point point) {
		shape.move(resizeAnchor);
	}

}
