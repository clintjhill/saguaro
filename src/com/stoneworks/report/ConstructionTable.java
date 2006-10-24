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
import com.stoneworks.BrickCanvas;

import edu.umd.cs.piccolo.PNode;

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
	}

	/**
	 * 
	 * @param c
	 */
	public void addCanvas(com.stoneworks.BrickCanvas c) {
		//FIXME: What's going on here - does the singleton remove the need for this method now?
		this.canvas = BrickCanvas.getInstance();
		for(Object obj : c.getBricks()) {
			if(obj instanceof Brick) {
				Brick b = (Brick)obj;
				this.canvas.getLayer().addChild((Brick)b.clone());
			}
		}
		this.canvas.showBrickStrokes(false);
		assignColors();
		getInventory(getCanvasImage());
	}
	
	@SuppressWarnings("unchecked")
	private void assignColors() {
		java.util.ListIterator<PNode> bricks = this.canvas.getLayer().getChildrenIterator();
		while(bricks.hasNext()) {
			PNode node = bricks.next();
			if(node instanceof Brick) {
				Brick b = (Brick)node;
				Brick newB = colorAssigned(b);
				b.setColor(newB.getColor());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private java.awt.Image getCanvasImage() {
		java.awt.Image canvasImage = null;
		this.canvas.getCutTool().setVisible(false);
		this.canvas.getBackgroundImage().setVisible(false);
		canvasImage = this.canvas.getLayer().toImage(940, 570, null);
		this.canvas.getCutTool().setVisible(true);
		this.canvas.getBackgroundImage().setVisible(true);
		return canvasImage;
	}
	
	private void getInventory(Image canvasImage) {
		for(Object obj : this.canvas.getBricks()) {
			if(obj instanceof Brick) {
				Brick b = (Brick)obj;
				boolean found = false;
				int foundOnRow = 0;
				int foundQuantity = 0;
				for(int i = 0; i < this.getRowCount(); i++) {
					final ConstructionRow row = rows.get(i);
					if(row.getCut().getDescription().equals(b.getDescription())) {
						found = true;
						foundOnRow = i;
						foundQuantity = row.getCutCount();
						break;
					}
				}
				if(found) {
					this.setValueAt(foundQuantity+1, foundOnRow, 2);
				} else {
					Brick newBrick = colorAssigned(b);
					rows.add(new ConstructionRow(canvasImage,1,newBrick.getDescription(),newBrick));
				}
			}
		}
	}
	
	private Brick colorAssigned(Brick b) {
		
		Brick returnBrick = new Brick();
		
		if(colorManager.containsKey(b.getDescription())) {
			colorManagerColor = colorManager.get(b.getDescription());
		} else {
			adjustColors();
			colorManagerColor = new Color(red,green,blue);
			colorManager.put(b.getDescription(), colorManagerColor);
		}
		returnBrick.setColor(colorManagerColor);
		returnBrick.setPathTo(b.getCuttable());
	
		return returnBrick;
	}
	
	private void adjustColors() {
		if(red < 255) {
			red += 51;
		}else if (green < 255) {
			green += 51;
		}else if (blue < 255) {
			blue += 51;
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
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 4;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return rows.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		final ConstructionRow row = rows.get(rowIndex);
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

	private com.stoneworks.BrickCanvas canvas = null;
	
	private java.util.ArrayList<ConstructionRow> rows = null;
	
	private java.awt.Color colorManagerColor = java.awt.Color.black;
	
	private Hashtable<String, Color> colorManager = new Hashtable<String, Color>();
	
	private int red = 0;
	
	private int green = 0;
	
	private int blue = 0;

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		final ConstructionRow row = rows.get(rowIndex);
		switch(columnIndex) {
		case 0: 
			row.setCanvas((java.awt.Image)aValue);
			break;
		case 1:
			row.setCut((com.stoneworks.Brick)aValue);
			break;
		case 2:
			row.setCutCount(Integer.valueOf(aValue.toString()));
			break;
		case 3: 
			row.setCutDescription((String)aValue);
			break;
		}
	}
}
