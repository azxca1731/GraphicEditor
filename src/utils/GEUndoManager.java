package utils;

import java.util.ArrayList;

import shapes.GEShape;

public class GEUndoManager {
	private ArrayList<ArrayList<GEShape>> shapeList;
//	private boolean undostatus = false;
//	private boolean redostatus = false;
	private int number;
	
	public GEUndoManager() {
		shapeList = new ArrayList<ArrayList<GEShape>>();
		number = 0;
	}
	
	public void init() {
		shapeList.clear();
	}
	
	public ArrayList<GEShape> undo(){
		if(number - 1 < 0){
			System.out.println("undo error");
			return new ArrayList<GEShape>();
		}
		number = number - 1;
		if(number == 0){
			return new ArrayList<GEShape>();
		}
		else {
			ArrayList<GEShape> temp = new ArrayList<GEShape>();
			ArrayList<GEShape> base = shapeList.get(number - 1); 
			for(int i =0; i<base.size(); i++){
				temp.add(base.get(i));
			}
			return temp;
		}
	}
	
	public ArrayList<GEShape> redo(){
		ArrayList<GEShape> temp = new ArrayList<GEShape>();
		if(shapeList.size() < number+1){
			System.out.println("redo error");
			if(shapeList.size() == 0){
				return new ArrayList<GEShape>();
			}
			else {
				ArrayList<GEShape> base = shapeList.get(number - 1); 
				for(int i =0; i<base.size(); i++){
					temp.add(base.get(i));
				}
				return temp;
			}
		}
		number = number+1;
		ArrayList<GEShape> base = shapeList.get(number - 1); 
		for(int i =0; i<base.size(); i++){
			temp.add(base.get(i));
		}
		return temp;
	}
	
	public void push(ArrayList<GEShape> shapeList) {
		ArrayList<GEShape> temp = new ArrayList<GEShape>();
		if(number < this.shapeList.size()){
			for(int i = this.shapeList.size() -1; number <= i; i--){
				this.shapeList.remove(i);
			}
		}
		
		for(int i =0; i<shapeList.size(); i++){
			temp.add(shapeList.get(i));
		}
		this.shapeList.add(temp);
		number++; 
	}
	
/*	public boolean canUndo() {
		return undostatus;
	}
	
	public boolean canRedo() {
		return redostatus;
	}*/
}
