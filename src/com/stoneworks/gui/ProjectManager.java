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
		this.initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(210, 400);
		this.setPreferredSize(new Dimension(210, 400));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.getProjectPanel(), null);
		this.add(this.getImagePanel(), null);
	}

	/**
	 * Sets the name of the client to the proper text field
	 * 
	 * @param n
	 */
	public void setClientName(String n) {
		this.clientTextField.setText(n);
	}

	/**
	 * Returns the name of the client from the Client Text Field
	 * 
	 * @return
	 */
	public String getClientName() {
		return this.clientTextField.getText();
	}

	/**
	 * Sets the name of the project to the proper text field
	 * 
	 * @param n
	 */
	public void setProjectName(String n) {
		this.nameTextField.setText(n);
	}

	/**
	 * Returns the name of the Project from the Name Text Field
	 * 
	 * @return
	 */
	public String getProjectName() {
		return this.nameTextField.getText();
	}

	/**
	 * This sets the BrickCanvas which is used to apply a background image
	 * 
	 * @param c
	 */
	public void setBrickCanvas(BrickCanvas c) {
		this.brickCanvas = c;
		this.getImageCanvas().getLayer().removeAllChildren();
		this.getImageCanvas().getLayer().addChild(
				(BackgroundImage) this.brickCanvas.getBackgroundImage().clone());
	}

	/**
	 * This method initializes projectPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getProjectPanel() {
		if (this.projectPanel == null) {
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
			this.clientLabel = new JLabel();
			this.clientLabel.setText("Client:");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			this.nameLabel = new JLabel();
			this.nameLabel.setText("Name:");
			this.projectPanel = new JPanel();
			this.projectPanel.setLayout(new GridBagLayout());
			this.projectPanel.setBorder(BorderFactory.createTitledBorder(null,
					"Project Information", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			this.projectPanel.add(this.nameLabel, gridBagConstraints);
			this.projectPanel.add(this.clientLabel, gridBagConstraints1);
			this.projectPanel.add(this.getNameTextField(), nameTextFieldConstraints);
			this.projectPanel.add(this.getClientTextField(), clientTextFieldConstraints);
		}
		return this.projectPanel;
	}

	/**
	 * This method initializes imagePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getImagePanel() {
		if (this.imagePanel == null) {
			GridBagConstraints imageTransparencyConstraints = new GridBagConstraints();
			imageTransparencyConstraints.fill = GridBagConstraints.HORIZONTAL;
			imageTransparencyConstraints.gridy = 2;
			imageTransparencyConstraints.weightx = 1.0D;
			imageTransparencyConstraints.gridx = 0;
			GridBagConstraints imageCanvasConstraints = new GridBagConstraints();
			imageCanvasConstraints.gridx = 0;
			imageCanvasConstraints.gridy = 0;
			imageCanvasConstraints.insets = new Insets(10, 10, 10, 10);
			GridBagConstraints imageButtonConstraints = new GridBagConstraints();
			imageButtonConstraints.gridx = 0;
			imageButtonConstraints.gridy = 1;
			this.imagePanel = new JPanel();
			this.imagePanel.setLayout(new GridBagLayout());
			this.imagePanel.setBorder(BorderFactory.createTitledBorder(null,
					"Background Image", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			this.imagePanel.add(this.getImageCanvas(), imageCanvasConstraints);
			this.imagePanel.add(this.getImageButton(), imageButtonConstraints);
			this.imagePanel
					.add(this.getImageTransparency(), imageTransparencyConstraints);
		}
		return this.imagePanel;
	}

	private PCanvas getImageCanvas() {
		if (this.imageCanvas == null) {
			this.imageCanvas = new PCanvas();
			this.imageCanvas.setPreferredSize(new Dimension(120, 120));
			this.imageCanvas.setSize(new Dimension(120, 120));
		}
		return this.imageCanvas;
	}

	/**
	 * This method initializes nameTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getNameTextField() {
		if (this.nameTextField == null) {
			this.nameTextField = new JTextField();
		}
		return this.nameTextField;
	}

	/**
	 * This method initializes clientTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getClientTextField() {
		if (this.clientTextField == null) {
			this.clientTextField = new JTextField();
		}
		return this.clientTextField;
	}

	/**
	 * This method initializes imageButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getImageButton() {
		if (this.imageButton == null) {
			this.imageButton = new JButton();
			this.imageButton.setText("Choose...");
			this.imageButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Thread imageFile = new Thread() {
						@Override
						public void run() {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JFileChooser chooser = new JFileChooser();
									int option = chooser.showDialog(
											ProjectManager.this,
											"Choose Image File");
									if (option == JFileChooser.APPROVE_OPTION) {
										BackgroundImage image = new BackgroundImage(
												chooser.getSelectedFile()
														.getPath());
										image.setBounds(0.0, 0.0, 120.0, 120.0);
										ProjectManager.this.getImageCanvas().getLayer()
												.addChild(
														(BackgroundImage) image
																.clone());
										ProjectManager.this.brickCanvas.setBackgroundImage(image);
									}
								}
							});
						}
					};
					imageFile.start();
				}
			});
		}
		return this.imageButton;
	}

	/**
	 * This method initializes imageTransparency
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getImageTransparency() {
		if (this.imageTransparency == null) {
			this.imageTransparency = new JSlider();
			this.imageTransparency.setMajorTickSpacing(5);
			this.imageTransparency.setValue(100);
			this.imageTransparency.setPaintTicks(true);
			this.imageTransparency
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							float transparency = ProjectManager.this.imageTransparency.getValue() * .01F;
							ProjectManager.this.brickCanvas.getBackgroundImage().setTransparency(
									transparency);
						}
					});
		}
		return this.imageTransparency;
	}

}
