package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import ausroulette.model.Player;
import ausroulette.view.RemoveBetDialog;

public class RemoveBetPlayerBoxListener implements ActionListener {
	private RemoveBetDialog rbd;
	private JComboBox<Player> playerbox;

	public RemoveBetPlayerBoxListener(RemoveBetDialog removeBetDialog, JComboBox<Player> playerbox) {
		this.playerbox = playerbox;
		this.rbd = removeBetDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Player selectedPlayer = (Player) playerbox.getSelectedItem();
		rbd.updateBets(selectedPlayer);
	}

}
