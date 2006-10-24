/**
 * 
 */
package com.stoneworks.report;

import java.awt.Image;

import javax.swing.table.AbstractTableModel;

import com.stoneworks.Brick;
import com.stoneworks.BrickCanvas;

public final class DesignReviewTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4720925631029569474L;

	public DesignReviewTable() {

	}
	/**
	 * 
	 * @param c
	 */
	public void addCanvas(com.stoneworks.BrickCanvas c) {
		//FIXME: What's going on here - we accept a canvas but we don't use it?
		//	Does the singleton remove the need for this method now?
		this.canvas = BrickCanvas.getInstance();
		for(Object obj : c.getBricks()) {
			if(obj instanceof Brick) {
				Brick b = (Brick)obj;
				this.canvas.getLayer().addChild((Brick)b.clone());
			}
		}
		this.canvas.showBrickStrokes(false);
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Image.class;
	}

	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int column) {
		return "DesignReview";
	}

	public int getRowCount() {
		return 1;
	}

	@SuppressWarnings("unchecked")
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(this.canvas != null) {
		java.awt.Image image = null;
		this.canvas.getCutTool().setVisible(false);
		this.canvas.getBackgroundImage().setVisible(false);
		image = this.canvas.getLayer().toImage(940, 570, null);
		this.canvas.getCutTool().setVisible(true);
		this.canvas.getBackgroundImage().setVisible(true);
		return image;
		} else {
			return null;
		}
	}

	private com.stoneworks.BrickCanvas canvas = null;

}