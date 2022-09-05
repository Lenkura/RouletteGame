package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.BetType;
import ausroulette.model.wheel.PocketColor;
import ausroulette.view.AddNewBetDialog;

public class AddNewBet implements ActionListener {
	private GameEngine ge;
	private AddNewBetDialog j;
	private JSlider pointsSlider;
	private JSpinner numberBet;
	private JFormattedTextField split2Bet1;
	private JComboBox<String> split2BetChoice;
	private JFormattedTextField split4Bet;
	private JComboBox<Player> playerbox;
	private JComboBox<BetType> betTypes;

	public AddNewBet(GameEngine ge, AddNewBetDialog j, JComboBox<Player> playerbox, JComboBox<BetType> betTypes,
			JSlider pointsSlider, JSpinner numberBet, JFormattedTextField split2Bet1, JComboBox<String> split2BetChoice,
			JFormattedTextField split4Bet) {
		this.ge = ge;
		this.j = j;
		this.pointsSlider = pointsSlider;
		this.numberBet = numberBet;
		this.split2Bet1 = split2Bet1;
		this.split2BetChoice = split2BetChoice;
		this.split4Bet = split4Bet;
		this.playerbox = playerbox;
		this.betTypes = betTypes;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Player betPlayer = (Player) playerbox.getSelectedItem();
		String betType = betTypes.getSelectedItem().toString();
		int betAmount = pointsSlider.getValue();
		if (betPlayer.getAvailablePoints() < betAmount)
			JOptionPane.showMessageDialog(null, "Player has insufficient points to make bet");
		if (betAmount == 0)
			JOptionPane.showMessageDialog(null, "Bet amount can not be zero");
		else {
			switch (betType) {
			case ("Zero"):

				new Thread() {
					@Override
					public void run() {
						ge.placeNumberBet(betPlayer.getId(), betAmount, 0);
					}
				}.start();
				j.dispose();
				break;
			case ("Red"):
				new Thread() {
					@Override
					public void run() {
						ge.placeColorBet(betPlayer.getId(), betAmount, PocketColor.RED);
					}
				}.start();
				j.dispose();
				break;
			case ("Black"):

				new Thread() {
					@Override
					public void run() {
						ge.placeColorBet(betPlayer.getId(), betAmount, PocketColor.BLACK);
					}
				}.start();
				j.dispose();
				break;
			case ("2-way Split"):
				if (split2Bet1.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter a Pocket to bet on");
					break;
				}

				int baseNumber = (int) split2Bet1.getValue();
				int secondNumber = 0;
				if (split2BetChoice.getSelectedItem().equals("Horizontal")) {
					secondNumber = baseNumber + 1;
				}
				if (split2BetChoice.getSelectedItem().equals("Vertical")) {
					secondNumber = baseNumber + 3;
				}
				final int s = secondNumber;
				new Thread() {
					@Override
					public void run() {
						try {
							ge.placeSplitBet2(betPlayer.getId(), betAmount, baseNumber, s);
							j.dispose();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
				}.start();
				break;
			case ("4-way Split"):
				if (split4Bet.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Enter a Pocket to bet on");
					break;
				}
				new Thread() {
					@Override
					public void run() {
						try {
							ge.placeSplitBet4(betPlayer.getId(), betAmount, (int) split4Bet.getValue());
							j.dispose();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
				}.start();
				break;
			case ("Number"):
				int number = (int) numberBet.getValue();
				if (number < 1 || number > 36)
					JOptionPane.showMessageDialog(null, "Choose a Pocket from the shown table");
				else {
					new Thread() {
						@Override
						public void run() {
							ge.placeNumberBet(betPlayer.getId(), betAmount, (int) numberBet.getValue());
						}
					}.start();
					j.dispose();
				}
				break;
			}

		}
	}
}
