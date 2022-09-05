package ausroulette.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import ausroulette.model.GameEngine;

@SuppressWarnings("serial")
public class PlayerBetSidebar extends JPanel {
	private PlayerSummaryPanel p;
	private BettingInformationPanel b;

	public PlayerBetSidebar(GameEngine ge, GuiCallback g, RouletteMenu rouletteMenu) {
		p = new PlayerSummaryPanel(ge);
		b = new BettingInformationPanel(ge);
		setLayout(new GridLayout(0, 1));
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		JScrollPane scrollPane = new JScrollPane(b);
		p.setBorder(border);
		scrollPane.setBorder(border);
		add(p);
		add(scrollPane);
		rouletteMenu.addPropertyChangeListener(p);
		rouletteMenu.addPropertyChangeListener(b);
		g.addPropertyChangeListener(p);
		g.addPropertyChangeListener(b);
	}

//	public PlayerSummaryPanel getPlayerSummaryPanel() {
//		return p;
//	}

//	public void betAccepted(Player player) {
//		p.updatePlayerDetailFields(player);
//		b.updateBetInformation();
//	}

}
