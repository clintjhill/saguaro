package com.stoneworks;

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
		PNode node = event.getPickedNode();
		node.moveToFront();
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
	public void mousePressed(PInputEvent event) {
		super.mousePressed(event);
//		if (event.getButton() == java.awt.event.MouseEvent.BUTTON3) {
//			javax.swing.JPopupMenu popUp = new javax.swing.JPopupMenu();
//			javax.swing.JMenuItem print = new javax.swing.JMenuItem("Print");
//			print.addActionListener(new java.awt.event.ActionListener() {
//
//				public void actionPerformed(ActionEvent e) { 
//					brick.print();
//				}
//				
//			});
//			popUp.add(print);
//			popUp.show(event.getSourceSwingEvent().getComponent(),
//					(int) event.getPosition().getX(), (int) event
//							.getPosition().getY());
//		} else {
//			super.mousePressed(event);
//		}
	}

	@Override
	public void mouseWheelRotated(PInputEvent event) {
		super.mouseWheelRotated(event);
		PNode node = event.getPickedNode();
		double x = node.getWidth() / 2;
		double y = node.getHeight() / 2;
		node.rotateAboutPoint(Math.toRadians(event.getWheelRotation()), x,
				y);
	}

	private java.awt.Paint currentColor = java.awt.Color.gray;
}