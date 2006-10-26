package com.stoneworks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;

/**
 * 
 * @author clinthill
 * 
 */
class BrickInputEventHandler extends PBasicInputEventHandler {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private final Brick brick;

	/**
	 * @param brick
	 */
	BrickInputEventHandler(Brick brick) {
		this.brick = brick;
	}

	@Override
	public void mouseClicked(PInputEvent event) {
		super.mouseClicked(event);
		if (event.getButton() == java.awt.event.MouseEvent.BUTTON3) {
			javax.swing.JPopupMenu popUp = new javax.swing.JPopupMenu();
			javax.swing.JMenuItem remove = new javax.swing.JMenuItem("Remove");
			remove.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					BrickCanvas.getInstance().removeBrick(brick);
				}

			});
			popUp.add(remove);
			if (brick.getUndoManager().canUndo()) {
				javax.swing.JMenuItem undo = new javax.swing.JMenuItem("Undo " + brick.getUndoManager().getPresentationName());
				undo.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						brick.getUndoManager().undo();
					}

				});
				popUp.add(undo);
			}
			if (brick.getUndoManager().canRedo()) {
				javax.swing.JMenuItem redo = new javax.swing.JMenuItem("Redo " + brick.getUndoManager().getPresentationName());
				redo.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						brick.getUndoManager().redo();
					}

				});
				popUp.add(redo);
			}
			Point2D position = event.getCamera().viewToLocal(
					event.getPosition());
			popUp.show(event.getSourceSwingEvent().getComponent(),
					(int) position.getX(), (int) position.getY());
		}
	}

	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		PPath node = (PPath) event.getPickedNode();
		currentColor = node.getStrokePaint();
		node.setStrokePaint(java.awt.Color.RED);
	}

	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		PPath node = (PPath) event.getPickedNode();
		node.setStrokePaint(currentColor);
	}

	@Override
	public void mouseWheelRotated(PInputEvent event) {
		super.mouseWheelRotated(event);
		PNode node = event.getPickedNode();
		double x = node.getWidth() / 2;
		double y = node.getHeight() / 2;
		node.rotateAboutPoint(Math.toRadians(event.getWheelRotation()), x, y);
	}

	private java.awt.Paint currentColor = java.awt.Color.gray;
}