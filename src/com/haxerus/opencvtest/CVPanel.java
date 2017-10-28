package com.haxerus.opencvtest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.crypto.dsig.CanonicalizationMethod;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class CVPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private final double MAX_VALUE = 359;
	
	private boolean running;
	private Thread cvThread;
	
	private ImagePanel panel;
	
	private SpinnerNumberModel numModel;
	private JSpinner hue, sat, val;
	
	private double h, s, v;
	
	private Mat frame;
	private VideoCapture camera;
	
	public CVPanel() {
		this.setPreferredSize(new Dimension(800, 600));
		
		initCV();
		initComponents();
		
		cvThread = new Thread(this, "CV Thread");
		cvThread.start();
	}
	
	@Override
	public void run() {
		if (camera.read(frame)) {
			running = true;
		} else {
			System.err.println("Camera Error");
		}
		
		while (running) {
			if (camera.read(frame)) {
				BufferedImage image = Algorithm.matToBufferedImage(frame);
				panel.setImage(image);
			}
			
			panel.repaint();
		}
		
		camera.release();
	}
	
	private void initCV() {
		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		 camera = new VideoCapture(0);
	}

	private void initComponents() {
		this.setLayout(new BorderLayout());
		
		panel = new ImagePanel(320, 240);
		
		numModel = new SpinnerNumberModel(MAX_VALUE, 0, MAX_VALUE, 1.0);
		
		hue = new JSpinner(numModel);
		hue.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner js = (JSpinner)e.getSource();
				h = (double)js.getValue();
			}
		});
		
		sat = new JSpinner(numModel);
		sat.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner js = (JSpinner)e.getSource();
				s = (double)js.getValue();
			}
		});

		val = new JSpinner(numModel);
		val.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner js = (JSpinner)e.getSource();
				v = (double)js.getValue();
			}
		});
		
		this.add(panel, BorderLayout.CENTER);
		this.add(hue, BorderLayout.PAGE_END);
		this.add(sat, BorderLayout.PAGE_END);
		this.add(val, BorderLayout.PAGE_END);
	}
}
