package ausroulette.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.Timer;

import ausroulette.controller.ResultsDelayListener;
import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.Bet;

@SuppressWarnings("serial")
public class RouletteMenu extends JFrame implements PropertyChangeListener {
	private MenuBar menubar;
	private Toolbar toolbar;
	private Statusbar statusbar;
	private WheelBetTablePanel wheelBetTablePanel;
	private PlayerBetSidebar playerBetPanel;
	private Player selectedPlayer;
	private ArrayList<Bet> finalisedBets = new ArrayList<Bet>();
	private Map<Player, Integer> playerWonLoss = new HashMap<>();
	int houseLost;
	int houseWon;
	WheelSettings settings;

	public RouletteMenu(GameEngine ge, GuiCallback g) {
		settings = new WheelSettings();
		statusbar = new Statusbar(ge);
		wheelBetTablePanel = new WheelBetTablePanel(g, this, settings);
		playerBetPanel = new PlayerBetSidebar(ge, g, this);
		this.setLayout(new BorderLayout());
		menubar = new MenuBar(ge, settings);
		toolbar = new Toolbar(ge, this, settings);
		setJMenuBar(menubar);
		add(wheelBetTablePanel, BorderLayout.CENTER);
		add(toolbar, BorderLayout.NORTH);
		add(statusbar, BorderLayout.SOUTH);
		add(playerBetPanel, BorderLayout.WEST);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1300, 800);
		this.setMinimumSize(new Dimension(1000, 500));
		this.setTitle("AusRouletteGame");
		setVisible(true);
		g.addPropertyChangeListener(toolbar);
		g.addPropertyChangeListener(menubar);
		g.addPropertyChangeListener(statusbar);
		g.addPropertyChangeListener(this);
		this.addPropertyChangeListener(menubar);
		this.addPropertyChangeListener(toolbar);
	}

	public void clearStoredResults() {
		finalisedBets.clear();
		playerWonLoss.clear();
	}

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public void setselectedPlayer(Player newPlayer) {
		Player oldselectedPlayer = this.selectedPlayer;
		this.selectedPlayer = newPlayer;
		this.pcs.firePropertyChange(PropertyChangeNames.SELECTED_PLAYER.toString(), oldselectedPlayer, newPlayer);
	}

	public void openResultsDialog() {
		@SuppressWarnings("unused")
		ResultsDialog r = new ResultsDialog(this, finalisedBets, playerWonLoss, houseLost, houseWon);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertyChangeNames.BET_RESULT.toString())) {
			finalisedBets.add((Bet) evt.getNewValue());
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_PLAYER_TOTAL.toString())) {
			Player player = (Player) evt.getOldValue();
			int amount = (int) evt.getNewValue();
			playerWonLoss.put(player, amount);
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_HOUSE_RESULT.toString())) {
			houseLost = (int) evt.getOldValue();
			houseWon = (int) evt.getNewValue();

			int timesFlashed = 15;
			Timer timer = new Timer(timesFlashed * WheelPanel.arrowFlashDelay, new ResultsDelayListener(this));
			timer.start();
			timer.setRepeats(false);
		}

	}

}
