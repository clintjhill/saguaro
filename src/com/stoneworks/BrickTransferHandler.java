/**
 * 
 */
package com.stoneworks;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 * @author clinthill
 * 
 */
public class BrickTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6056642798602835521L;

	/**
	 * 
	 */
	public BrickTransferHandler() {
	}

	/**
	 * @param property
	 */
	public BrickTransferHandler(String property) {
		super(property);
	}

	@Override
	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
		DataFlavor brickFlavor;
		if (comp instanceof JList)
			return false;
		try {
			brickFlavor = new DataFlavor(Brick.FLAVOR_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		for (DataFlavor current : transferFlavors) {
			if (current.equals(brickFlavor)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		if (c instanceof JList) {
			JList list = (JList) c;
			return (Brick) list.getSelectedValue();
		}
		return super.createTransferable(c);
	}

	@Override
	public void exportAsDrag(JComponent comp, InputEvent e, int action) {
		
		super.exportAsDrag(comp, e, action);
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		
		super.exportDone(source, data, action);
	}

	@Override
	public void exportToClipboard(JComponent comp, Clipboard clip, int action)
			throws IllegalStateException {
		
		super.exportToClipboard(comp, clip, action);
	}

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.COPY_OR_MOVE;
	}

	@Override
	public Icon getVisualRepresentation(Transferable t) {
		
		return super.getVisualRepresentation(t);
	}

	@Override
	public boolean importData(JComponent comp, Transferable t) {
		if (comp instanceof com.stoneworks.BrickCanvas) {
			com.stoneworks.BrickCanvas canvas = (com.stoneworks.BrickCanvas) comp;
			com.stoneworks.Brick brick;
			try {
				brick = (com.stoneworks.Brick) t
						.getTransferData(new java.awt.datatransfer.DataFlavor(
								com.stoneworks.Brick.FLAVOR_NAME));
				Point2D globalPoint = comp.getMousePosition();
				Point2D localPoint = canvas.getCamera().localToView(globalPoint);
				double x = localPoint.getX()-(brick.getWidth()/2);
				double y = localPoint.getY()-(brick.getHeight()/2);
				brick.translate(x, y);
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			canvas.addBrick(brick);
			return true;
		}
		return false;
	}

}
