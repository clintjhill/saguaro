/**
 * 
 */
package com.stoneworks.report;

import java.awt.Image;

import javax.swing.table.AbstractTableModel;

public final class DesignReviewTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4720925631029569474L;

	public DesignReviewTable() {

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
		java.awt.Image image = null;
		this.canvas.showTools(false);
		image = this.canvas.getLayer().toImage(940, 570, null);
		this.canvas.showTools(true);
		return image;
	}

	private com.stoneworks.BrickCanvas canvas = com.stoneworks.BrickCanvas
			.getInstance();

}