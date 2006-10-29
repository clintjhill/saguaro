/**
 * 
 */
package com.stoneworks;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.io.IOException;

import com.stoneworks.math.StandardMeasurement;

import edu.umd.cs.piccolo.nodes.PPath;

/**
 * @author clinthill
 * 
 */
public class Brick extends PPath implements Cuttable, Transferable {

	/**
	 * 
	 */
	public static final String FLAVOR_NAME = DataFlavor.javaJVMLocalObjectMimeType
			+ ";class=com.stoneworks.Brick";

	public static final String PROPERTY_CUT = "cut";

	public static final int PROPERTY_CODE_CUT = 1 << 19;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7457508494468471137L;

	/**
	 * 
	 */
	public Brick() {
		this.initialize();
	}

	/**
	 * @param s
	 */
	public Brick(Shape s) {
		super(s);
		this.initialize();
	}

	/**
	 * 
	 * @param s
	 * @param c
	 */
	public Brick(Shape s, Color c) {
		super(s);
		this.initialize();
		this.setColor(c);
	}

	/**
	 * @param sh
	 * @param st
	 */
	public Brick(Shape sh, Stroke st) {
		super(sh, st);
		this.initialize();
	}

	public void cut(Shape s) {
		java.awt.geom.Area originalArea = new java.awt.geom.Area(
				this.getPathReference());
		java.awt.geom.Area original = (java.awt.geom.Area) originalArea.clone();
		originalArea.transform(this.getTransform());
		java.awt.geom.Area otherArea = new java.awt.geom.Area(s);
		if (originalArea.intersects(otherArea.getBounds2D())) {
			originalArea.subtract(otherArea);
			java.awt.geom.Area newArea = originalArea
					.createTransformedArea(this.getInverseTransform());
			this.setPathTo(newArea);
			com.stoneworks.undo.UndoBrickCut edit = new com.stoneworks.undo.UndoBrickCut(
					this, original, newArea);
			this.undoManager.addEdit(edit);
			this
					.firePropertyChange(PROPERTY_CODE_CUT, PROPERTY_CUT, null,
							this);
		}
	}

	/**
	 * 
	 * @return java.awt.Color
	 */
	public java.awt.Color getColor() {
		return this.color;
	}

	public Shape getCuttable() {
		return this.getPathReference();
	}

	public javax.swing.undo.UndoManager getUndoManager() {
		return this.undoManager;
	}

	public String getDescription() {
		PathIterator path = this.getPathReference().getPathIterator(null);
		String description = "";
		float[] coords = new float[6];
		int type = 0;
		Point2D origination = null;
		Point2D firstPoint = null;
		Point2D secondPoint = null;
		while (!path.isDone()) {
			type = path.currentSegment(coords);
			float x = coords[0];
			float y = coords[1];
			switch (type) {
			case PathIterator.SEG_MOVETO:
				origination = new Point2D.Float(x, y);
				firstPoint = origination;
				break;
			case PathIterator.SEG_LINETO:
				// TODO: Check this for feet/inches values ... could be trouble
				// later
				secondPoint = new Point2D.Float(x, y);
				double distance = firstPoint.distance(secondPoint);
				description = description.concat(StandardMeasurement
						.createForInches(distance).toString()
						+ ", ");
				firstPoint = secondPoint;
				break;
			case PathIterator.SEG_CLOSE:
				double finalDistance = firstPoint.distance(origination);
				if (finalDistance > 0) {
					description = description.concat(StandardMeasurement
							.createForInches(finalDistance).toString());
				} else {
					description = description.substring(0, description
							.lastIndexOf(","));
				}
				break;
			}
			path.next();
		}
		return description;
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (this.isDataFlavorSupported(flavor)) {
			Brick copy = new Brick((Shape) this.getPathReference().clone());
			copy.setColor(this.color);
			return copy;
		} else {
			return null;
		}
	}

	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor localFlavor = null;
		try {
			localFlavor = new DataFlavor(FLAVOR_NAME);
			return new DataFlavor[] { localFlavor };
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void initialize() {
		this.setStrokePaint(java.awt.Color.gray);
		this.addInputEventListener(new BrickInputEventHandler(this));
		this.undoManager = new javax.swing.undo.UndoManager();
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		for (DataFlavor currentFlavor : this.getTransferDataFlavors()) {
			if (currentFlavor.equals(flavor)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param color
	 */
	public void setColor(java.awt.Color color) {
		this.color = color;
		this.setPaint(color);
	}
	
	public void setColorForReport(java.awt.Color color) {
		this.setPaint(color);
	}

	private java.awt.Color color = null;

	private javax.swing.undo.UndoManager undoManager = null;
}
