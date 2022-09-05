package ausroulette.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class arrowFlashListener implements ActionListener {
	private WheelPanel wheelPanel;
	private final int TIMES_FLASHED = 15;
	private int counter = 0;

	public arrowFlashListener(WheelPanel wheelPanel) {
		this.wheelPanel = wheelPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (counter < TIMES_FLASHED) {
			wheelPanel.arrowFlash();
			counter++;
		} else
			((Timer) e.getSource()).stop();

	}

}
