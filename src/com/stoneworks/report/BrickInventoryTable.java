/**
 * 
 */
package com.stoneworks.report;

import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;

/**
 * @author clinthill
 * 
 */
public class BrickInventoryTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3029384543681217175L;

	/**
	 * 
	 * @param b
	 */
	public void addBrick(com.stoneworks.Brick b) {
		boolean found = false;
		int foundOnRow = 0;
		int foundQuantity = 0;
		for(int i = 0; i < this.getRowCount(); i++) {
			final BrickInventoryRow row = rows.get(i);
			//TODO: I don't like this here...need a more robust solution for equals in Brick
			if(row.getCut().getDescription().equals(b.getDescription())) {
				found = true;
				foundOnRow = i;
				foundQuantity = row.getCutCount();
				break;
			}
		}
		if(found) {
			this.setValueAt(foundQuantity+1, foundOnRow, 1);
		} else {
			rows.add(new BrickInventoryRow(1,b.getDescription(),b));
		}
	}

	/**
	 * 
	 */
	public BrickInventoryTable(boolean forTemplates) {
		this.forTemplates = forTemplates;
		rows = new java.util.ArrayList<BrickInventoryRow>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return JComponent.class;
		case 1:
			return Integer.class;
		case 2:
			return String.class;
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Cut";
		case 1:
			return "Count";
		case 2:
			return "Description";
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return rows.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		final BrickInventoryRow row = rows.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return (forTemplates) ? new BrickTemplateElement(row.getCut()) :
				new BrickReportElement(row.getCut());
		case 1:
			return row.getCutCount();
		case 2:
			return row.getCutDescription();
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		final BrickInventoryRow row = rows.get(rowIndex);
		switch(columnIndex) {
		case 0: 
			row.setCut((com.stoneworks.Brick)aValue);
			break;
		case 1:
			row.setCutCount(Integer.valueOf(aValue.toString()));
			break;
		case 2: 
			row.setCutDescription((String)aValue);
			break;
		}
	}
	
	private java.util.ArrayList<BrickInventoryRow> rows = null;
	
	private boolean forTemplates = false;

}
