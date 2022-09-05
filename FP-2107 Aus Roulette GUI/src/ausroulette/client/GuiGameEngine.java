package ausroulette.client;

import javax.swing.SwingUtilities;

import ausroulette.model.GameEngine;
import ausroulette.model.GameEngineImpl;
import ausroulette.view.GuiCallback;
import ausroulette.view.LoggerCallback;
import ausroulette.view.RouletteMenu;

public class GuiGameEngine {

	public static void main(String[] args) {
		GameEngine ge = new GameEngineImpl();
//		ge.addPlayer("P1", "Bob", 100);
//		ge.addPlayer("P2", "Sarah", 50);
//		ge.addPlayer("P3", "Adam", 50);
//		ge.placeNumberBet("P1", 1, 18);
//		ge.placeSplitBet("P1", 1, new int[] { 28, 29, 31, 32 });
//		ge.placeSplitBet2("P1", 3, 1, 4);
//		ge.placeColorBet("P2", 10, PocketColor.BLACK);
//		ge.placeNumberBet("P2", 5, 0);
		GuiCallback g = new GuiCallback();
		ge.registerCallback(g);
		ge.registerCallback(new LoggerCallback());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				@SuppressWarnings("unused")
				RouletteMenu r = new RouletteMenu(ge, g);
			}
		});

	}

}

