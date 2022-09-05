package ausroulette.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ausroulette.controller.OpenAddBetDialogListener;
import ausroulette.controller.OpenRemoveBetListener;
import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.Bet;
import ausroulette.model.bet.NumberBet;
import ausroulette.model.bet.SplitBet;
import ausroulette.model.bet.SplitBet.SplitPos;

@SuppressWarnings("serial")
public class BettingInformationPanel extends JPanel implements PropertyChangeListener {
	private final int TABLE_WIDTH = 5;
	private final int TABLE_PADDING = 20;
	private GameEngine ge;
	private JLabel betPlayer;
	private JLabel betType;
	private JLabel betPockets;
	private JLabel betAmount;
	private ArrayList<JLabel> betTable = new ArrayList<JLabel>();
	private ArrayList<Bet> betList = new ArrayList<Bet>();
	private int tableStartingRow;
	private GridBagConstraints c = new GridBagConstraints();
	private JButton add;
	private JButton cancel;
	private Player selectedPlayer;
	private Color foregroundColor = Color.WHITE;
	private Color backgroundColor = new Color(92, 35, 30);
	private Border border = BorderFactory.createLineBorder(foregroundColor, 1);
	
	public BettingInformationPanel(GameEngine ge) {
		this.ge = ge;
		this.setBackground(backgroundColor);
		int row = 0;
		setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = TABLE_WIDTH;
		c.gridx = 0;
		c.gridy = row;
		c.anchor = GridBagConstraints.WEST;
		JLabel heading = new JLabel("All Player Bets");
		heading.setFont(new Font("Verdana", Font.PLAIN, 25));
		heading.setForeground(foregroundColor);
		add(heading, c);
		row++;
		String[] columnNames = { " Player ", " Type ", " Pockets ", " Amount " };
		int col = 1;
		for (String cheading : columnNames) {
			c.gridx = col;
			c.gridy = row;
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.CENTER;
			JLabel h = new JLabel(cheading);
			h.setHorizontalAlignment(JLabel.CENTER);
			h.setBorder(border);
			h.setForeground(foregroundColor);
			add(h, c);
			col++;
		}
		row++;
		tableStartingRow = row;
		this.drawBetInformation();
	}

	private JLabel createBetJLabel(String text) {
		JLabel l = new JLabel(text);
		l.setBorder(border);
		return l;

	}

	public void drawBetInformation() {
		for (JLabel j : betTable)
			this.remove(j);
		if (add != null) {
			this.remove(add);
			this.remove(cancel);
		}
		this.revalidate();
		this.repaint();
		boolean noBets = true;
		int row = tableStartingRow;
		for (Player p : ge.getAllPlayers())
			if (p.getBets().size() != 0)
				noBets = false;
		if (noBets) {
			c.insets = new Insets(0, 0, 0, 0);
			c.gridwidth = 4;
			c.gridx = 1;
			c.gridy = row++;
			JLabel emptyTable = new JLabel("No Current Bets");
			emptyTable.setBorder(border);
			emptyTable.setHorizontalAlignment(JLabel.CENTER);
			emptyTable.setForeground(foregroundColor);
			betTable.add(emptyTable);
			add(emptyTable, c);
		} else {

			int col = 1;

			betList.clear();
			Color foreground;
			Color background;
			for (Player p : ge.getAllPlayers()) {
				for (Bet b : p.getBets()) {
					if (selectedPlayer == p) {
						foreground = Color.WHITE;
						background = Color.BLACK;
					} else {
						foreground = foregroundColor;
						background = backgroundColor;
					}
					c.gridwidth = 1;
					c.ipadx = TABLE_PADDING;
					c.gridx = col++;
					c.gridy = row;
					c.insets = new Insets(0, 0, 0, 0);
					betPlayer = this.createBetJLabel(b.getPlayer().getName());
					betPlayer.setHorizontalAlignment(JLabel.CENTER);
					betPlayer.setForeground(foreground);
					betPlayer.setBackground(background);
					betPlayer.setOpaque(true);
					add(betPlayer, c);
					betTable.add(betPlayer);
					c.ipadx = TABLE_PADDING;
					c.gridx = col++;
					c.gridy = row;
					betType = this.createBetJLabel(b.getBetType().toString());
					betType.setHorizontalAlignment(JLabel.CENTER);
					betType.setForeground(foreground);
					betType.setBackground(background);
					betType.setOpaque(true);
					add(betType, c);
					betTable.add(betType);
					String pockets = null;
					switch (b.getBetType()) {
					case BLACK:
						pockets = "Even";
						break;
					case RED:
						pockets = "Odd";
						break;
					case SPLIT_2:
						pockets = String.format("%d,%d", ((SplitBet) b).getFirstNumber(),
								((SplitBet) b).getLastNumber());
						break;
					case SPLIT_4:
						pockets = String.format("%d,%d,%d,%d", ((SplitBet) b).getFirstNumber(),
								((SplitBet) b).getNumber(SplitPos.SECOND), ((SplitBet) b).getNumber(SplitPos.THIRD),
								((SplitBet) b).getLastNumber());
						break;
					case NUMBER:
					case ZERO:
						pockets = String.valueOf(((NumberBet) b).getNumber());
						break;
					}
					betPockets = this.createBetJLabel(pockets);
					betPockets.setHorizontalAlignment(JLabel.CENTER);
					c.ipadx = TABLE_PADDING;
					c.gridx = col++;
					c.gridy = row;
					betPockets.setForeground(foreground);
					betPockets.setBackground(background);
					betPockets.setOpaque(true);
					add(betPockets, c);
					betTable.add(betPockets);
					betAmount = this.createBetJLabel(String.valueOf(b.getAmount()));
					betAmount.setHorizontalAlignment(JLabel.CENTER);
					c.ipadx = TABLE_PADDING;
					c.gridx = col++;
					c.gridy = row;
					betAmount.setForeground(foreground);
					betAmount.setBackground(background);
					betAmount.setOpaque(true);
					add(betAmount, c);
					betTable.add(betAmount);

					row++;
					col = 1;
				}
			}
		}
		c.gridx = 1;
		c.gridwidth = Math.round(TABLE_WIDTH / 2);
		c.gridy = ++row;
		c.insets = new Insets(10, 5, 0, 5);
		c.anchor = GridBagConstraints.CENTER;
		add = new JButton("Add Bets");
		add.addActionListener(new OpenAddBetDialogListener(ge));
		add.setForeground(backgroundColor);
		add.setBackground(foregroundColor);
		add(add, c);
		c.gridx = Math.round(TABLE_WIDTH / 2) + 1;
		c.gridy = row;
		c.anchor = GridBagConstraints.CENTER;
		cancel = new JButton("Cancel Bets");
		cancel.setForeground(backgroundColor);
		cancel.setBackground(foregroundColor);
		cancel.addActionListener(new OpenRemoveBetListener(ge));
		add(cancel, c);
		disableForNoBets();
		disableForNoPlayers();
		this.revalidate();
		this.repaint();

	}

	private void disableForNoBets() {
		boolean noBets = true;
		for (Player p : ge.getAllPlayers())
			if (p.getBets().size() != 0)
				noBets = false;
		if (noBets) {
			cancel.setVisible(false);
		}
	}

	private void disableForNoPlayers() {
		if (ge.getAllPlayers().size() == 0) {
			add.setVisible(false);
			cancel.setVisible(false);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_PLAYER.toString())) {
			if (!add.isVisible()) {
				add.setVisible(true);
			}

		}
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_BET.toString())
				|| evt.getPropertyName().equals(PropertyChangeNames.CANCEL_BET.toString())) {
			this.drawBetInformation();
			disableForNoBets();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SELECTED_PLAYER.toString())) {
			this.setSelectedPlayer((Player) evt.getNewValue());
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.REMOVE_PLAYER.toString())) {
			disableForNoPlayers();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_START.toString())) {
			add.setVisible(false);
			cancel.setVisible(false);

		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_RESULT.toString())) {
			add.setVisible(true);
			cancel.setVisible(true);
			disableForNoBets();
			disableForNoPlayers();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_HOUSE_RESULT.toString())) {
			drawBetInformation();
		}

	}

	public void setSelectedPlayer(Player player) {
		selectedPlayer = player;
		drawBetInformation();

	}
}
