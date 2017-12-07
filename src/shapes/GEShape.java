package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import constants.GEConstants.EAnchorTypes;
import utils.GEAnchorList;

public abstract class GEShape {
	protected Shape myShape;
	protected Point startP;
	protected boolean selected;
	protected GEAnchorList anchorList;
	protected EAnchorTypes selectedAnchor;
	protected Color lineColor, fillColor;
	protected AffineTransform affineTransform;
	protected BasicStroke basicStroke;
	
	protected void setShape(Shape shape){
		myShape = shape;
	}
	
	public GEShape(Shape shape) {
		this.myShape = shape;
		anchorList = null;
		selected= false;
		affineTransform = new AffineTransform();
	}
	
	public GEAnchorList getAnchorList() {
		return anchorList;
	}
	

	public EAnchorTypes getSelectedAnchor() {
		return selectedAnchor;
	}
	
	public Rectangle getBounds() {
		return myShape.getBounds();
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void draw(Graphics2D g2d) {
		if(fillColor != null) {
			g2d.setColor(fillColor);
			g2d.fill(myShape);
		}
		if(lineColor != null) {
			g2d.setColor(lineColor);
			g2d.draw(myShape);
		}
		
		if(selected == true) {
			anchorList.setPosition(myShape.getBounds());
			anchorList.draw(g2d);
		}
	}
	
	public EAnchorTypes onAnchor(Point p) {
		selectedAnchor = anchorList.onAnchors(p);
		return selectedAnchor;
	}
	
	public void moveCoordinate(Point moveP) {
		affineTransform.setToTranslation(moveP.getX(), moveP.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void move(Point resizeAnchor) {
		affineTransform.setToTranslation(resizeAnchor.x, resizeAnchor.y);
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void moveReverse(Point resizeAnchor) {
		affineTransform.setToTranslation(-resizeAnchor.x,-resizeAnchor.y);
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void resizeCoordinate(Point2D resizeFactor) {
		affineTransform.setToScale(resizeFactor.getX(), resizeFactor.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public Color getFillColor() {
		return fillColor;
	}
	
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	
	public Color getLineColor() {
		return lineColor;
	}
	
	public void setBasicStroke(BasicStroke basicStroke) {
		this.basicStroke = basicStroke;
	}
	
	public BasicStroke getBasicStroke() {
		return basicStroke;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(selected == true) {
			anchorList = new GEAnchorList();
			anchorList.setPosition(myShape.getBounds());
		}else {
			anchorList = null;
		}
	}
	
	public boolean onShape(Point p) {
		if(anchorList != null) {
			selectedAnchor = anchorList.onAnchors(p);
			if(selectedAnchor != EAnchorTypes.NONE) {
				return true;
			}
		}
		return myShape.intersects(new Rectangle(p.x , p.y , 2 ,2));
	}
	
	public void setAnchorList(GEAnchorList anchorList){
		this.anchorList = anchorList;
	}
	
	public void setGraphicsAttributes(GEShape shape){
		setBasicStroke(shape.getBasicStroke());
		setAnchorList(shape.getAnchorList());
		setAnchorList(shape.getAnchorList());
		setSelected(shape.isSelected());
		setFillColor(shape.getFillColor());
		setLineColor(shape.getLineColor());
		
	}
	
	//원점 이동을 위한 메소드 moveReverse, move
	public void moveReverse(Point2D resizeAnchor){
		affineTransform.setToTranslation(-resizeAnchor.getX(), -resizeAnchor.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void move(Point2D resizeAnchor){
		affineTransform.setToTranslation(resizeAnchor.getX(), resizeAnchor.getY());
		myShape = affineTransform.createTransformedShape(myShape);
	}
	
	public void setMyShape(Shape myShape) {
		this.myShape = myShape;
	}
	
	public Shape getMyShape() {
		return myShape;
	}
	
	public abstract void initDraw(Point startP);
	public abstract void setCoordinate(Point currentP);
	public abstract GEShape clone();
	public abstract GEShape deepCopy();
	
}
