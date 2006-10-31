/**
 * 
 */
package com.stoneworks;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author clinthill
 * 
 */
public class BackgroundImageConverter implements Converter {

	/**
	 * 
	 */
	public BackgroundImageConverter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class type) {
		return (type == BackgroundImage.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object,
	 *      com.thoughtworks.xstream.io.HierarchicalStreamWriter,
	 *      com.thoughtworks.xstream.converters.MarshallingContext)
	 */
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		BackgroundImage image = (BackgroundImage) source;
		writer.startNode("imageFilePath");
		context.convertAnother(image.getFilePath());
		writer.endNode();
		writer.startNode("imageBounds");
		context.convertAnother(image.getBounds().getBounds());
		writer.endNode();
		writer.startNode("imageTransform");
		context.convertAnother(image.getTransform());
		writer.endNode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader,
	 *      com.thoughtworks.xstream.converters.UnmarshallingContext)
	 */
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		BackgroundImage image = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (reader.getNodeName().equals("imageFilePath")) {
				image = new BackgroundImage(reader.getValue());
			}
			if (image != null && reader.getNodeName().equals("imageBounds")) {
				image.setBounds((Rectangle) context.convertAnother(reader,
						Rectangle.class));
			}
			if (image != null && reader.getNodeName().equals("imageTransform")) {
				image.setTransform((AffineTransform) context.convertAnother(
						reader, AffineTransform.class));
			}
			reader.moveUp();
		}
		return image;
	}

}
