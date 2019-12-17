package com.mycompany.a3.Interfaces;

import com.mycompany.a3.GameObjects.GameObject;

public interface IIterator{
	public boolean hasNext();
	public int get_index();
	GameObject getNext();
}


