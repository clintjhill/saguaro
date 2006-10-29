/**
 * 
 */
package com.stoneworks;

import java.awt.Color;
import java.awt.geom.GeneralPath;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import edu.umd.cs.piccolo.util.PAffineTransform;

/**
 * 
 * @author clinthill
 * 
 */
public class BrickConverter implements
		com.thoughtworks.xstream.converters.Converter {

	/**
	 * 
	 */
	public BrickConverter() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.thoughtworks.xstream.converters.Converter#canConvert(java.lang.Class)
	 */
	public boolean canConvert(Class type) {
		return (type == Brick.class);
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
		Brick brick = (Brick) source;
		writer.startNode("path");
		context.convertAnother(brick.getPathReference());
		writer.endNode();
		writer.startNode("transform");
		context.convertAnother(brick.getTransform());
		writer.endNode();
		writer.startNode("color");
		context.convertAnother(brick.getColor());
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
		Brick brick = new Brick();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (reader.getNodeName().equals("path")) {
				brick.setPathTo((GeneralPath) context.convertAnother(reader,
						GeneralPath.class));
			}
			if (reader.getNodeName().equals("transform")) {
				PAffineTransform transform = (PAffineTransform) context
						.convertAnother(reader, PAffineTransform.class);
				brick.setTransform(transform);
			}
			if (reader.getNodeName().equals("color")) {
				brick.setColor((Color) context.convertAnother(reader,
						Color.class));
			}
			reader.moveUp();
		}
		return brick;
	}

}