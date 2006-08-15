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

	/**
	 * 
	 */
	private static final long serialVersionUID = -7457508494468471137L;

	/**
	 * 
	 */
	public Brick() {
		initialize();
	}

	/**
	 * @param s
	 */
	public Brick(Shape s) {
		super(s);
		initialize();
	}

	/**
	 * 
	 * @param s
	 * @param c
	 */
	public Brick(Shape s, Color c) {
		super(s);
		initialize();
		setColor(c);
	}

	/**
	 * @param sh
	 * @param st
	 */
	public Brick(Shape sh, Stroke st) {
		super(sh, st);
		initialize();
	}

	public void cut(Shape s) {
		java.awt.geom.Area thisArea = new java.awt.geom.Area(getPathReference());
		thisArea.transform(getTransform());
		java.awt.geom.Area otherArea = new java.awt.geom.Area(s);
		if (thisArea.intersects(otherArea.getBounds2D())) {
			thisArea.subtract(otherArea);
			java.awt.geom.Area finalArea = thisArea.createTransformedArea(this
					.getInverseTransform());
			this.setPathTo(finalArea);
		}
	}

	/**
	 * 
	 * @return java.awt.Color
	 */
	public java.awt.Color getColor() {
		return color;
	}

	public Shape getCuttable() {
		return getPathReference();
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
			case PathIterator.SEG_QUADTO:

				break;
			case PathIterator.SEG_CUBICTO:

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
				description = description.concat(StandardMeasurement
						.createForInches(finalDistance).toString());
				break;
			}
			path.next();
		}
		return description;
	}

	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (this.isDataFlavorSupported(flavor)) {
			return new Brick(this.getCuttable(), this.color);
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
		setStrokePaint(java.awt.Color.gray);
		addInputEventListener(new BrickInputEventHandler(this));
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

	private java.awt.Color color = null;

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((color == null) ? 0 : color.hashCode());
		result = PRIME
				* result
				+ ((this.getPathReference() == null) ? 0 : this
						.getPathReference().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Brick other = (Brick) obj;
		return (color.equals(other.color) && getDescription().equals(
				other.getDescription()));
	}
	
}
