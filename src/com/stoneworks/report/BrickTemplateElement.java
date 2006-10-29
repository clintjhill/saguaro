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
public class BrickTemplateElement extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8462789317999485419L;

	/**
	 * 
	 */
	public BrickTemplateElement(com.stoneworks.Brick b) {
		this.brick = b;
	}

	public com.stoneworks.Brick getBrick() {
		return this.brick;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		java.awt.Graphics2D g2D = (java.awt.Graphics2D) g;
		g2D.scale(12, 12);
		g2D.setStroke(new java.awt.BasicStroke(0.1F));
		g2D.draw(this.brick.getCuttable());
	}

	public void setBrick(com.stoneworks.Brick brick) {
		this.brick = brick;
	}

	private com.stoneworks.Brick brick = null;
}
