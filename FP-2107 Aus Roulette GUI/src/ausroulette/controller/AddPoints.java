package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.view.AddPointsDialog;

public class AddPoints implements ActionListener {
	private GameEngine ge;
	private AddPointsDialog apd;
	private JComboBox<Player> playerbox;
	private JFormattedTextField pointsBox;

	public AddPoints(GameEngine ge, AddPointsDialog addPointsDialog, JComboBox<Player> playerbox,
			JFormattedTextField pointsBox) {
		this.ge = ge;
		this.apd = addPointsDialog;
		this.playerbox = playerbox;
		this.pointsBox = pointsBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean error = false;

		Player selectedPlayer = (Player) playerbox.getSelectedItem();
		int points = 0;
		String pointText = pointsBox.getText().replace(",", "");
		try {
			points = Integer.parseInt(pointText);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Enter a postive, whole number of points");
			error = true;
		}
		final Player p = selectedPlayer;
		final int pts = points;
		if (!error) {

			new Thread() {
				@Override
				public void run() {
					ge.addPoints(p.getId(), pts);
				}
			}.start();
			apd.dispose();
		}
	}

}
