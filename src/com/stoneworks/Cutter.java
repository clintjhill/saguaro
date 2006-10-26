/**
 * 
 */
package com.stoneworks;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.handles.PBoundsHandle;

;

/**
 * @author clinthill
 * 
 */
public class Cutter extends PPath {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4984830697051878510L;

	private BrickCanvas canvas = null;

	/**
	 * 
	 */
	public Cutter(BrickCanvas c) {
		this.canvas = c;
		this.setPaint(java.awt.Color.gray);
		PBoundsHandle.addBoundsHandlesTo(this);
		this.setPathToPolyline(new java.awt.geom.Point2D[] {
				new java.awt.Point(0, 0), new java.awt.Point(40, 40),
				new java.awt.Point(40, 80), new java.awt.Point(0,80),
				new java.awt.Point(0,0)});
		setStrokePaint(java.awt.Color.gray);
		this.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals(PNode.PROPERTY_BOUNDS) ||
						evt.getPropertyName().equals(PNode.PROPERTY_FULL_BOUNDS)) {
					Rectangle bounds = getPathReference().getBounds();
					double adj = bounds.width;
					double opp = bounds.height/2;
					double hyp = Math.sqrt(adj*adj + opp*opp);
					angleString = String.format("%d%s", Math.round(Math.toDegrees(Math.asin(opp/hyp))), "¡");
					angleXposition = 10;
					angleYposition = getBounds().height-10;
				}
			}
			
		});
		addInputEventListener(new edu.umd.cs.piccolo.event.PBasicInputEventHandler() {
			private java.awt.Paint currentColor = java.awt.Color.gray;

			@Override
			public void mouseWheelRotated(PInputEvent event) {
				super.mouseWheelRotated(event);
				PNode node = event.getPickedNode();
				double x = node.getWidth() / 2;
				double y = node.getHeight() / 2;
				node.rotateAboutPoint(Math.toRadians(event.getWheelRotation()),
						x, y);
			}

			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(PInputEvent event) {
				super.mouseClicked(event);
				PNode node = event.getPickedNode();
				node.moveToFront();
				if(event.getModifiersEx() == java.awt.event.KeyEvent.CTRL_DOWN_MASK ||
						event.getModifiersEx() == java.awt.event.KeyEvent.META_DOWN_MASK ||
						event.getButton() == java.awt.event.MouseEvent.BUTTON3) {
					for(Brick b : canvas.getBricks()) {
						java.awt.geom.Area area = new java.awt.geom.Area(Cutter.this.getPathReference());
						area.transform(Cutter.this.getTransform());
						b.cut(area);
					}
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
		});
	}

	/**
	 * Returns the BrickCanvas this cutter is associated too.
	 * 
	 * @return
	 */
	public BrickCanvas getBrickCanvas() {
		return this.canvas;

	}

	@Override
	protected void paint(PPaintContext paintContext) {
		super.paint(paintContext);
		java.awt.Graphics2D g2D = (java.awt.Graphics2D)paintContext.getGraphics();
		g2D.setColor(java.awt.Color.white);
		g2D.drawString(angleString, (int)angleXposition, (int)angleYposition);
	}
	
	private String angleString = "45";
	private double angleXposition = 0;
	private double angleYposition = 0;
}
