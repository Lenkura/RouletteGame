package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import ausroulette.model.bet.BetType;
import ausroulette.view.AddNewBetDialog;

public class AddBetChooseBetTypeListener implements ActionListener {
	private AddNewBetDialog abd;

	public AddBetChooseBetTypeListener(AddNewBetDialog addNewBetDialog) {
		this.abd = addNewBetDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unchecked")
		String choice = ((JComboBox<BetType>) e.getSource()).getSelectedItem().toString();
		switch (choice) {
		case ("Zero"):
		case ("Red"):
		case ("Black"):
			abd.hideBetAdditionals();
			break;
		case ("2-way Split"):
			abd.showSplit2Bet();
			break;
		case ("4-way Split"):
			abd.showSplit4Bet();
			break;
		case ("Number"):
			abd.showNumberBet();
			break;
		}

	}

}
