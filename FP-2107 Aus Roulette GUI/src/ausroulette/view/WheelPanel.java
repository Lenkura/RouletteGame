package ausroulette.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import ausroulette.controller.WindowResizeListener;
import ausroulette.model.wheel.Wheel;

@SuppressWarnings("serial")
public class WheelPanel extends JPanel implements PropertyChangeListener {
	private Image wheel = new ImageIcon("images/wheel.png").getImage();
	private Image arrow = new ImageIcon("images/arrow.png").getImage();
	private int[] wheelNumbers = Wheel.POCKET_NUMBERS;
	private double wheelAngle;
	private double wheelspinadjustment = 0.7;
	boolean wheelSpinning = false;
	static final int arrowFlashDelay = 150;
	private WheelSettings settings;

	// Image
	// source:https://images.vexels.com/media/users/3/151205/isolated/preview/8857efb275fbf2435db40a222d05b1e6-roulette-wheel-icon-by-vexels.png
	public WheelPanel(WheelSettings settings) {
		this.settings = settings;
		setLayout(new GridLayout());
		this.addComponentListener(new WindowResizeListener(this));
		this.wheelAngle = 0;
		this.setBackground(new Color(66, 8, 57));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		int x;
		int y;
		int length;
		if (this.getWidth() < this.getHeight()) {
			// Centers the image vertically if the window is too tall
			y = (this.getHeight() - this.getWidth()) / 2;
			x = 0;
			length = this.getWidth();
		} else {
			// Centers the image horizontally if the window is too wide
			x = (this.getWidth() - this.getHeight()) / 2;
			y = 0;
			length = this.getHeight();
		}
		AffineTransform old;
		old = g2d.getTransform();
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(wheelAngle), x + length / 2,
				y + length / 2);

		g2d.setTransform(tx);
		g2d.drawImage(wheel, x, y, length, length, null);
		g2d.setTransform(old);
		if (wheelSpinning) {
			g.drawImage(arrow, (x + (length / 2)) * 95 / 100, 0, null);
		}
	}

	public void arrowFlash() {
		if (wheelSpinning) {
			wheelSpinning = false;
		} else {
			wheelSpinning = true;
		}
		this.repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_START.toString())) {
			wheelSpinning = true;
			int StartingNumber = settings.getStartingNumber();
			for (int i = 0; i < wheelNumbers.length; i++)
				if (StartingNumber == wheelNumbers[i])
					wheelAngle = 360 - (((360 / (wheelNumbers.length) + wheelspinadjustment)) * i);
			this.repaint();
		}

		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_TICK.toString())) {

			if (this.wheelAngle > 360)
				this.wheelAngle -= 360;
			this.wheelAngle += 360 - (360 / (wheelNumbers.length) + wheelspinadjustment);
			this.repaint();

		}

		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_RESULT.toString())) {
			this.wheelAngle += 360 - (360 / (wheelNumbers.length) + wheelspinadjustment);
			this.repaint();
			Timer timer = new Timer(arrowFlashDelay, new arrowFlashListener(this));
			timer.start();
		}

	}
}
