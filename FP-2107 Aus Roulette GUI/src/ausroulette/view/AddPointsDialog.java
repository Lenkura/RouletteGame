package ausroulette.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.NumberFormatter;

import ausroulette.controller.AddPoints;
import ausroulette.controller.FrameCloserListener;
import ausroulette.model.GameEngine;
import ausroulette.model.Player;

@SuppressWarnings("serial")
public class AddPointsDialog extends AbstractJDialog {
	private JComboBox<Player> playerbox;
	private JFormattedTextField pointsBox;

	public AddPointsDialog(GameEngine ge) {
		int row = 0;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel heading = getStyledHeadingLabel("Add Points");
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = row++;
		add(heading, c);

		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = row++;
		add(getStyledJLabel("Choose player and enter points to add"), c);

		playerbox = getStyledPlayerJComboBox();
		for (Player p : ge.getAllPlayers()) {
			playerbox.addItem(p);
		}
		c.insets = new Insets(5, 0, 5, 0);
		c.ipady = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = row++;
		add(playerbox, c);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setCommitsOnValidEdit(true);
		pointsBox = new JFormattedTextField(formatter);
		pointsBox.setPreferredSize(new Dimension(100, 20));
		c.ipady = 0;
		c.insets = new Insets(5, 0, 5, 0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = row++;
		add(pointsBox, c);

		c.ipady = 0;
		c.insets = new Insets(5, 50, 5, 0);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row;
		JButton addpoint = getStyledButton("Add Points");
		addpoint.addActionListener(new AddPoints(ge, this, playerbox, pointsBox));
		add(addpoint, c);
		c.ipady = 0;
		c.insets = new Insets(5, 50, 5, 0);
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = row++;
		JButton cancel = getStyledButton("Cancel");
		cancel.addActionListener(new FrameCloserListener(this));
		add(cancel, c);

		pack();
		this.setTitle("Add Points");
		setVisible(true);
	}

}
