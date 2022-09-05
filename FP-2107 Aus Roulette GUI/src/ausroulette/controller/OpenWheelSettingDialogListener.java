package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.GameEngine;
import ausroulette.view.WheelSettingDialog;
import ausroulette.view.WheelSettings;

public class OpenWheelSettingDialogListener implements ActionListener {
	private GameEngine ge;
	private WheelSettings settings;

	public OpenWheelSettingDialogListener(GameEngine ge, WheelSettings settings) {
		this.ge = ge;
		this.settings = settings;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unused")
		WheelSettingDialog w = new WheelSettingDialog(ge, settings);

	}

}
