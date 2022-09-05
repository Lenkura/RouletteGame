package ausroulette.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.Bet;

@SuppressWarnings("serial")
public class Statusbar extends JPanel implements PropertyChangeListener {
	private JLabel left;
	private JLabel right;
	private GameEngine ge;

	public Statusbar(GameEngine ge) {
		this.ge = ge;
		setLayout(new GridLayout(1, 0));
		left = new JLabel("-", SwingConstants.CENTER);
		right = new JLabel("-", SwingConstants.CENTER);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		left.setBorder(border);
		right.setBorder(border);
		add(left);
		add(right);
		this.setVisible(true);
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_PLAYER.toString())) {
			left.setText(String.format("%s has been added to the game", ((Player) evt.getNewValue()).getName()));
			updateRightText();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.REMOVE_PLAYER.toString())) {
			left.setText(String.format("%s has been removed from the game", ((Player) evt.getOldValue()).getName()));
			updateRightText();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_BET.toString())) {
			left.setText(String.format("%s bet has been placed", ((Bet) evt.getNewValue()).getBetType().toString()));
			updateRightText();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.CANCEL_BET.toString())) {
			left.setText(String.format("%s bet has been removed", ((Bet) evt.getOldValue()).getBetType().toString()));
			updateRightText();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.ADD_POINTS.toString())) {
			left.setText(String.format("%s has added %d points", ((Player) evt.getNewValue()).getName(),
					(int) evt.getOldValue()));
			updateRightText();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_START.toString())) {
			left.setText("Wheel Spinning");
		}

		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_HOUSE_RESULT.toString())) {
			left.setText("Spin Complete! Results Shown");
			updateRightText();
		}

	}

	private int netBetPointTotal() {
		int points = 0;
		for (Player p : ge.getAllPlayers())
			for (Bet b : p.getBets())
				points += b.getAmount();
		return points;
	}

	private int numberofBets() {
		int bets = 0;
		for (Player p : ge.getAllPlayers())
			bets += p.getBets().size();
		return bets;
	}

	private void updateRightText() {
		right.setText(String.format("Players:%d  Bets: %d  Points on the line: %d", ge.getAllPlayers().size(),
				numberofBets(), netBetPointTotal()));
	}

}
