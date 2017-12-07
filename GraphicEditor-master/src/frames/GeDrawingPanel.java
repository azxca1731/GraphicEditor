package frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import constants.GeConstants;
import constants.GeConstants.EAnchorTypes;
import constants.GeConstants.EState;
import shapes.GEPolygon;
import shapes.GEShape;
import transformer.GEDrawer;
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
	
	private GEClipBoard clipboard;
	private GEUndoManager undoManager;
	
	public GeDrawingPanel() {	
		super();
		
		shapeList=new ArrayList<>();
		currentState=EState.Idle;
		drawingHandler=new MouseDrawingHandler();
		clipboard = new GEClipBoard();
		undoManager = new GEUndoManager();
		
		fillColor = GeConstants.DEFAULT_FILL_COLOR;
		lineColor = GeConstants.DEFAULT_LINE_COLOR;
		cursorManager = new GECursorManager();
		basicStroke=new BasicStroke(GeConstants.DEFAULT_LINE_WIDTH);
		this.addMouseListener(drawingHandler);
		this.addMouseMotionListener(drawingHandler);
		this.setBackground(GeConstants.BACKGROUND_COLOR);
		this.setForeground(GeConstants.FOREGROUND_COLOR);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d= (Graphics2D)g;
		for(GEShape shape : shapeList) {
			g2d.setStroke(shape.getBasicStroke());
			shape.draw(g2d);
		}
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
			repaint();
	}
	public void ShapeCut() {
		clipboard.cut(shapeList);
		repaint();
	}
	
	public void ShapeCopy() {
		clipboard.copy(shapeList);
	}
	
	public void ShapePaste() {
		for(GEShape shape: clipboard.paste()){
			shapeList.add(shape.deepCopy());
		}
		repaint();
	}
	
	private class MouseDrawingHandler extends MouseInputAdapter{
		@Override
		public void mouseDragged(MouseEvent e) {
			if(currentState != EState.Idle) {
				transformer.transformer((Graphics2D)getGraphics(), e.getPoint());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(currentState==EState.Idle) {
				if(currentShape != null) { 
					clearSelectedShapes();
					selectedShape = null;
					initDraw(e.getPoint());
					transformer = new GEDrawer(currentShape);
					transformer.init(e.getPoint());
					if(currentShape instanceof GEPolygon){
						currentState=EState.NPointsDrawing;
					}else {
						currentState = EState.TwoPointsDrawing;
					}
				}else { 
					selectedShape = onShape(e.getPoint());
					clearSelectedShapes();
					if(selectedShape != null) {
						selectedShape.setSelected(true);
						if(selectedShape.onAnchor(e.getPoint()) == EAnchorTypes.NONE) {
							transformer = new GEMover(selectedShape);
							currentState = EState.Moving;
							transformer.init(e.getPoint());
						}else {
							transformer = new GEResizer(selectedShape);
							currentState = EState.Resizing;
							transformer.init(e.getPoint());
						}
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
