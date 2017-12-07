package frames;

import java.util.ArrayList;

import shapes.GEShape;

public class GEClipBoard {
	
	private ArrayList<GEShape> clipBaord;
	
	public GEClipBoard() {
		clipBaord = new ArrayList<GEShape>();
	}
	
	public void add(GEShape shape) {
		
	}
	
	public ArrayList<GEShape> paste() {
		return (ArrayList<GEShape>) clipBaord.clone();
	}

	public void copy(ArrayList<GEShape> shapes) {
		clipBaord.clear(); // 클립보드 비워주기
		GEShape shape;
		for(int i=shapes.size(); i>0; i--) {
			shape = shapes.get(i-1);
			if(shape.isSelected()) {
				clipBaord.add(shape.deepCopy());
			}
		}
	}
	
	public void cut(ArrayList<GEShape> shapes) {
		clipBaord.clear(); //클립보드 비워주기
		GEShape shape;
		for(int i=shapes.size(); i>0; i--) {
			shape = shapes.get(i-1);
			if(shape.isSelected()) {
				clipBaord.add(shape.deepCopy());
				shapes.remove(shape); 
			}
		}
	}

}
