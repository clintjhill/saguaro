/**
 * 
 */
package com.stoneworks;

import java.awt.Graphics2D;
import java.net.URL;

import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.util.PPaintContext;

/**
 * @author clinthill
 *
 */
public class BackgroundImage extends PImage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4015094150261832739L;
	
	private String filePath = null;
	/**
	 * 
	 */
	public BackgroundImage() {
		super();
	}


	/**
	 * @param fileName
	 */
	public BackgroundImage(String fileName) {
		super(fileName);
		this.filePath = fileName;
	}

	/**
	 * @param url
	 */
	public BackgroundImage(URL url) {
		super(url);
		this.filePath = url.getFile();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	/* (non-Javadoc)
	 * @see edu.umd.cs.piccolo.nodes.PImage#paint(edu.umd.cs.piccolo.util.PPaintContext)
	 */
	/**
	 * Do regular paint but include dimensions converted for feet and inches.
	 * @param pc
	 */
	@Override
	protected void paint(PPaintContext pc) {
		super.paint(pc);
		Graphics2D g2D = pc.getGraphics();
		g2D.setColor(java.awt.Color.black);
		String width = com.stoneworks.math.StandardMeasurement.createForFeet(this.getWidth()).toString();
		String height = com.stoneworks.math.StandardMeasurement.createForFeet(this.getHeight()).toString();
		g2D.drawString("Dimensions: " + width + " x " + height, 5, -8);
	}

}
