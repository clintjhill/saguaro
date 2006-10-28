/**
 * 
 */
package com.stoneworks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;

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

	static protected Line2D gridLine = new Line2D.Double();

	static protected Color gridPaint = Color.LIGHT_GRAY;

	static protected double gridSpacing = 6;

	static protected Stroke gridStroke = new BasicStroke(0.5F);

	private static BrickCanvas instance = null;

	public static final String PROPERTY_BRICK_ADDED = "brickAdded";

	public static final String PROPERTY_BRICK_REMOVED = "brickRemoved";

	/**
	 * 
	 */
	private static final long serialVersionUID = -5126521237548541451L;

	/**
	 * Returns Singleton instance of BrickCanvas
	 * 
	 * @return
	 */
	public static BrickCanvas getInstance() {
		if (instance == null) {
			instance = new BrickCanvas();
		}
		return instance;
	}

	private BackgroundImage backgroundImage = null;

	private Cutter cutTool = null;

	private boolean paintGrid = true;

	/**
	 * 
	 */
	private BrickCanvas() {
		this.setBackground(null);

		PRoot root = getRoot();
		final PCamera camera = getCamera();
		final PLayer gridLayer = new PLayer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 0L;

			protected void paint(PPaintContext paintContext) {
				if (paintGrid) {
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
				} else {
					super.paint(paintContext);
				}
			}
		};

		// replace standar layer with grid layer.
		root.removeChild(camera.getLayer(0));
		camera.removeLayer(0);
		root.addChild(gridLayer);
		camera.addLayer(gridLayer);

		// add constrains so that grid layers bounds always match cameras view
		// bounds. This makes it look like an infinite grid.
		camera.addPropertyChangeListener(PNode.PROPERTY_BOUNDS,
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						gridLayer.setBounds(camera.getViewBounds());
					}
				});

		camera.addPropertyChangeListener(PCamera.PROPERTY_VIEW_TRANSFORM,
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						gridLayer.setBounds(camera.getViewBounds());
					}
				});

		gridLayer.setBounds(camera.getViewBounds());

		removeInputEventListener(getPanEventHandler());
		PDragEventHandler dragHandler = new PDragEventHandler() {

			@Override
			public void mouseDragged(PInputEvent e) {
				PNode node = e.getPickedNode();
				if (node instanceof Brick || node instanceof Cutter) {
					super.mouseDragged(e);
				}
			}

			@Override
			public void mousePressed(PInputEvent e) {
				PNode node = e.getPickedNode();
				if (node instanceof Brick || node instanceof Cutter) {
					super.mousePressed(e);
				}
			}

		};
		dragHandler.setMoveToFrontOnPress(true);
		addInputEventListener(dragHandler);
		setTransferHandler(new com.stoneworks.BrickTransferHandler());
		// final PCamera camera = getCamera();
		final PText tooltipNode = new PText();

		tooltipNode.setPickable(false);
		camera.addChild(tooltipNode);

		camera.addInputEventListener(new PBasicInputEventHandler() {
			public void mouseDragged(PInputEvent event) {
				updateToolTip(event);
			}

			public void mouseMoved(PInputEvent event) {
				updateToolTip(event);
			}

			public void updateToolTip(PInputEvent event) {
				PNode n = event.getInputManager().getMouseOver()
						.getPickedNode();
				if (n instanceof com.stoneworks.Brick) {
					com.stoneworks.Brick b = (com.stoneworks.Brick) n;
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
	 * @param b
	 */
	public void addBrick(Brick b) {
		getLayer().addChild(b);
		firePropertyChange(PROPERTY_BRICK_ADDED, null, b);
	}

	public BackgroundImage getBackgroundImage() {
		if (backgroundImage == null) {
			backgroundImage = new BackgroundImage();
		}
		return backgroundImage;
	}

	/**
	 * 
	 * @return
	 */
	public Collection<Brick> getBricks() {
		Collection<Brick> bricks = new ArrayList<Brick>();
		for (Object o : getLayer().getAllNodes()) {
			if (o instanceof Brick)
				bricks.add((Brick) o);
		}
		return bricks;
	}

	public Cutter getCutTool() {
		if (cutTool == null) {
			cutTool = new Cutter(this);
		}
		return cutTool;
	}

	/**
	 * 
	 * @param b
	 */
	public void removeBrick(Brick b) {
		getLayer().removeChild(b);
		firePropertyChange(PROPERTY_BRICK_REMOVED, b, null);
	}

	@SuppressWarnings("unchecked")
	public void setBackgroundImage(BackgroundImage backgroundImage) {
		if (this.backgroundImage == null) {
			this.backgroundImage = backgroundImage;
		} else {
			// set both properties since we inherited from PImage to extend
			// FilePath
			this.backgroundImage.setImage(backgroundImage.getImage());
			this.backgroundImage.setFilePath(backgroundImage.getFilePath());
		}
		PBoundsHandle.addBoundsHandlesTo(this.backgroundImage);
		final java.util.ListIterator<PNode> nodes = getLayer()
				.getChildrenIterator();
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

	@SuppressWarnings("unchecked")
	public void setCutTool(Cutter cutTool) {
		this.cutTool = cutTool;
		final java.util.ListIterator<PNode> nodes = getLayer()
				.getChildrenIterator();
		while (nodes.hasNext()) {
			PNode node = nodes.next();
			if (node instanceof com.stoneworks.Cutter) {
				getLayer().removeChild(node);
				break;
			}
		}
		getLayer().addChild(this.cutTool);
	}

	/**
	 * 
	 * @param show
	 */
	public void showBrickStrokes(boolean show) {
		for (Brick b : getBricks()) {
			if (show) {
				b.setStrokePaint(java.awt.Color.gray);
			} else {
				b.setStrokePaint(null);
			}
		}
	}

	/**
	 * Sets all of the drawing space tools to visible or not
	 * 
	 * @param isVisible
	 */
	public void showTools(boolean isVisible) {
		this.getCutTool().setVisible(isVisible);
		this.getBackgroundImage().setVisible(isVisible);
		this.paintGrid = isVisible;
		this.showBrickStrokes(isVisible);
	}
}
