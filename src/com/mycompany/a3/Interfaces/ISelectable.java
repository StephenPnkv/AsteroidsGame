package com.mycompany.a3.Interfaces;

import com.codename1.ui.geom.Point;





public interface ISelectable {
	 public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
	 //public void draw(Graphics g,Point pCmpRelPrnt);
	 public void setSelected(boolean select);
	 public boolean isSelected();
}