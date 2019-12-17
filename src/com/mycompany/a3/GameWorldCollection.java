
package com.mycompany.a3;
import java.util.Vector;

import com.mycompany.a3.GameObjects.GameObject;
import com.mycompany.a3.Interfaces.ICollection;
import com.mycompany.a3.Interfaces.IIterator;

import java.util.*;

public class GameWorldCollection implements ICollection{
	private Vector<GameObject> space_vector;
	
	public GameWorldCollection() {
		this.space_vector = new Vector<GameObject>();
	}
	
	public void trim_collection() {this.space_vector.trimToSize();}
	
	public void add(GameObject obj) {
		
		this.space_vector.addElement(obj);
		
	}
	
	public void remove_collisions() {
		Iterator<GameObject> it = this.space_vector.iterator();
		while(it.hasNext()) {
			GameObject obj = (GameObject)it.next();
			if(obj.get_col() == true)
				it.remove();
		}
	}
	
	public void clear() {
		this.space_vector.clear();
	}
	
	public void remove_at(int i) {
		this.space_vector.removeElementAt(i);
	}
	
	
	public int get_size() {return this.space_vector.size();}
	
	public IIterator getIterator() {
		return new SpaceVectorIterator();
	}
	
	private class SpaceVectorIterator implements IIterator{
		private int cur_element_index;
		
		public int get_index() {return this.cur_element_index;}
		
		public SpaceVectorIterator() {
			this.cur_element_index = -1;
		}
		
		public boolean hasNext() {
			if(space_vector.size() <= 0) return false;
			if(cur_element_index == space_vector.size() - 1) return false;
			return true;
		}
		
		
		
		public GameObject getNext() {
			++cur_element_index;
			return space_vector.elementAt(cur_element_index);
		}
	}
	
	
	
	
	
}