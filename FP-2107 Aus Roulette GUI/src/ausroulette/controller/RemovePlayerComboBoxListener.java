package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import ausroulette.model.Player;
import ausroulette.view.RemovePlayerDialog;

public class RemovePlayerComboBoxListener implements ActionListener {
	private RemovePlayerDialog rpd;
	private JComboBox<Player> playerbox;

	public RemovePlayerComboBoxListener(RemovePlayerDialog removePlayerDialog, JComboBox<Player> playerbox) {
		this.rpd = removePlayerDialog;
		this.playerbox = playerbox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		rpd.setSelectedPlayer((Player) playerbox.getSelectedItem());

	}

}
