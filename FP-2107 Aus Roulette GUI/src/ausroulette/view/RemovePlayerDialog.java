package ausroulette.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import ausroulette.controller.FrameCloserListener;
import ausroulette.controller.RemovePlayer;
import ausroulette.controller.RemovePlayerComboBoxListener;
import ausroulette.model.GameEngine;
import ausroulette.model.Player;

@SuppressWarnings("serial")
public class RemovePlayerDialog extends AbstractJDialog {
	private JComboBox<Player> playerbox;
	private int row = 0;
	private JLabel playerPoints;

	public RemovePlayerDialog(GameEngine ge) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel heading = getStyledHeadingLabel("Select Player to Remove");
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = row++;
		add(heading, c);

		JLabel playerHeading = getStyledJLabel("Player");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row;
		add(playerHeading, c);

		JLabel pointsHeading = getStyledJLabel("Points");
		c.gridx = 1;
		c.gridy = row++;
		add(pointsHeading, c);

		playerbox = getStyledPlayerJComboBox();
		for (Player p : ge.getAllPlayers()) {
			playerbox.addItem(p);
		}

		playerbox.addActionListener(new RemovePlayerComboBoxListener(this, playerbox));
		c.gridx = 0;
		c.gridy = row;
		add(playerbox, c);

		playerPoints = getStyledJLabel(String.valueOf(((Player) playerbox.getSelectedItem()).getPoints()));
		c.gridx = 1;
		c.gridy = row++;
		add(playerPoints, c);

		JButton add = getStyledButton("Remove Player");
		add.addActionListener(new RemovePlayer(ge, this, playerbox));
		c.gridx = 0;
		c.gridy = row;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 10, 0, 0);
		add(add, c);
		JButton cancel = getStyledButton("Cancel");
		cancel.addActionListener(new FrameCloserListener(this));
		c.gridx = 1;
		c.gridy = row++;
		add(cancel, c);

		pack();
		this.setTitle("Remove Player");
		setVisible(true);
	}

	public void setSelectedPlayer(Player player) {
		playerPoints.setText(String.valueOf(player.getPoints()));
	}

}
