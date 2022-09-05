package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import ausroulette.model.Player;
import ausroulette.view.RouletteMenu;

public class toolbarComboBoxListener implements ActionListener {
	private JComboBox<Player> playerbox;
	private RouletteMenu rouletteMenu;

	public toolbarComboBoxListener(RouletteMenu rouletteMenu, JComboBox<Player> playerbox) {
		this.playerbox = playerbox;
		this.rouletteMenu = rouletteMenu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		rouletteMenu.setselectedPlayer((Player) playerbox.getSelectedItem());
	}

}
