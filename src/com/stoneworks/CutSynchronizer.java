/**
 * 
 */
package com.stoneworks;

import java.awt.Shape;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author clinthill
 * 
 */
public class CutSynchronizer {

	/**
	 * 
	 */
	public CutSynchronizer(BrickCanvas canvas, CutList cutList) {
		this.canvas = canvas;
		this.cutList = cutList;
		this.canvas.addPropertyChangeListener(BrickCanvas.PROPERTY_BRICK_ADDED,
				new PropertyChangeListener() {

					public void propertyChange(PropertyChangeEvent evt) {
						Brick brick = (Brick) evt.getNewValue();
						brick.addPropertyChangeListener(Brick.PROPERTY_CUT,
								new CutListener());
					}

				});
	}

	public BrickCanvas getCanvas() {
		return this.canvas;
	}

	public CutList getCutList() {
		return this.cutList;
	}

	public void setCanvas(BrickCanvas canvas) {
		this.canvas = canvas;
	}

	public void setCutList(CutList cutList) {
		this.cutList = cutList;
	}

	private BrickCanvas canvas = null;

	private CutList cutList = null;

	private class CutListener implements PropertyChangeListener {

		public void propertyChange(final PropertyChangeEvent evt) {
			Brick brick = (Brick) evt.getSource();
			if (evt.getPropertyName().equals(Brick.PROPERTY_CUT)) {
				if (!CutSynchronizer.this.getCutList().contains(brick)) {
					Brick copy = new Brick((Shape) brick.getPathReference()
							.clone());
					copy.setColor(brick.getColor());
					CutSynchronizer.this.getCutList().addElement(copy);
				}
			}
		}
	}
}
