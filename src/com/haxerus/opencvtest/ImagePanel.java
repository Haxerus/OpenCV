package com.haxerus.opencvtest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	public ImagePanel(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getPreferredSize().width, this.getPreferredSize().height, null);
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
