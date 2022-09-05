package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ausroulette.model.GameEngine;
import ausroulette.view.RemovePlayerDialog;

public class OpenRemovePlayerDialogListener implements ActionListener {
	private GameEngine ge;

	public OpenRemovePlayerDialogListener(GameEngine ge) {
		this.ge = ge;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (ge.getAllPlayers().size() == 0) {
			JOptionPane.showMessageDialog(null, "There are no players to remove");
		} else {
			@SuppressWarnings("unused")
			RemovePlayerDialog a = new RemovePlayerDialog(ge);

		}
	}
}
