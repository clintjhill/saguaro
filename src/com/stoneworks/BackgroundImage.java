/**
 * 
 */
package com.stoneworks;

import java.net.URL;

import edu.umd.cs.piccolo.nodes.PImage;

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

}
