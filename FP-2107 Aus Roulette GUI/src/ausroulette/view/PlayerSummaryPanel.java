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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;

@SuppressWarnings("serial")
public class PlayerSummaryPanel extends JPanel implements PropertyChangeListener {
	private GameEngine ge;
	private String[] columnNames = { " Icon ", "   Player   ", "Net Points", " Bet Total ", " Unused Points " };
	private int row = 0;
	private JLabel noPlayer;
	private GridBagConstraints c = new GridBagConstraints();
	private ArrayList<JLabel> betContent = new ArrayList<JLabel>();
	private int tableStartingRow;
	private Player selectedPlayer;
	private Color foregroundColor = Color.WHITE;
	private Color backgroundColor = new Color(66, 8, 28);

	public PlayerSummaryPanel(GameEngine ge) {
		this.ge = ge;
		setLayout(new GridBagLayout());
		this.setBackground(backgroundColor);

		c.ipady = 40;
		c.gridwidth = columnNames.length + 1;
		c.gridx = 0;
		c.gridy = row++;
		c.insets = new Insets(5, 5, 0, 5);
		c.anchor = GridBagConstraints.WEST;
		JLabel heading = new JLabel("Player Information Panel");
		heading.setFont(new Font("Verdana", Font.BOLD, 20));
		heading.setForeground(foregroundColor);
		add(heading, c);

		Border border = BorderFactory.createLineBorder(foregroundColor, 1);
		int col = 0;
		c.ipady = 5;
		c.insets = new Insets(0, 0, 0, 0);
		for (String l : columnNames) {
			c.gridx = col;
			c.gridy = row;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER;
			JLabel h = new JLabel(l);
			h.setForeground(foregroundColor);
			h.setHorizontalAlignment(JLabel.CENTER);
			h.setBorder(border);
			add(h, c);
			col++;
		}
		col = 0;
		row++;
		c.gridwidth = columnNames.length;
		c.gridx = col;
		c.gridy = row;
		noPlayer = new JLabel("No Players");
		noPlayer.setHorizontalAlignment(JLabel.CENTER);
		noPlayer.setBorder(border);
		noPlayer.setForeground(foregroundColor);
		add(noPlayer, c);
		tableStartingRow = row;
		this.updatePlayerSummary();
	}

	public void addPlayer(Player p) {
		noPlayer.setVisible(false);
		int col = 0;
		JLabel contentLabel = null;
		Border border = BorderFactory.createLineBorder(foregroundColor, 1);
		Color foreground;
		Color background;
		if (selectedPlayer == p) {
			foreground = Color.WHITE;
			background = Color.BLACK;
		} else {
			foreground = foregroundColor;
			background = backgroundColor;
		}
		for (int i = 0; i < columnNames.length; i++) {
			c.gridx = col++;
			c.gridy = row;
			c.gridwidth = 1;
			switch (i) {
			case 0:
				ImageIcon playerIcon = null;
				for (PlayerIcon pi : PlayerIcon.values())
					if (pi.getPlayerID().equals(p.getId()))
						playerIcon = pi.getIcon();
				contentLabel = new JLabel(playerIcon);
				break;
			case 1:
				contentLabel = new JLabel(p.getName());
				break;
			case 2:
				contentLabel = new JLabel(String.valueOf(p.getPoints()));
				break;
			case 3:
				contentLabel = new JLabel(String.valueOf(p.getCurrentBetTotal()));
				break;
			case 4:
				contentLabel = new JLabel(String.valueOf(p.getAvailablePoints()));
				break;
			}
			contentLabel.setBorder(border);
			contentLabel.setHorizontalAlignment(JLabel.CENTER);
			contentLabel.setForeground(foreground);
			contentLabel.setBackground(background);
			contentLabel.setOpaque(true);
			betContent.add(contentLabel);
			add(contentLabel, c);
		}
		row++;
	}

	public void updatePlayerSummary() {
		for (JLabel j : betContent)
			this.remove(j);
		if (ge.getAllPlayers().size() == 0) {
			noPlayer.setVisible(true);
		} else {
			noPlayer.setVisible(false);
			row = tableStartingRow;
			for (Player p : ge.getAllPlayers()) {
				this.addPlayer(p);
			}

		}
		this.revalidate();
		this.repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_PLAYER.toString())) {
			addPlayer((Player) evt.getNewValue());
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_BET.toString())
				|| evt.getPropertyName().equals(PropertyChangeNames.CANCEL_BET.toString())
				|| evt.getPropertyName().equals(PropertyChangeNames.ADD_POINTS.toString())
				|| evt.getPropertyName().equals(PropertyChangeNames.REMOVE_PLAYER.toString())
				|| evt.getPropertyName().equals(PropertyChangeNames.SPIN_HOUSE_RESULT.toString())) {
			this.updatePlayerSummary();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SELECTED_PLAYER.toString())) {
			this.setSelectedPlayer((Player) evt.getNewValue());
		}
	}

	private void setSelectedPlayer(Player player) {
		this.selectedPlayer = player;
		this.updatePlayerSummary();

	}

}
