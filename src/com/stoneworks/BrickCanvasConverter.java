/**
 * 
 */
package com.stoneworks;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author clinthill
 *
 */
public class BrickCanvasConverter implements Converter {

	/**
	 * 
	 */
	public BrickCanvasConverter() {
	}

	/* (non-Javadoc)
	 * @see com.thoughtworks.xstream.converters.Converter#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class type) {
		return (type == BrickCanvas.class);
	}

	/* (non-Javadoc)
	 * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object, com.thoughtworks.xstream.io.HierarchicalStreamWriter, com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		BrickCanvas canvas = (BrickCanvas)source;
		writer.startNode("canvasBackgroundImage");
		context.convertAnother(canvas.getBackgroundImage());
		writer.endNode();
		writer.startNode("bricksOnCanvas");
		for(Object obj : canvas.getLayer().getChildrenReference()) {
			if(obj instanceof Brick) {
				writer.startNode(obj.getClass().getName());
				context.convertAnother((Brick)obj);
				writer.endNode();
			}
		}
		writer.endNode();
	}

	/* (non-Javadoc)
	 * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader, com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		BrickCanvas canvas = new BrickCanvas();
		while(reader.hasMoreChildren()) {
			reader.moveDown();
			if(reader.getNodeName().equals("canvasBackgroundImage")) {
				canvas.setBackgroundImage((BackgroundImage)context.convertAnother(reader, BackgroundImage.class));
			}
			if(reader.getNodeName().equals("bricksOnCanvas")) {
				while(reader.hasMoreChildren()) {
					reader.moveDown();
					if(reader.getNodeName().equals(Brick.class.getName())) {
						canvas.getLayer().addChild((Brick)context.convertAnother(reader, Brick.class));
					}
					reader.moveUp();
				}
			}
			reader.moveUp();
		}
		return canvas;
	}

}
