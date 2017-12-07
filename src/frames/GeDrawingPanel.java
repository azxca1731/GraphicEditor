package frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import constants.GEConstants;
import constants.GEConstants.EAnchorTypes;
import constants.GEConstants.EState;
import shapes.GEGroup;
import shapes.GEPolygon;
import shapes.GESelect;
import shapes.GEShape;
import transformer.GEDrawer;
import transformer.GEGrouper;
import transformer.GEMover;
import transformer.GEResizer;
import transformer.GETransformer;
import utils.GECursorManager;
import utils.GEUndoManager;

public class GeDrawingPanel extends JPanel {
	private GEShape currentShape;
	private GEShape selectedShape;
	private ArrayList<GEShape> shapeList;
	private EState currentState;
	private GETransformer transformer;
	private MouseDrawingHandler drawingHandler;
	private Color fillColor, lineColor;	
	private GECursorManager cursorManager;
	private BasicStroke basicStroke;
	private Point tempP;
	private double shiftResizeW, shiftResizeH;
	
	private GEClipBoard clipboard;
	private GEUndoManager undoManager;
	
	public GeDrawingPanel() {	
		super();
		
		shapeList=new ArrayList<>();
		currentState=EState.Idle;
		drawingHandler=new MouseDrawingHandler();
		clipboard = new GEClipBoard();
		undoManager = new GEUndoManager();
		
		fillColor = GEConstants.DEFAULT_FILL_COLOR;
		lineColor = GEConstants.DEFAULT_LINE_COLOR;
		cursorManager = new GECursorManager();
		basicStroke=new BasicStroke(GEConstants.DEFAULT_LINE_WIDTH);
		this.addMouseListener(drawingHandler);
		this.addMouseMotionListener(drawingHandler);
		this.setBackground(GEConstants.BACKGROUND_COLOR);
		this.setForeground(GEConstants.FOREGROUND_COLOR);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d= (Graphics2D)g;
		for(GEShape shape : shapeList) {
			//g2d.setStroke(shape.getBasicStroke());
			shape.draw(g2d);
		}
	}
	
	public void setSelectedShape(GEShape selectedShape) {
		this.selectedShape = selectedShape;
	}
	
	public void setStroke(BasicStroke basicStroke){
		if(selectedShape != null) {
			selectedShape.setBasicStroke(basicStroke);
			undoManager.push(shapeList);
			repaint();
		}else {
			this.basicStroke=basicStroke;
		}
	}
	
	public void setFillColor(Color fillColor) {
		if(selectedShape != null) {
			selectedShape.setFillColor(fillColor);
			undoManager.push(shapeList);
			repaint();
		}else {
			this.fillColor = fillColor;	
		}
	}
	
	public void setLineColor(Color lineColor) {
		if(selectedShape != null) {
			selectedShape.setLineColor(lineColor);
			undoManager.push(shapeList);
			repaint();
		}else {
			this.lineColor = lineColor;
		}
	}

	public void setCurrentShape(GEShape currentShape) {
		this.currentShape = currentShape;
	}
	
	private void initDraw(Point startP) {
		currentShape=currentShape.clone();
		currentShape.setFillColor(fillColor);
		currentShape.setLineColor(lineColor);
		currentShape.setBasicStroke(basicStroke);
	}

	private void continueDrawing(Point currentP) {
		((GEDrawer)transformer).continueDrawing(currentP);
	}
	
	private void finishDraw() {
		shapeList.add(currentShape);
		undoManager.push(shapeList);
	}
	
	private GEShape onShape(Point p) {
		for(int i = shapeList.size() -1; i >= 0; i--) {
			GEShape shape = shapeList.get(i);
			if(shape.onShape(p)) {
				return shape;
			}
		}
		return null;
	}
	
	private void clearSelectedShapes() {
		for(GEShape shape : shapeList) {
			shape.setSelected(false);
		}
	}
	
	public void undo() {
		shapeList = undoManager.undo();
		selectedShape = null;
		repaint();
	}
	
	public void redo() {
		shapeList = undoManager.redo();
		selectedShape = null;
		repaint();
	}
	
	public void ShapeDelete() {
		for(int i = shapeList.size(); i > 0 ; i--){
			GEShape shape = shapeList.get(i-1);
			if(shape.isSelected()){
				shape.setSelected(false);
				shapeList.remove(shape);
			}
		}
		undoManager.push(shapeList);
		repaint();
	}
	
	public void ShapeCut() {
		clipboard.cut(shapeList);
		undoManager.push(shapeList);
		repaint();
	}
	
	public void ShapeCopy() {
		clipboard.copy(shapeList);
	}
	
	public void ShapePaste() {
		for(GEShape shape: clipboard.paste()){
			shapeList.add(shape.deepCopy());
		}
		undoManager.push(shapeList);
		repaint();
	}
	
	public void group(GEGroup group){
		boolean check = false;
		for(int i = shapeList.size(); i > 0; i--){
			GEShape shape = shapeList.get(i - 1);
			if(shape.isSelected()){
				shape.setSelected(false);
				group.addShape(shape);
				shapeList.remove(shape);
				check = true;
			}
		}
		if(check){
			group.setSelected(true);
			shapeList.add(group);
		}
		setSelectedShape(group);
		repaint();
	}
	
	public void unGroup(){
		Vector<GEShape> tempList = new Vector<GEShape>();
		for(int i = shapeList.size(); i > 0; i--){
			GEShape shape = shapeList.get(i - 1);
			if(shape instanceof GEGroup && shape.isSelected()){
				for(GEShape childShape : ((GEGroup)shape).getChildList()){
					childShape.setSelected(true);
					tempList.add(childShape);
				}
				shapeList.remove(shape);
			}
		}
		shapeList.addAll(tempList);
		repaint();
	}
	
	private class MouseDrawingHandler extends MouseInputAdapter{
		@Override
		public void mouseDragged(MouseEvent e) {
			if(currentState != EState.Idle) {
				transformer.transformer((Graphics2D)getGraphics(), e.getPoint());
				if(transformer instanceof GEMover){
					((GEMover)transformer).setMove(true);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(currentState == EState.Idle){
				if(currentShape instanceof GESelect){
					setSelectedShape(onShape(e.getPoint()));
					if(selectedShape != null){
						clearSelectedShapes();
						selectedShape.setSelected(true);
						if(selectedShape.onAnchor(e.getPoint()) == EAnchorTypes.NONE){
							transformer = new GEMover(selectedShape);
							currentState = EState.Moving;
							transformer.init(e.getPoint()); /////////
						}else{
							shiftResizeW = selectedShape.getMyShape().getBounds().width-6;
							shiftResizeH = selectedShape.getMyShape().getBounds().height-6;
							transformer = new GEResizer(selectedShape);
							currentState = EState.Resizing;
							transformer.init(e.getPoint());
							if(transformer.getShape().getSelectedAnchor() == EAnchorTypes.NW){
								tempP.setLocation(selectedShape.getAnchorList().getAnchors().get(EAnchorTypes.SE.ordinal()).getCenterX(), 
										selectedShape.getAnchorList().getAnchors().get(EAnchorTypes.SE.ordinal()).getCenterY());
							}else if(transformer.getShape().getSelectedAnchor() == EAnchorTypes.NE){
								tempP.setLocation(selectedShape.getAnchorList().getAnchors().get(EAnchorTypes.SW.ordinal()).getCenterX(), 
										selectedShape.getAnchorList().getAnchors().get(EAnchorTypes.SW.ordinal()).getCenterY());
							}else if(transformer.getShape().getSelectedAnchor() == EAnchorTypes.SW){
								tempP.setLocation(selectedShape.getAnchorList().getAnchors().get(EAnchorTypes.NE.ordinal()).getCenterX(), 
										selectedShape.getAnchorList().getAnchors().get(EAnchorTypes.NE.ordinal()).getCenterY());
							}else if(transformer.getShape().getSelectedAnchor() == EAnchorTypes.SE){
								tempP.setLocation(selectedShape.getAnchorList().getAnchors().get(EAnchorTypes.NW.ordinal()).getCenterX(), 
										selectedShape.getAnchorList().getAnchors().get(EAnchorTypes.NW.ordinal()).getCenterY());
							}
						}
						
					}else{
						currentState = EState.Selecting;
						tempP = e.getPoint();
						clearSelectedShapes();
						initDraw(e.getPoint());
						transformer = new GEGrouper(currentShape);
						transformer.init(e.getPoint());
					}
				}else{
					clearSelectedShapes();
					initDraw(e.getPoint());
					transformer = new GEDrawer(currentShape);
					transformer.init(e.getPoint());
					if(currentShape instanceof GEPolygon){
						currentState = EState.NPointsDrawing;
					}else{
						tempP = e.getPoint();
						currentState = EState.TwoPointsDrawing;
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(currentState == EState.TwoPointsDrawing) {
				finishDraw();
			}else if(currentState == EState.NPointsDrawing) {
				return;
			}else if(currentState == EState.Resizing) {
				((GEResizer)transformer).finalize(e.getPoint());
				undoManager.push(shapeList);
			}else if(currentState == EState.Moving) {
				if(((GEMover)transformer).isMoved()){
					undoManager.push(shapeList);
				}
			}else if(currentState == EState.Selecting){
				((GEGrouper)transformer).finalize(shapeList);
				setCurrentShape(new GESelect());
			}
			currentState = EState.Idle;
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON1) {
				if(currentState==EState.NPointsDrawing) {
					if(e.getClickCount() == 1) {
						continueDrawing(e.getPoint());
					}else if(e.getClickCount() == 2) {
						finishDraw();
						currentState=EState.Idle;
						repaint();
					}
					
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(currentState == EState.NPointsDrawing) {
				transformer.transformer((Graphics2D)getGraphics(), e.getPoint());
			}else if(currentState == EState.Idle) {
				GEShape shape = onShape(e.getPoint());
				if(shape == null) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}else if(shape.isSelected() == true) {
					EAnchorTypes anchorTypes = shape.onAnchor(e.getPoint());
					setCursor(cursorManager.get(anchorTypes.ordinal()));
				}
			}
		}
	}
}
