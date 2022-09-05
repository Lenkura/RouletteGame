package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import ausroulette.model.Player;
import ausroulette.view.AddNewBetDialog;

public class AddBetPlayerBoxListener implements ActionListener {
	private AddNewBetDialog abd;

	public AddBetPlayerBoxListener(AddNewBetDialog addNewBetDialog) {
		this.abd = addNewBetDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Player selectedPlayer = (Player) ((JComboBox<?>) e.getSource()).getSelectedItem();
		if (selectedPlayer.getAvailablePoints() == 0)
			abd.updatePointsSlider(0, 0);
		else
			abd.updatePointsSlider(1, selectedPlayer.getAvailablePoints());
	}

}
