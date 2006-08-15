/**
 * 
 */
package com.stoneworks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.PRoot;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.handles.PBoundsHandle;

/**
 * @author clinthill
 * 
 */
public class BrickCanvas extends PCanvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5126521237548541451L;
	static protected Line2D gridLine = new Line2D.Double();
	static protected Stroke gridStroke = new BasicStroke(0.5F);
	static protected Color gridPaint = Color.LIGHT_GRAY;
	static protected double gridSpacing = 6;
	/**
	 * 
	 */
	public BrickCanvas() {
		this.setBackground(null);
		
		PRoot root = getRoot();
		final PCamera camera = getCamera();
		final PLayer gridLayer = new PLayer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 0L;

			protected void paint(PPaintContext paintContext) {
				// make sure grid gets drawn on snap to grid boundaries. And 
				// expand a little to make sure that entire view is filled.
				double bx = (getX() - (getX() % gridSpacing)) - gridSpacing;
				double by = (getY() - (getY() % gridSpacing)) - gridSpacing;
				double rightBorder = getX() + getWidth() + gridSpacing;
				double bottomBorder = getY() + getHeight() + gridSpacing;

				Graphics2D g2 = paintContext.getGraphics();
				Rectangle2D clip = paintContext.getLocalClip();

				g2.setStroke(gridStroke);
				g2.setPaint(gridPaint);

				for (double x = bx; x < rightBorder; x += gridSpacing) {
					gridLine.setLine(x, by, x, bottomBorder);
					if (clip.intersectsLine(gridLine)) {
						g2.draw(gridLine);
					}
				}

				for (double y = by; y < bottomBorder; y += gridSpacing) {
					gridLine.setLine(bx, y, rightBorder, y);
					if (clip.intersectsLine(gridLine)) {
						g2.draw(gridLine);
					}
				}
			}
		};
		
//		 replace standar layer with grid layer.
		root.removeChild(camera.getLayer(0));
		camera.removeLayer(0);
		root.addChild(gridLayer);
		camera.addLayer(gridLayer);

		// add constrains so that grid layers bounds always match cameras view bounds. This makes 
		// it look like an infinite grid.
		camera.addPropertyChangeListener(PNode.PROPERTY_BOUNDS, new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				gridLayer.setBounds(camera.getViewBounds());
			}
		});

		camera.addPropertyChangeListener(PCamera.PROPERTY_VIEW_TRANSFORM, new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				gridLayer.setBounds(camera.getViewBounds());
			}
		});

		gridLayer.setBounds(camera.getViewBounds());
		
		removeInputEventListener(getPanEventHandler());
		addInputEventListener(new PDragEventHandler());
		setTransferHandler(new com.stoneworks.BrickTransferHandler());
		//final PCamera camera = getCamera();
		final PText tooltipNode = new PText();

		tooltipNode.setPickable(false);
		camera.addChild(tooltipNode);

		camera.addInputEventListener(new PBasicInputEventHandler() {
			public void mouseMoved(PInputEvent event) {
				updateToolTip(event);
			}

			public void mouseDragged(PInputEvent event) {
				updateToolTip(event);
			}

			public void updateToolTip(PInputEvent event) {
				PNode n = event.getInputManager().getMouseOver()
						.getPickedNode();
				if (n instanceof com.stoneworks.Brick) {
					com.stoneworks.Brick b = (com.stoneworks.Brick)n;
					String tooltipString = b.getDescription();
					java.awt.geom.Point2D p = event.getCanvasPosition();

					event.getPath().canvasToLocal(p, camera);

					tooltipNode.setText(tooltipString);
					tooltipNode.setOffset(p.getX() + 15, p.getY() - 15);
				} else {
					tooltipNode.setText(null);
				}
			}
		});
	}
	/**
	 * 
	 * @param show
	 */
	public void showBrickStrokes(boolean show) {
		for(Object obj : getLayer().getChildrenReference()) {
			if(obj instanceof Brick) {
				Brick b = (Brick)obj;
				strokePaint = b.getStrokePaint();
				if(show) {
					b.setStrokePaint(strokePaint);
				} else {
					b.setStrokePaint(null);
				}
			}
		}
	}
	
	private Cutter cutTool = null;
	private BackgroundImage backgroundImage = null;
	private Paint strokePaint = null;

	public BackgroundImage getBackgroundImage() {
		if(backgroundImage == null) {
			backgroundImage = new BackgroundImage();
		}
		return backgroundImage;
	}
	@SuppressWarnings("unchecked")
	public void setBackgroundImage(BackgroundImage backgroundImage) {
		if(this.backgroundImage == null) {
			this.backgroundImage = backgroundImage;
		} else {
			// set both properties since we inherited from PImage to extend FilePath
			this.backgroundImage.setImage(backgroundImage.getImage());
			this.backgroundImage.setFilePath(backgroundImage.getFilePath());
		}
		PBoundsHandle.addBoundsHandlesTo(this.backgroundImage);
		final java.util.ListIterator<PNode> nodes = getLayer().getChildrenIterator();
		while (nodes.hasNext()) {
			PNode node = nodes.next();
			if (node instanceof PImage) {
				getLayer().removeChild(node);
				break;
			}
		}
		getLayer().addChild(this.backgroundImage);
		this.backgroundImage.moveToBack();
	}
	public Cutter getCutTool() {
		if(cutTool == null) {
			cutTool = new Cutter(this);
		}
		return cutTool;
	}
	@SuppressWarnings("unchecked")
	public void setCutTool(Cutter cutTool) {
		this.cutTool = cutTool;
		final java.util.ListIterator<PNode> nodes = getLayer().getChildrenIterator();
		while (nodes.hasNext()) {
			PNode node = nodes.next();
			if (node instanceof com.stoneworks.Cutter) {
				getLayer().removeChild(node);
				break;
			}
		}
		getLayer().addChild(this.cutTool);
	}
}
