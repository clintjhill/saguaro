/**
 * 
 */
package com.stoneworks;

import java.io.FileNotFoundException;

/**
 * @author clinthill
 * 
 */
public class Project {

	private CutList cutList = null;

	private BrickCanvas brickCanvas = null;

	private String projectName = null;

	private String projectClient = null;

	private String filePath = null;

	/**
	 * 
	 */
	public Project() {
	}

	public void save(String path) {
		this.filePath = path;
		this.save();
	}

	public void save() {
		try {
			java.io.FileOutputStream fileOut = new java.io.FileOutputStream(
					this.filePath);
			com.thoughtworks.xstream.XStream xml = new com.thoughtworks.xstream.XStream();
			xml.registerConverter(new com.stoneworks.BrickCanvasConverter());
			xml.registerConverter(new com.stoneworks.BrickConverter());
			xml
					.registerConverter(new com.stoneworks.BackgroundImageConverter());
			xml.toXML(this, fileOut);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void open(String path) {
		try {
			this.filePath = path;
			java.io.FileInputStream fileIn = new java.io.FileInputStream(
					this.filePath);
			com.thoughtworks.xstream.XStream xml = new com.thoughtworks.xstream.XStream();
			xml.registerConverter(new com.stoneworks.BrickCanvasConverter());
			xml.registerConverter(new com.stoneworks.BrickConverter());
			xml
					.registerConverter(new com.stoneworks.BackgroundImageConverter());
			Project project = (Project) xml.fromXML(fileIn);
			this.brickCanvas = project.brickCanvas;
			this.cutList = project.cutList;
			this.projectClient = project.projectClient;
			this.projectName = project.projectName;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public CutList getCutList() {
		return this.cutList;
	}

	public void setCutList(CutList cl) {
		this.cutList = cl;
	}

	public String getProjectClient() {
		return this.projectClient;
	}

	public void setProjectClient(String projectClient) {
		this.projectClient = projectClient;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public BrickCanvas getBrickCanvas() {
		return this.brickCanvas;
	}

	public void setBrickCanvas(BrickCanvas brickCanvas) {
		this.brickCanvas = brickCanvas;
	}

	public boolean hasFilePath() {
		return this.filePath != null && this.filePath.length() > 0;
	}
}
