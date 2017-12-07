package utils;

import java.util.ArrayList;

import shapes.GEShape;

public class GEUndoManager {
//	private static int limit = 100; // undo, redo ÃÖ´ñ°ª ¼³Á¤
	private ArrayList<ArrayList<GEShape>> shapeList;
//	private boolean undostatus;
//	private boolean redostatus = false;
	private int shapeListNum;
	
	public GEUndoManager() {
		shapeList = new ArrayList<ArrayList<GEShape>>();
		shapeListNum = 0;
	}
	
/*	public boolean canUndo() {
		return undostatus;
	}

	public boolean canRedo() {
		return redostatus;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}*/
	
	public void clearList() {
		shapeList.clear();
	}
	
	public ArrayList<GEShape> undo(){
		ArrayList<GEShape> tempList = new ArrayList<GEShape>();
		
		if(shapeListNum - 1 < 0){
			System.out.println("Can't undo");
			return tempList;
		} else {
			shapeListNum = shapeListNum - 1;
		
			if(shapeListNum == 0){
				return tempList;
			}
			else {
				ArrayList<GEShape> undoshapeList = shapeList.get(shapeListNum - 1); 
				for(int i = 0; i<undoshapeList.size(); i++){
					tempList.add(undoshapeList.get(i).deepCopy());
				}
				return tempList;
			}
		}
	}
	
	public ArrayList<GEShape> redo(){
		ArrayList<GEShape> tempList = new ArrayList<GEShape>();
		
		if(shapeList.size() < shapeListNum + 1){
			System.out.println("Can't redo");
			if(shapeList.size() == 0){
				return tempList;
			} else {
				ArrayList<GEShape> redoshapeList = shapeList.get(shapeListNum - 1); 
				for(int i =0; i<redoshapeList.size(); i++){
					tempList.add(redoshapeList.get(i).deepCopy());
				}
				return tempList;
			}
		}
		
		shapeListNum = shapeListNum + 1 ;
		
		ArrayList<GEShape> redoshapeList = shapeList.get(shapeListNum - 1); 
		for(int i = 0; i < redoshapeList.size(); i++){
			tempList.add(redoshapeList.get(i).deepCopy());
		}
		return tempList;
	}
	
	public void push(ArrayList<GEShape> shapeList) {
		ArrayList<GEShape> tempList = new ArrayList<GEShape>();
		if(shapeListNum < this.shapeList.size()){
			for(int i = this.shapeList.size()-1; i >= shapeListNum; i--) {
				this.shapeList.remove(i);
			}
		}
		
		for(int i = 0; i < shapeList.size(); i++){
			tempList.add(shapeList.get(i).deepCopy());
		}
		this.shapeList.add(tempList);
		
		shapeListNum = shapeListNum + 1; 
	}
}
