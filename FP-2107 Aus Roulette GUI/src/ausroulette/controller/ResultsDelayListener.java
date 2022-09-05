package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.view.RouletteMenu;

public class ResultsDelayListener implements ActionListener {
	private RouletteMenu rouletteMenu;

	public ResultsDelayListener(RouletteMenu rouletteMenu) {
		this.rouletteMenu = rouletteMenu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		rouletteMenu.openResultsDialog();

	}

}
