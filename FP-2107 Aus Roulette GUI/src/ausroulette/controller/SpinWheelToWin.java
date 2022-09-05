package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.GameEngine;
import ausroulette.view.WheelSettings;

public class SpinWheelToWin implements ActionListener {
	private GameEngine ge;
	private WheelSettings settings;

	public SpinWheelToWin(GameEngine ge, WheelSettings settings) {
		this.ge = ge;
		this.settings = settings;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread() {
			@Override
			public void run() {
				ge.spinToWin(settings.getTurnNumber(), settings.getDelayNumber(), settings.getStartingNumber());
			}
		}.start();

	}

}
