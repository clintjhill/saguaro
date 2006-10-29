/**
 * 
 */
package com.stoneworks.report;

import java.awt.Color;
import java.awt.Image;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;

import com.stoneworks.Brick;

/**
 * @author clinthill
 * 
 */
public class ConstructionTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4988021335687699776L;

	/**
	 * 
	 */
	public ConstructionTable() {
		this.rows = new java.util.ArrayList<ConstructionRow>();
		this.getInventory(this.getCanvasImage());
	}

	private void assignColors() {
		for(Brick b : this.canvas.getBricks()) {
			b.setColorForReport(this.colorAssigned(b.getDescription()));
		}
	}
	
	private void resetColors() {
		for(Brick b : this.canvas.getBricks()) {
			b.setColor(b.getColor());
		}
	}

	private java.awt.Image getCanvasImage() {
		java.awt.Image canvasImage = null;
		this.canvas.showTools(false);
		this.assignColors();
		canvasImage = this.canvas.getLayer().toImage(940, 570, null);
		this.resetColors();
		this.canvas.showTools(true);
		return canvasImage;
	}

	private void getInventory(Image canvasImage) {
		for (Brick b : this.canvas.getBricks()) {
			boolean found = false;
			int foundOnRow = 0;
			int foundQuantity = 0;
			for (int i = 0; i < this.getRowCount(); i++) {
				final ConstructionRow row = this.rows.get(i);
				// TODO: I don't like this here...need a more robust solution
				// for equals in Brick
				if (row.getCut().getDescription().equals(b.getDescription())) {
					found = true;
					foundOnRow = i;
					foundQuantity = row.getCutCount();
					break;
				}
			}
			if (found) {
				this.setValueAt(foundQuantity + 1, foundOnRow, 2);
			} else {
				Brick newBrick = new Brick(b.getCuttable(),this.colorAssigned(b.getDescription()));
				this.rows.add(new ConstructionRow(canvasImage, 1, newBrick
						.getDescription(), newBrick));
			}
		}
	}

	private java.awt.Color colorAssigned(String cut) {
		if (this.colorManager.containsKey(cut)) {
			this.colorManagerColor = this.colorManager.get(cut);
		} else {
			this.adjustColors();
			this.colorManagerColor = new Color(this.red, this.green, this.blue);
			this.colorManager.put(cut, this.colorManagerColor);
		}
		return this.colorManagerColor;
	}

	private void adjustColors() {
		if (this.red < 255) {
			this.red += 51;
		} else if (this.green < 255) {
			this.green += 51;
		} else if (this.blue < 255) {
			this.blue += 51;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return java.awt.Image.class;
		case 1:
			return JComponent.class;
		case 2:
			return Integer.class;
		case 3:
			return String.class;
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Canvas";
		case 1:
			return "Cut";
		case 2:
			return "Count";
		case 3:
			return "Description";
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 4;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return this.rows.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		final ConstructionRow row = this.rows.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return row.getCanvas();
		case 1:
			return new BrickReportElement(row.getCut());
		case 2:
			return row.getCutCount();
		case 3:
			return row.getCutDescription();
		}
		return null;
	}

	private com.stoneworks.BrickCanvas canvas = com.stoneworks.BrickCanvas
			.getInstance();

	private java.util.ArrayList<ConstructionRow> rows = null;

	private java.awt.Color colorManagerColor = java.awt.Color.black;

	private Hashtable<String, Color> colorManager = new Hashtable<String, Color>();

	private int red = 0;

	private int green = 0;

	private int blue = 0;

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		final ConstructionRow row = this.rows.get(rowIndex);
		switch (columnIndex) {
		case 0:
			row.setCanvas((java.awt.Image) aValue);
			break;
		case 1:
			row.setCut((com.stoneworks.Brick) aValue);
			break;
		case 2:
			row.setCutCount(Integer.valueOf(aValue.toString()));
			break;
		case 3:
			row.setCutDescription((String) aValue);
			break;
		}
	}
}
