package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.Bet;
import ausroulette.view.RemoveBetDialog;

public class RemoveBet implements ActionListener {
	private GameEngine ge;
	private ButtonGroup btn;
	private RemoveBetDialog rbd;
	private JComboBox<Player> playerbox;

	public RemoveBet(GameEngine ge, ButtonGroup btn, RemoveBetDialog removeBetDialog, JComboBox<Player> playerbox) {
		this.ge = ge;
		this.btn = btn;
		this.rbd = removeBetDialog;
		this.playerbox = playerbox;
	}

	public RemoveBet(GameEngine ge, RemoveBetDialog removeBetDialog, JComboBox<Player> playerbox) {
		this.ge = ge;
		this.rbd = removeBetDialog;
		this.playerbox = playerbox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Bet betRemove = null;
		Player selectedPlayer = (Player) playerbox.getSelectedItem();
		if (((JButton) e.getSource()).getText().equals("Remove Selected Bet")) {
			String bet = btn.getSelection().getActionCommand();
			for (Bet b : selectedPlayer.getBets()) {
				if (bet.equals(b.toString().substring(3)))
					betRemove = b;
			}
			final Bet br = betRemove;
			int result = JOptionPane.showConfirmDialog(rbd, "Removing a bet cannot be undone, are you sure?",
					"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				new Thread() {
					@Override
					public void run() {
						ge.cancelBet(br);
					}
				}.start();

				rbd.dispose();
			}
		}
		if (((JButton) e.getSource()).getText().equals("Remove All Bets of this Player")) {
			ArrayList<Bet> tempBetList = new ArrayList<Bet>();
			int result = JOptionPane.showConfirmDialog(rbd,
					String.format("Removing all bets for %s, are you sure?", selectedPlayer.getName()), "Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				for (Bet b : selectedPlayer.getBets()) {
					tempBetList.add(b);
				}
				for (Bet b : tempBetList) {
					new Thread() {
						@Override
						public void run() {
							ge.cancelBet(b);
						}
					}.start();

				}
				rbd.dispose();
			}

		}
	}
}