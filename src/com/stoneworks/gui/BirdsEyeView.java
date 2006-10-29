/**
 * 
 */
package com.stoneworks.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragSequenceEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolo.util.PPaintContext;
import edu.umd.cs.piccolox.nodes.P3DRect;

/**
 * The Birds Eye View Class
 */
public class BirdsEyeView extends PCanvas implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1113897215388843460L;

	/**
	 * This is the node that shows the viewed area.
	 */
	PNode areaVisiblePNode;

	/**
	 * This is the canvas that is being viewed
	 */
	PCanvas viewedCanvas;

	/**
	 * The change listener to know when to update the birds eye view.
	 */
	PropertyChangeListener changeListener;

	int layerCount;

	/**
	 * Creates a new instance of a BirdsEyeView
	 */
	public BirdsEyeView() {

		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				BirdsEyeView.this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
			}

		});
		// create the PropertyChangeListener for listening to the viewed
		// canvas
		this.changeListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				BirdsEyeView.this.updateFromViewed();
			}
		};

		// create the coverage node
		this.areaVisiblePNode = new P3DRect();
		this.areaVisiblePNode.setPaint(new Color(128, 128, 255));
		this.areaVisiblePNode.setTransparency(.6f);
		this.areaVisiblePNode.setBounds(0, 0, 100, 100);
		this.getCamera().addChild(this.areaVisiblePNode);

		// add the drag event handler
		this.getCamera().addInputEventListener(new PDragSequenceEventHandler() {
			@Override
			protected void startDrag(PInputEvent e) {
				if (e.getPickedNode() == BirdsEyeView.this.areaVisiblePNode) {
					super.startDrag(e);
				}
			}

			@Override
			protected void drag(PInputEvent e) {
				PDimension dim = e.getDelta();
				BirdsEyeView.this.viewedCanvas.getCamera().translateView(0 - dim.getWidth(),
						0 - dim.getHeight());
			}

		});

		// remove Pan and Zoom
		this.removeInputEventListener(this.getPanEventHandler());
		this.removeInputEventListener(this.getZoomEventHandler());

		this.setDefaultRenderQuality(PPaintContext.LOW_QUALITY_RENDERING);

	}

	public void connect(PCanvas canvas, PLayer[] viewed_layers) {

		this.viewedCanvas = canvas;
		this.layerCount = 0;

		this.viewedCanvas.getCamera().addPropertyChangeListener(this.changeListener);

		for (this.layerCount = 0; this.layerCount < viewed_layers.length; ++this.layerCount) {
			this.getCamera().addLayer(this.layerCount, viewed_layers[this.layerCount]);
		}

	}

	/**
	 * Add a layer to list of viewed layers
	 */
	public void addLayer(PLayer new_layer) {
		this.getCamera().addLayer(new_layer);
		this.layerCount++;
	}

	/**
	 * Remove the layer from the viewed layers
	 */
	public void removeLayer(PLayer old_layer) {
		this.getCamera().removeLayer(old_layer);
		this.layerCount--;
	}

	/**
	 * Stop the birds eye view from receiving events from the viewed canvas and
	 * remove all layers
	 */
	public void disconnect() {
		this.viewedCanvas.getCamera().removePropertyChangeListener(this.changeListener);

		for (int i = 0; i < this.getCamera().getLayerCount(); ++i) {
			this.getCamera().removeLayer(i);
		}

	}

	/**
	 * This method will get called when the viewed canvas changes
	 */
	public void propertyChange(PropertyChangeEvent event) {
		this.updateFromViewed();
	}

	/**
	 * This method gets the state of the viewed canvas and updates the
	 * BirdsEyeViewer This can be called from outside code
	 */
	public void updateFromViewed() {

		double viewedX;
		double viewedY;
		double viewedHeight;
		double viewedWidth;

		double ul_camera_x = this.viewedCanvas.getCamera().getViewBounds().getX();
		double ul_camera_y = this.viewedCanvas.getCamera().getViewBounds().getY();
		double lr_camera_x = ul_camera_x
				+ this.viewedCanvas.getCamera().getViewBounds().getWidth();
		double lr_camera_y = ul_camera_y
				+ this.viewedCanvas.getCamera().getViewBounds().getHeight();

		Rectangle2D drag_bounds = this.getCamera().getUnionOfLayerFullBounds();

		double ul_layer_x = drag_bounds.getX();
		double ul_layer_y = drag_bounds.getY();
		double lr_layer_x = drag_bounds.getX() + drag_bounds.getWidth();
		double lr_layer_y = drag_bounds.getY() + drag_bounds.getHeight();

		// find the upper left corner

		// set to the lesser value
		if (ul_camera_x < ul_layer_x) {
			viewedX = ul_layer_x;
		} else {
			viewedX = ul_camera_x;
		}

		// same for y
		if (ul_camera_y < ul_layer_y) {
			viewedY = ul_layer_y;
		} else {
			viewedY = ul_camera_y;
		}

		// find the lower right corner

		// set to the greater value
		if (lr_camera_x < lr_layer_x) {
			viewedWidth = lr_camera_x - viewedX;
		} else {
			viewedWidth = lr_layer_x - viewedX;
		}

		// same for height
		if (lr_camera_y < lr_layer_y) {
			viewedHeight = lr_camera_y - viewedY;
		} else {
			viewedHeight = lr_layer_y - viewedY;
		}

		Rectangle2D bounds = new Rectangle2D.Double(viewedX, viewedY,
				viewedWidth, viewedHeight);
		bounds = this.getCamera().viewToLocal(bounds);
		this.areaVisiblePNode.setBounds(bounds);

		// keep the birds eye view centered
		this.getCamera().animateViewToCenterBounds(drag_bounds, true, 0);

	}

}