package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JSpinner;

import ausroulette.view.WheelSettingDialog;
import ausroulette.view.WheelSettings;

public class SaveWheelSettings implements ActionListener {
	private ButtonGroup turnGroup;
	private ButtonGroup delayGroup;
	private WheelSettingDialog wsd;
	private JSpinner pocketNumber;
	private WheelSettings settings;

	public SaveWheelSettings(ButtonGroup turnGroup, ButtonGroup delayGroup, JSpinner pocketNumber,
			WheelSettingDialog wheelSettingDialog, WheelSettings settings) {
		this.turnGroup = turnGroup;
		this.delayGroup = delayGroup;
		this.pocketNumber = pocketNumber;
		this.wsd = wheelSettingDialog;
		this.settings = settings;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		settings.setTurnSetting(turnGroup.getSelection().getActionCommand());
		settings.setDelaySetting(delayGroup.getSelection().getActionCommand());
		settings.setStartingNumber((int) pocketNumber.getValue());
		this.wsd.dispose();
	}

}
