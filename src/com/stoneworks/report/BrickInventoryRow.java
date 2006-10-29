package com.stoneworks.report;

/**
 * @author clinthill
 * 
 */
public class BrickInventoryRow {

	/**
	 * 
	 */
	public BrickInventoryRow(int count, String desc, com.stoneworks.Brick cut) {
		this.cutCount = count;
		this.cutDescription = desc;
		this.cut = cut;
	}

	public com.stoneworks.Brick getCut() {
		return this.cut;
	}

	public int getCutCount() {
		return this.cutCount;
	}

	public String getCutDescription() {
		return this.cutDescription;
	}

	public void setCut(com.stoneworks.Brick cut) {
		this.cut = cut;
	}

	public void setCutCount(int cutCount) {
		this.cutCount = cutCount;
	}

	public void setCutDescription(String cutDescription) {
		this.cutDescription = cutDescription;
	}

	private com.stoneworks.Brick cut = null;

	private int cutCount = 0;

	private String cutDescription = null;

}