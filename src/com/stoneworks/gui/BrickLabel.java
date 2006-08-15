/*
 * BrickLabel.java
 * Stoneworks 2006 v2
 * clinthill
 * May 29, 2006
 * Copyright (c) 2006, H3O Software - clint hill (clint.hill@h3osoftware.com)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, 
 *		 this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, 
 *		 this list of conditions and the following disclaimer in the documentation 
 * 		 and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors 
 * 		 may be used to endorse or promote products derived from this software 
 *		 without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
/**
 *
 */
package com.stoneworks.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.stoneworks.math.StandardMeasurement;

/**
 * A class that extends a JLabel and draws the shape as well as shows a description of the shape
 * using {@link StandardMeasurement}
 * 
 * @author clinthill
 * 
 */
public class BrickLabel extends JLabel implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1908727125599215608L;

	public BrickLabel() {
		
	}
	/**
	 * Default Constructor
	 * 
	 */
	public BrickLabel(com.stoneworks.Brick b) {
		this.brick = b;
		this.initialize();
	}

	
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		com.stoneworks.Brick brick = (com.stoneworks.Brick)value;
		this.brick = brick;
		this.initialize();
		return this;
	}
	
	private void initialize() {
		setIcon(new ShapeIcon(this.brick.getCuttable(),this.brick.getColor()));
		setText(this.brick.getDescription());
	}

	private com.stoneworks.Brick brick = null;

	/**
	 * A class that draws an Icon of the shape passed in
	 * 
	 * @author clinthill
	 * 
	 */
	private class ShapeIcon implements Icon {

		/**
		 * Constructs a ShapeIcon with the shape passed in
		 * 
		 * @param shape
		 * @param color
		 */
		public ShapeIcon(java.awt.Shape shape, java.awt.Color color) {
			this.shape = shape;
			this.color = color;
		}

		/**
		 * Returns the height of the shape
		 * 
		 * @return int
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconHeight()
		 */
		public int getIconHeight() {
			return shape.getBounds().height+5;
		}

		/**
		 * Returns the width of the shape
		 * 
		 * @return int
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#getIconWidth()
		 */
		public int getIconWidth() {
			return shape.getBounds().width+5;
		}

		/**
		 * Paints the shape at location 5,5 in the label
		 * 
		 * @param c
		 * @param g
		 * @param x
		 * @param y
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
		 */
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2d = (Graphics2D) g;
			RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHints(renderHints);
			AffineTransform currentTransform = g2d.getTransform();
			g2d.translate(2, 2);
			g2d.setColor(this.color);
			g2d.fill(this.shape);
			g2d.setTransform(currentTransform);
		}

		private java.awt.Color color = java.awt.Color.black;

		private java.awt.Shape shape = null;
	}
}
