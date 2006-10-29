/**
 * 
 */
package com.stoneworks.undo;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * @author clinthill
 * 
 */
public class UndoBrickCut extends AbstractUndoableEdit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4795202897969933611L;

	private java.awt.Shape originalShape = null;

	private java.awt.Shape newShape = null;

	private com.stoneworks.Brick brick = null;

	/**
	 * 
	 * @param b
	 * @param o
	 * @param n
	 */
	public UndoBrickCut(com.stoneworks.Brick b, java.awt.geom.Area o,
			java.awt.geom.Area n) {
		this.brick = b;
		this.originalShape = o;
		this.newShape = n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.undo.AbstractUndoableEdit#getPresentationName()
	 */
	@Override
	public String getPresentationName() {
		return "Brick cut";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.brick.setPathTo(this.newShape);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.brick.setPathTo(this.originalShape);
	}
}
