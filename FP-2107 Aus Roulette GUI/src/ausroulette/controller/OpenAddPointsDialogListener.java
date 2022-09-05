package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.GameEngine;
import ausroulette.view.AddPointsDialog;

public class OpenAddPointsDialogListener implements ActionListener {
	private GameEngine ge;

	public OpenAddPointsDialogListener(GameEngine ge) {
		this.ge = ge;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unused")
		AddPointsDialog a = new AddPointsDialog(ge);

	}

}
