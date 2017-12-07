package transformer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

import constants.GeConstants;
import shapes.GEShape;

public abstract class GETransformer {
	protected GEShape shape;
	protected BasicStroke dashedLineStroke;
	
	public GETransformer(GEShape shape) {
		this.shape = shape;
		float dashes[] = {GeConstants.DEFAULT_DASH_OFFSET};
		dashedLineStroke = new BasicStroke(
				GeConstants.DEFAULT_DASHEDLINE_WIDTH,
				BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 10, dashes, 0);
	}
	
	public abstract void transformer(Graphics2D g2d, Point p);
	public abstract void init(Point p);
}
