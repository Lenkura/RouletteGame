package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.GameEngine;
import ausroulette.view.AddNewPlayerDialog;

public class OpenAddPlayerDialogListener implements ActionListener {
	private GameEngine ge;

	public OpenAddPlayerDialogListener(GameEngine ge) {
		this.ge = ge;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unused")
		AddNewPlayerDialog a = new AddNewPlayerDialog(ge);

	}

}
