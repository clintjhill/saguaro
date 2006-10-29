/**
 * 
 */
package com.stoneworks.report;

import java.awt.Image;

import com.stoneworks.Brick;

/**
 * @author clinthill
 * 
 */
public class ConstructionRow extends BrickInventoryRow {

	/**
	 * @param count
	 * @param desc
	 * @param cut
	 */
	public ConstructionRow(Image canvas, int count, String desc, Brick cut) {
		super(count, desc, cut);
		this.canvas = canvas;
	}

	private Image canvas = null;

	public Image getCanvas() {
		return this.canvas;
	}

	public void setCanvas(Image canvas) {
		this.canvas = canvas;
	}
}
