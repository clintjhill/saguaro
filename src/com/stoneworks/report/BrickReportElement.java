/**
 * 
 */
package com.stoneworks.report;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author clinthill
 * 
 */
public class BrickReportElement extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1430907386104036509L;

	/**
	 * 
	 */
	public BrickReportElement(com.stoneworks.Brick b) {
		this.brick = b;
	}

	public com.stoneworks.Brick getBrick() {
		return brick;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		java.awt.Graphics2D g2D = (java.awt.Graphics2D) g;
		g2D.setColor(this.brick.getColor());
		g2D.fill(this.brick.getCuttable());
	}

	public void setBrick(com.stoneworks.Brick brick) {
		this.brick = brick;
	}

	private com.stoneworks.Brick brick = null;

}
