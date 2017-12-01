package utils;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import constants.GeConstants;
import constants.GeConstants.EAnchorTypes;

public class GEAnchorList {
	
   private ArrayList<Ellipse2D.Double> anchors;
   
   public GEAnchorList() {
      anchors = new ArrayList<Ellipse2D.Double>();
      for(int i = 0 ; i < GeConstants.EAnchorTypes.values().length - 1 ; i++) {
         anchors.add(new Ellipse2D.Double());
      }
   }
   
   public ArrayList<Ellipse2D.Double> getAnchors() {
	return anchors;
   }


   public EAnchorTypes onAnchors(Point p) {
      for(Ellipse2D ellipse : anchors) {
         if(ellipse.contains(p)) {
            return EAnchorTypes.values()[anchors.indexOf(ellipse)];
         }
      }
      return EAnchorTypes.NONE;
   }
   
   public void draw(Graphics2D g2D) {
      for(int i = 0 ; i < EAnchorTypes.values().length - 1 ; i++) {
         Ellipse2D.Double ellipse = anchors.get(i);
         g2D.setColor(GeConstants.ANCHOR_FILL_COLOR);
         g2D.fill(ellipse);
         g2D.setColor(GeConstants.ANCHOR_LINE_COLOR);
         g2D.draw(ellipse);
      }
   }
   
   public void setPosition(Rectangle r) {
      int x = r.x;
      int y = r.y;
      int w = r.width;
      int h = r.height;
      int dx = GeConstants.ANCHOR_W / 2;
      int dy = GeConstants.ANCHOR_H / 2;
      
      anchors.get(EAnchorTypes.NW.ordinal()).setFrame(x-dx, y-dy, GeConstants.ANCHOR_W, GeConstants.ANCHOR_H);
      anchors.get(EAnchorTypes.NN.ordinal()).setFrame(x+w/2-dx, y-dy, GeConstants.ANCHOR_W, GeConstants.ANCHOR_H);
      anchors.get(EAnchorTypes.NE.ordinal()).setFrame(x+w-dx, y-dy, GeConstants.ANCHOR_W, GeConstants.ANCHOR_H);
      anchors.get(EAnchorTypes.WW.ordinal()).setFrame(x-dx, y+h/2-dy, GeConstants.ANCHOR_W, GeConstants.ANCHOR_H);
      anchors.get(EAnchorTypes.EE.ordinal()).setFrame(x+w-dx, y+h/2-dy, GeConstants.ANCHOR_W, GeConstants.ANCHOR_H);
      anchors.get(EAnchorTypes.SW.ordinal()).setFrame(x-dx, y+h-dy, GeConstants.ANCHOR_W, GeConstants.ANCHOR_H);
      anchors.get(EAnchorTypes.SS.ordinal()).setFrame(x+w/2-dx, y+h-dy, GeConstants.ANCHOR_W, GeConstants.ANCHOR_H);
      anchors.get(EAnchorTypes.SE.ordinal()).setFrame(x+w-dx, y+h-dy, GeConstants.ANCHOR_W, GeConstants.ANCHOR_H);
      anchors.get(EAnchorTypes.RR.ordinal()).setFrame(x+w/2-dx, y-GeConstants.RR_OFFSET, GeConstants.ANCHOR_W, GeConstants.ANCHOR_H);
      
   }

}