package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

import ausroulette.model.GameEngine;
import ausroulette.view.AddNewPlayerDialog;
import ausroulette.view.PlayerIcon;

public class AddNewPlayer implements ActionListener {
	private ArrayList<JFormattedTextField> textfields;
	private static int playerIDCounter = 1;
	private GameEngine ge;
	private AddNewPlayerDialog anpd;
	private JComboBox<PlayerIcon> iconBox;

	public AddNewPlayer(GameEngine ge, AddNewPlayerDialog addNewPlayerDialog, ArrayList<JFormattedTextField> textfields,
			JComboBox<PlayerIcon> iconBox) {
		this.anpd = addNewPlayerDialog;
		this.textfields = textfields;
		this.ge = ge;
		this.iconBox = iconBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean error = false;
		String errorMessage = "";
		String name = textfields.get(0).getText();
		String points = textfields.get(1).getText().replace(",", "");
		if (name.trim().isEmpty())
			errorMessage += "Please enter a name\n";
		int initialPoints = -1;
		try {
			initialPoints = Integer.parseInt(points);
		} catch (NumberFormatException e1) {
			errorMessage += "Please enter an initial points value\n";
			error = true;
		}

		if (error == true)
			JOptionPane.showMessageDialog(anpd, errorMessage);
		else {
			String playerID = String.format("P%d", playerIDCounter);
			((PlayerIcon) iconBox.getSelectedItem()).setPlayerID(playerID);
			final Integer ip = initialPoints;
			new Thread() {
				@Override
				public void run() {
					ge.addPlayer(playerID, name, ip);
				}
			}.start();
			playerIDCounter++;
			anpd.dispose();

		}
	}

}
