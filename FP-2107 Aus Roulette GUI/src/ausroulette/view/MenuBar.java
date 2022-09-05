package ausroulette.view;

import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ausroulette.controller.AboutDialogListener;
import ausroulette.controller.BetRulesListener;
import ausroulette.controller.OpenAddBetDialogListener;
import ausroulette.controller.OpenAddPlayerDialogListener;
import ausroulette.controller.OpenAddPointsDialogListener;
import ausroulette.controller.OpenRemoveBetListener;
import ausroulette.controller.OpenRemovePlayerDialogListener;
import ausroulette.controller.OpenWheelSettingDialogListener;
import ausroulette.controller.SpinWheelToWin;
import ausroulette.controller.TestSpinWheel;
import ausroulette.model.GameEngine;
import ausroulette.model.Player;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements PropertyChangeListener {
	private GameEngine ge;
	private ArrayList<JMenuItem> disabledIfNoPlayers = new ArrayList<JMenuItem>();
	private ArrayList<JMenuItem> disabledIfSpinning = new ArrayList<JMenuItem>();
	private ArrayList<JMenuItem> disabledForNoBets = new ArrayList<JMenuItem>();

	public MenuBar(GameEngine ge, WheelSettings settings) {
		this.ge = ge;
		String[] playerSubHeadings = { "Add Player", "Remove Player", "Add Points" };
		String[] betSubHeadings = { "Add New Bet", "Cancel Bet"};
		String[] wheelSubHeadings = { "Spin Wheel", "Test Spin", "Wheel Settings" };
		String[] helpSubHeadings = { "Bet Rules", "About" };
		JMenu playerTab = new JMenu("Player");
		playerTab.setMnemonic(KeyEvent.VK_P);
		for (String psh : playerSubHeadings) {
			JMenuItem m = new JMenuItem(psh);
			if (psh.equals(playerSubHeadings[0])) {
				m.addActionListener(new OpenAddPlayerDialogListener(ge));
				m.setMnemonic(KeyEvent.VK_A);
				disabledIfSpinning.add(m);
			}
			if ((psh.equals(playerSubHeadings[1]))) {
				m.addActionListener(new OpenRemovePlayerDialogListener(ge));
				m.setMnemonic(KeyEvent.VK_R);
				disabledIfNoPlayers.add(m);
				disabledIfSpinning.add(m);
			}
			if ((psh.equals(playerSubHeadings[2]))) {
				m.addActionListener(new OpenAddPointsDialogListener(ge));
				m.setMnemonic(KeyEvent.VK_P);
				disabledIfNoPlayers.add(m);
				disabledIfSpinning.add(m);
			}
			playerTab.add(m);
		}

		JMenu betTab = new JMenu("Bets");
		betTab.setMnemonic(KeyEvent.VK_B);
		for (String bsh : betSubHeadings) {
			JMenuItem m = new JMenuItem(bsh);
			if (bsh.equals(betSubHeadings[0])) {
				m.addActionListener(new OpenAddBetDialogListener(ge));
				m.setMnemonic(KeyEvent.VK_A);
				disabledIfNoPlayers.add(m);
				disabledIfSpinning.add(m);
			}
			if (bsh.equals(betSubHeadings[1])) {
				m.addActionListener(new OpenRemoveBetListener(ge));
				m.setMnemonic(KeyEvent.VK_C);
				disabledIfNoPlayers.add(m);
				disabledIfSpinning.add(m);
				disabledForNoBets.add(m);
			}
			betTab.add(m);
		}

		JMenu wheeltab = new JMenu("Wheel");
		wheeltab.setMnemonic(KeyEvent.VK_W);
		for (String wsh : wheelSubHeadings) {
			JMenuItem m = new JMenuItem(wsh);
			if (wsh.equals(wheelSubHeadings[0])) {
				m.setMnemonic(KeyEvent.VK_S);
				m.addActionListener(new SpinWheelToWin(ge,settings));
				disabledIfSpinning.add(m);
				disabledIfNoPlayers.add(m);
				disabledForNoBets.add(m);
			}
			if (wsh.equals(wheelSubHeadings[1])) {
				m.setMnemonic(KeyEvent.VK_T);
				m.addActionListener(new TestSpinWheel(ge,settings));
				disabledIfSpinning.add(m);

			}
			if (wsh.equals(wheelSubHeadings[2])) {
				m.addActionListener(new OpenWheelSettingDialogListener(ge,settings));
				m.setMnemonic(KeyEvent.VK_E);
				disabledIfSpinning.add(m);
			}
			wheeltab.add(m);
		}

		JMenu helptab = new JMenu("Help");
		helptab.setMnemonic(KeyEvent.VK_H);
		for (String hsh : helpSubHeadings) {
			JMenuItem m = new JMenuItem(hsh);
			if (hsh.equals(helpSubHeadings[0])) {
				m.addActionListener(new BetRulesListener());
				m.setMnemonic(KeyEvent.VK_B);
			}
			if (hsh.equals(helpSubHeadings[1])) {
				m.addActionListener(new AboutDialogListener());
				m.setMnemonic(KeyEvent.VK_A);
			}
			helptab.add(m);
		}
		if (ge.getAllPlayers().size() == 0) {
			for (JMenuItem j : disabledIfNoPlayers)
				j.setEnabled(false);
		}
		add(playerTab);
		add(betTab);
		add(wheeltab);
		add(helptab);
		setVisible(true);
	}

	private void disabledIfNoPlayers() {
		if (ge.getAllPlayers().size() == 0) {
			for (JMenuItem j : disabledIfNoPlayers)
				j.setEnabled(false);
		}
	}

	private void disabledForNoBets() {
		boolean noBets = true;
		for (Player p : ge.getAllPlayers())
			if (p.getBets().size() != 0)
				noBets = false;
		if (noBets)
			for (JMenuItem j : disabledForNoBets) {
				j.setEnabled(false);
			}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_PLAYER.toString())) {
			if (!disabledIfNoPlayers.get(0).isEnabled())
				for (JMenuItem j : disabledIfNoPlayers)
					j.setEnabled(true);
			disabledForNoBets();
		}

		if (evt.getPropertyName().equals(PropertyChangeNames.REMOVE_PLAYER.toString())) {
			disabledIfNoPlayers();
		}

		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_BET.toString())) {
			if (!disabledForNoBets.get(0).isEnabled())
				for (JMenuItem j : disabledForNoBets) {
					j.setEnabled(true);
				}
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.CANCEL_BET.toString())) {
			disabledForNoBets();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_START.toString())) {
			for (JMenuItem j : disabledIfSpinning) {
				j.setEnabled(false);
			}
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_RESULT.toString())) {
			for (JMenuItem j : disabledIfSpinning) {
				j.setEnabled(true);
			}
			disabledIfNoPlayers();
			disabledForNoBets();

		}
	}

}
