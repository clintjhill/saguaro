/**
 * 
 */
package com.stoneworks.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import com.stoneworks.BackgroundImage;
import com.stoneworks.BrickCanvas;

import edu.umd.cs.piccolo.PCanvas;

import javax.swing.JSlider;

/**
 * @author clinthill
 * 
 */
public class ProjectManager extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761477553834751791L;

	private JPanel projectPanel = null;

	private JPanel imagePanel = null;

	private JLabel nameLabel = null;

	private JLabel clientLabel = null;

	private JTextField nameTextField = null;

	private JTextField clientTextField = null;

	private JButton imageButton = null;
	
	private PCanvas imageCanvas = null;
	
	private BrickCanvas brickCanvas = null;

	private JSlider imageTransparency = null;
	
	/**
	 * 
	 */
	public ProjectManager() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(210, 400);
		this.setPreferredSize(new Dimension(210,400));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getProjectPanel(), null);
		this.add(getImagePanel(), null);
	}

	/**
	 * Sets the name of the client to the proper text field
	 * @param n
	 */
	public void setClientName(String n) {
		this.clientTextField.setText(n);
	}
	/**
	 * Returns the name of the client from the Client Text Field
	 * @return
	 */
	public String getClientName() {
		return this.clientTextField.getText();
	}
	/**
	 * Sets the name of the project to the proper text field
	 * @param n
	 */
	public void setProjectName(String n) {
		this.nameTextField.setText(n);
	}
	/**
	 * Returns the name of the Project from the Name Text Field
	 * @return
	 */
	public String getProjectName() {
		return this.nameTextField.getText();
	}
	/**
	 * This sets the BrickCanvas which is used to apply a background image
	 * @param c
	 */
	public void setBrickCanvas(BrickCanvas c) {
		this.brickCanvas = c;
		getImageCanvas().getLayer().removeAllChildren();
		getImageCanvas().getLayer().addChild((BackgroundImage)brickCanvas.getBackgroundImage().clone());
	}
	
	/**
	 * This method initializes projectPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getProjectPanel() {
		if (projectPanel == null) {
			Insets textFieldInsets = new Insets(5, 3, 5, 3);
			GridBagConstraints clientTextFieldConstraints = new GridBagConstraints();
			clientTextFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
			clientTextFieldConstraints.gridy = 1;
			clientTextFieldConstraints.weightx = 1.0;
			clientTextFieldConstraints.gridx = 1;
			clientTextFieldConstraints.insets = textFieldInsets;
			GridBagConstraints nameTextFieldConstraints = new GridBagConstraints();
			nameTextFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
			nameTextFieldConstraints.gridy = 0;
			nameTextFieldConstraints.weightx = 1.0;
			nameTextFieldConstraints.gridheight = 1;
			nameTextFieldConstraints.gridx = 1;
			nameTextFieldConstraints.insets = textFieldInsets;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			clientLabel = new JLabel();
			clientLabel.setText("Client:");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			nameLabel = new JLabel();
			nameLabel.setText("Name:");
			projectPanel = new JPanel();
			projectPanel.setLayout(new GridBagLayout());
			projectPanel.setBorder(BorderFactory.createTitledBorder(null, "Project Information", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			projectPanel.add(nameLabel, gridBagConstraints);
			projectPanel.add(clientLabel, gridBagConstraints1);
			projectPanel.add(getNameTextField(), nameTextFieldConstraints);
			projectPanel.add(getClientTextField(), clientTextFieldConstraints);
		}
		return projectPanel;
	}

	/**
	 * This method initializes imagePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getImagePanel() {
		if (imagePanel == null) {
			GridBagConstraints imageTransparencyConstraints = new GridBagConstraints();
			imageTransparencyConstraints.fill = GridBagConstraints.HORIZONTAL;
			imageTransparencyConstraints.gridy = 2;
			imageTransparencyConstraints.weightx = 1.0D;
			imageTransparencyConstraints.gridx = 0;
			GridBagConstraints imageCanvasConstraints = new GridBagConstraints();
			imageCanvasConstraints.gridx = 0;
			imageCanvasConstraints.gridy = 0;
			imageCanvasConstraints.insets = new Insets(10,10,10,10);
			GridBagConstraints imageButtonConstraints = new GridBagConstraints();
			imageButtonConstraints.gridx = 0;
			imageButtonConstraints.gridy = 1;
			imagePanel = new JPanel();
			imagePanel.setLayout(new GridBagLayout());
			imagePanel.setBorder(BorderFactory.createTitledBorder(null, "Background Image", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			imagePanel.add(getImageCanvas(), imageCanvasConstraints);
			imagePanel.add(getImageButton(), imageButtonConstraints);
			imagePanel.add(getImageTransparency(), imageTransparencyConstraints);
		}
		return imagePanel;
	}

	private PCanvas getImageCanvas() {
		if(imageCanvas == null) {
			imageCanvas = new PCanvas();
			imageCanvas.setPreferredSize(new Dimension(120,120));
			imageCanvas.setSize(new Dimension(120,120));
		}
		return imageCanvas;
	}
	/**
	 * This method initializes nameTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new JTextField();
		}
		return nameTextField;
	}

	/**
	 * This method initializes clientTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getClientTextField() {
		if (clientTextField == null) {
			clientTextField = new JTextField();
		}
		return clientTextField;
	}

	/**
	 * This method initializes imageButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getImageButton() {
		if (imageButton == null) {
			imageButton = new JButton();
			imageButton.setText("Choose...");
			imageButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Thread imageFile = new Thread() {
						public void run() {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JFileChooser chooser = new JFileChooser();
									int option = chooser.showDialog(
											ProjectManager.this,
											"Choose Image File");
									if (option == JFileChooser.APPROVE_OPTION) {
										BackgroundImage image = new BackgroundImage(chooser.getSelectedFile().getPath());
										image.setBounds(0.0,0.0,120.0,120.0);
										getImageCanvas().getLayer().addChild((BackgroundImage)image.clone());
										brickCanvas.setBackgroundImage(image);
									}
								}
							});
						}
					};
					imageFile.start();
				}
			});
		}
		return imageButton;
	}

	/**
	 * This method initializes imageTransparency	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getImageTransparency() {
		if (imageTransparency == null) {
			imageTransparency = new JSlider();
			imageTransparency.setMajorTickSpacing(5);
			imageTransparency.setValue(100);
			imageTransparency.setPaintTicks(true);
			imageTransparency.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					float transparency = imageTransparency.getValue()*.01F;
					brickCanvas.getBackgroundImage().setTransparency(transparency);
				}
			});
		}
		return imageTransparency;
	}

}
