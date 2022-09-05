package ausroulette.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import ausroulette.controller.AddNewPlayer;
import ausroulette.controller.FrameCloserListener;
import ausroulette.model.GameEngine;

@SuppressWarnings("serial")
public class AddNewPlayerDialog extends AbstractJDialog {

	private ArrayList<JFormattedTextField> textfields = new ArrayList<JFormattedTextField>();;
	private JComboBox<PlayerIcon> iconBox;

	public AddNewPlayerDialog(GameEngine ge) {
		String[] labels = { "Enter Player Name ", "Enter Initial Points" };
		int fields = labels.length;
		int row = 0;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel heading = getStyledHeadingLabel("Add Player");
		c.insets = new Insets(0, 0, 10, 0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = row++;
		add(heading, c);

		c.insets = new Insets(0, 0, 0, 0);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row;
		JLabel iconLabel = getStyledJLabel("Pick Icon");
		add(iconLabel, c);

		iconBox = new JComboBox<PlayerIcon>();
		for (PlayerIcon p : PlayerIcon.values())
			if (p.getPlayerID().isBlank())
				iconBox.addItem(p);
		iconBox.setRenderer(new ListCellRendererIcon());
		iconBox.setBackground(this.getForegroundColor());
		c.gridx = 1;
		c.gridy = row++;
		add(iconBox, c);

		for (int i = 0; i < fields; i++) {
			c.gridx = 0;
			c.gridy = row;
			c.anchor = GridBagConstraints.WEST;
			JLabel l = getStyledJLabel(labels[i]);
			add(l, c);
			c.gridx = 1;
			c.gridy = row++;
			JFormattedTextField textField = new JFormattedTextField();

			switch (i) {
			case 0:
				textField = new JFormattedTextField();
				break;
			case 1:
				NumberFormat format = NumberFormat.getInstance();
				NumberFormatter formatter = new NumberFormatter(format);
				formatter.setValueClass(Integer.class);
				formatter.setMinimum(0);
				formatter.setCommitsOnValidEdit(true);
				textField = new JFormattedTextField(new NumberFormatter());
				break;

			}
			textfields.add(textField);
			textField.setPreferredSize(new Dimension(150, 20));
			l.setLabelFor(textField);
			add(textField, c);
		}
		JButton add = getStyledButton("Add Player");
		add.addActionListener(new AddNewPlayer(ge, this, textfields, iconBox));
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
		this.setTitle("Add Player");
		setVisible(true);
	}
}
