package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.view.PlayerIcon;
import ausroulette.view.RemovePlayerDialog;

public class RemovePlayer implements ActionListener {
	private GameEngine ge;
	private JComboBox<Player> playerbox;
	private RemovePlayerDialog rpd;

	public RemovePlayer(GameEngine ge, RemovePlayerDialog removePlayerDialog, JComboBox<Player> playerbox) {
		this.ge = ge;
		this.playerbox = playerbox;
		this.rpd = removePlayerDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Player selectedPlayer = (Player) playerbox.getSelectedItem();
		if (selectedPlayer.getBets().size() != 0)
			JOptionPane.showMessageDialog(null, "Cannot remove player with bets");
		else {
			int result = JOptionPane.showConfirmDialog(rpd,
					String.format("Removing %s\nRemoving a player cannot be undone, are you sure?",
							((Player) playerbox.getSelectedItem()).getName()),
					"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				for (PlayerIcon p : PlayerIcon.values())
					if (p.getPlayerID().equals(selectedPlayer.getId()))
						p.setPlayerID("");
				new Thread() {
					@Override
					public void run() {
						ge.removePlayer(selectedPlayer.getId());
					}
				}.start();
				rpd.dispose();
			}
		}
	}

}
