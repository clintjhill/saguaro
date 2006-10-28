/**
 * 
 */
package com.stoneworks;


/**
 * @author clinthill
 *
 */
public class CutList extends javax.swing.DefaultListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;

	/**
	 * 
	 */
	public CutList() {
	}

	public boolean contains(Brick brick) {
		boolean hasOne = false;
		for(int i = 0; i < getSize(); i++) {
			Brick inList = (Brick)get(i);
			//TODO: I don't like this here...need a more robust solution for equals in Brick
			if (inList.getDescription().equals(brick.getDescription())) {
				hasOne = true;
				break;
			}
		}
		return hasOne;
	}
}
