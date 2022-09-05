package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.view.AddNewBetDialog;

public class OpenAddBetDialogListener implements ActionListener {
	private GameEngine ge;

	public OpenAddBetDialogListener(GameEngine ge) {
		this.ge = ge;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean noPoints = true;
		for (Player p : ge.getAllPlayers()) {
			if (p.getAvailablePoints() > 0)
				noPoints = false;
		}
		if (ge.getAllPlayers().size() == 0) {
			JOptionPane.showMessageDialog(null, "There must be at least one player to place a bet");
		} else if (noPoints) {
			JOptionPane.showMessageDialog(null, "No players have points available");
		} else {
			@SuppressWarnings("unused")
			AddNewBetDialog a = new AddNewBetDialog(ge);

		}
	}

}
