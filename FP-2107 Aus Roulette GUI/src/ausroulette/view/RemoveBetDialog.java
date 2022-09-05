package ausroulette.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import ausroulette.controller.FrameCloserListener;
import ausroulette.controller.RemoveBet;
import ausroulette.controller.RemoveBetPlayerBoxListener;
import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.Bet;

@SuppressWarnings("serial")
public class RemoveBetDialog extends AbstractJDialog {
	private JComboBox<Player> playerbox;
	private GridBagConstraints c = new GridBagConstraints();
	private GameEngine ge;
	private int tableRow;
	private ArrayList<JRadioButton> betTable = new ArrayList<JRadioButton>();
	private JButton removeBet;
	private JButton removeAllBet;
	private JButton cancel;

	public RemoveBetDialog(GameEngine ge) {
		this.ge = ge;
		int row = 0;
		setLayout(new GridBagLayout());
		JLabel heading = getStyledHeadingLabel("Remove a Bet");
		c.insets = new Insets(0, 0, 20, 0);
		c.ipadx = 20;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = row++;
		c.fill = GridBagConstraints.BOTH;
		add(heading, c);
		playerbox = getStyledPlayerJComboBox();
		for (Player p : ge.getAllPlayers()) {
			if (p.getBets().size() != 0)
				playerbox.addItem(p);
		}

		c.insets = new Insets(0, 0, 0, 0);
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row++;
		playerbox.addActionListener(new RemoveBetPlayerBoxListener(this, playerbox));
		add(playerbox, c);
		tableRow = row;

		this.updateBets((Player) playerbox.getSelectedItem());

		pack();
		this.setTitle("Remove Bet");
		setVisible(true);
	}

	public void updateBets(Player selectedPlayer) {
		if (betTable.size() != 0) {
			for (JRadioButton j : betTable)
				this.remove(j);
			this.remove(removeBet);
			this.remove(removeAllBet);
			this.remove(cancel);
		}
		int row = tableRow;
		c.insets = new Insets(0, 0, 0, 0);
		c.ipady = 0;
		c.gridwidth = 1;
		ButtonGroup btn = new ButtonGroup();
		for (Bet b : selectedPlayer.getBets()) {
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = row++;
			JRadioButton betInfo = new JRadioButton(b.toString().substring(3));
			System.out.println(b.toString());
			betInfo.setActionCommand(b.toString().substring(3));
			betInfo.setForeground(this.getForegroundColor());
			betInfo.setBackground(this.getBackgroundColor());
			btn.add(betInfo);
			betTable.add(betInfo);
			add(betInfo, c);
		}
		c.gridx = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 5, 0);
		c.gridy = row++;
		removeBet = getStyledButton("Remove Selected Bet");
		removeBet.addActionListener(new RemoveBet(ge, btn, this, playerbox));
		add(removeBet, c);

		c.gridx = 0;
		c.gridy = row++;
		removeAllBet = getStyledButton("Remove All Bets of this Player");
		removeAllBet.addActionListener(new RemoveBet(ge, this, playerbox));
		add(removeAllBet, c);

		c.ipady = 0;
		c.insets = new Insets(5, 0, 5, 5);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row++;
		cancel = getStyledButton("Cancel");
		cancel.addActionListener(new FrameCloserListener(this));
		add(cancel, c);

		this.revalidate();

	}

}
