package ausroulette.view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import ausroulette.controller.OpenAddBetDialogListener;
import ausroulette.controller.OpenAddPlayerDialogListener;
import ausroulette.controller.OpenAddPointsDialogListener;
import ausroulette.controller.OpenRemoveBetListener;
import ausroulette.controller.OpenRemovePlayerDialogListener;
import ausroulette.controller.SpinWheelToWin;
import ausroulette.controller.toolbarComboBoxListener;
import ausroulette.model.GameEngine;
import ausroulette.model.Player;

@SuppressWarnings("serial")
public class Toolbar extends JToolBar implements PropertyChangeListener {
	private GameEngine ge;
	private JComboBox<Player> playerbox;
	private ArrayList<JButton> disabledIfNoPlayers = new ArrayList<JButton>();
	private ArrayList<JButton> disabledForNoBets = new ArrayList<JButton>();
	private ArrayList<JButton> disabledIfSpinning = new ArrayList<JButton>();

	public Toolbar(GameEngine ge, RouletteMenu rouletteMenu, WheelSettings settings) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.ge = ge;
		playerbox = new JComboBox<Player>();
		ListCellRendererPlayer plcr = new ListCellRendererPlayer();
		playerbox.setRenderer(plcr);
		if (ge.getAllPlayers().size() == 0)
			playerbox.setVisible(false);

		playerbox.setFont(new Font("Verdana", Font.PLAIN, 25));
		playerbox.addActionListener(new toolbarComboBoxListener(rouletteMenu, playerbox));
		add((playerbox));

		JButton addPlayer = new JButton(new ImageIcon("FP-2107 Aus Roulette GUI/images/addplayericon.png"));
		// Sourcec:
		// https://cdn2.iconfinder.com/data/icons/user-profile-1/100/player-profile-add-512.png
		addPlayer.addActionListener(new OpenAddPlayerDialogListener(ge));
		addPlayer.setToolTipText("Add a new player");
		disabledIfSpinning.add(addPlayer);
		add(addPlayer);

		JButton addPoints = new JButton(new ImageIcon("FP-2107 Aus Roulette GUI/images/addpointsicon.png"));
		// Source:
		// https://www.flaticon.com/svg/vstatic/svg/4291/4291393.svg?token=exp=1620471787~hmac=46fa51a9d3eaa1cb5ef8d17102d27c81
		addPoints.addActionListener(new OpenAddPointsDialogListener(ge));
		addPoints.setToolTipText("Add Points");
		disabledIfNoPlayers.add(addPoints);
		disabledIfSpinning.add(addPoints);
		add(addPoints);

		JButton addBets = new JButton(new ImageIcon("FP-2107 Aus Roulette GUI/images/addbeticon.png"));
		// Source:
		// https://cdn1.iconfinder.com/data/icons/casino-poker/100/Casino-13-512.png
		addBets.addActionListener(new OpenAddBetDialogListener(ge));
		addBets.setToolTipText("Add new Bet");
		disabledIfNoPlayers.add(addBets);
		disabledIfSpinning.add(addBets);
		add(addBets);

		JButton removeBet = new JButton(new ImageIcon("FP-2107 Aus Roulette GUI/images/removebeticon.png"));
		removeBet.setToolTipText("Remove Bet");
		removeBet.addActionListener(new OpenRemoveBetListener(ge));
		disabledIfNoPlayers.add(removeBet);
		disabledForNoBets.add(removeBet);
		disabledIfSpinning.add(removeBet);
		add(removeBet);

		JButton cashOut = new JButton(new ImageIcon("FP-2107 Aus Roulette GUI/images/cashout.png"));
		cashOut.setToolTipText("Remove Player");
		cashOut.addActionListener(new OpenRemovePlayerDialogListener(ge));
		disabledIfNoPlayers.add(cashOut);
		disabledIfSpinning.add(cashOut);
		add(cashOut);

		JButton spinWheel = new JButton(new ImageIcon("FP-2107 Aus Roulette GUI/images/wheelicon.png"));
		spinWheel.setToolTipText("Spin wheel");
		spinWheel.addActionListener(new SpinWheelToWin(ge, settings));
		add(spinWheel);
		disabledIfNoPlayers.add(spinWheel);
		disabledForNoBets.add(spinWheel);
		disabledIfSpinning.add(spinWheel);

		if (ge.getAllPlayers().size() == 0) {
			for (JButton j : disabledIfNoPlayers)
				j.setEnabled(false);
			for (JButton j : disabledForNoBets) {
				j.setEnabled(false);
			}
		}
	}

	private void disabledForNoBets() {
		boolean noBets = true;
		for (Player p : ge.getAllPlayers())
			if (p.getBets().size() != 0)
				noBets = false;
		if (noBets)
			for (JButton j : disabledForNoBets) {
				j.setEnabled(false);
			}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_PLAYER.toString())) {
			playerbox.addItem((Player) evt.getNewValue());
			if (ge.getAllPlayers().size() > 0) {
				playerbox.setVisible(true);
				if (!disabledIfNoPlayers.get(0).isEnabled())
					for (JButton j : disabledIfNoPlayers)
						j.setEnabled(true);
			}
			disabledForNoBets();
		}

		if (evt.getPropertyName().equals(PropertyChangeNames.REMOVE_PLAYER.toString())) {
			if (ge.getAllPlayers().size() == 0) {
				playerbox.setVisible(false);
				playerbox.removeAllItems();
				for (JButton j : disabledIfNoPlayers)
					j.setEnabled(false);
			} else
				playerbox.removeItem((Player) evt.getOldValue());
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_BET.toString())) {
			if (!disabledForNoBets.get(0).isEnabled())
				for (JButton j : disabledForNoBets) {
					j.setEnabled(true);
				}
		}

		if (evt.getPropertyName().equals(PropertyChangeNames.CANCEL_BET.toString())) {
			disabledForNoBets();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_START.toString())) {
			for (JButton j : disabledIfSpinning) {
				j.setEnabled(false);
			}
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_RESULT.toString())) {
			for (JButton j : disabledIfSpinning) {
				j.setEnabled(true);
			}
			disabledForNoBets();
			if (ge.getAllPlayers().size() == 0) {
				for (JButton j : disabledIfNoPlayers)
					j.setEnabled(false);
			}
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_HOUSE_RESULT.toString())) {
			disabledForNoBets();
		}
	}
}
