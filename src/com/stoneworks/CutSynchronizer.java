/**
 * 
 */
package com.stoneworks;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;

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
		this.hearingList = new java.util.ArrayList<Brick>();
		this.canvas.getLayer().addPropertyChangeListener(
				PNode.PROPERTY_CHILDREN, new PropertyChangeListener() {

					public void propertyChange(PropertyChangeEvent evt) {
						hearingList.clear();
						for (Object node : getCanvas().getLayer()
								.getChildrenReference()) {
							if (node instanceof Brick) {
								Brick brick = (Brick) node;
								brick.addPropertyChangeListener(
										PPath.PROPERTY_PATH, new CutListener());
								hearingList.add(brick);
							}
						}
					}

				});
	}

	public BrickCanvas getCanvas() {
		return canvas;
	}

	public CutList getCutList() {
		return cutList;
	}

	public void setCanvas(BrickCanvas canvas) {
		this.canvas = canvas;
	}

	public void setCutList(CutList cutList) {
		this.cutList = cutList;
	}

	private BrickCanvas canvas = null;

	private CutList cutList = null;

	private java.util.ArrayList<Brick> hearingList = null;

	private class CutListener implements PropertyChangeListener {

		public void propertyChange(final PropertyChangeEvent evt) {
			Brick brick = (Brick) evt.getSource();
			if (evt.getPropertyName().equals(PPath.PROPERTY_PATH)) {
				if (!getCutList().contains(brick)) {
					getCutList().addElement(
							new Brick(brick.getCuttable(), brick.getColor()));
				}
			}
		}

	}
}
