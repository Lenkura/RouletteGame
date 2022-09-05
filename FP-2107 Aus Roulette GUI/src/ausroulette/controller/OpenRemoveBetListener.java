package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.view.RemoveBetDialog;

public class OpenRemoveBetListener implements ActionListener {
	private GameEngine ge;

	public OpenRemoveBetListener(GameEngine ge) {
		this.ge = ge;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean noBets = true;
		for (Player p : ge.getAllPlayers()) {
			if (p.getBets().size() != 0)
				noBets = false;
		}
		if (noBets)
			JOptionPane.showMessageDialog(null, "There are no bets to remove");
		else {
			@SuppressWarnings("unused")
			RemoveBetDialog a = new RemoveBetDialog(ge);
		}
	}

}
