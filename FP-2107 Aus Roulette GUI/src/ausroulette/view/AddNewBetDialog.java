package ausroulette.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

import ausroulette.controller.AddBetChooseBetTypeListener;
import ausroulette.controller.AddBetPlayerBoxListener;
import ausroulette.controller.AddNewBet;
import ausroulette.controller.FrameCloserListener;
import ausroulette.controller.PointSliderChangeListener;
import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.BetType;
import ausroulette.model.wheel.Wheel;

@SuppressWarnings("serial")
public class AddNewBetDialog extends AbstractJDialog {
	private final int HIGHEST_NUMBER = 36;
	private JComboBox<Player> playerbox;
	private JComboBox<BetType> betTypes;
	private JLabel pointsLabel;
	private JSlider pointsSlider;
	private JLabel selectPockets;
	private JSpinner numberBet;
	private JFormattedTextField split2Bet;
	private JComboBox<String> split2BetChoice;
	private JFormattedTextField split4Bet;
	private ArrayList<JComponent> entryfields = new ArrayList<JComponent>();

	public AddNewBetDialog(GameEngine ge) {
		int row = 0;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel heading = getStyledHeadingLabel("Add Bet");
		c.insets = new Insets(0, 0, 20, 0);
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = row++;
		add(heading, c);
		playerbox = getStyledPlayerJComboBox();
		for (Player p : ge.getAllPlayers()) {
			playerbox.addItem(p);
		}

		c.insets = new Insets(0, 0, 0, 0);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row;
		playerbox.addActionListener(new AddBetPlayerBoxListener(this));
		add(playerbox, c);

		betTypes = new JComboBox<BetType>();
		for (BetType b : BetType.values())
			betTypes.addItem(b);
		ListCellRendererBetType lcrbt = new ListCellRendererBetType();
		betTypes.setRenderer(lcrbt);
		betTypes.setForeground(this.getBackgroundColor());
		betTypes.setBackground(this.getForegroundColor());
		c.gridx = 1;
		c.gridy = row++;
		betTypes.addActionListener(new AddBetChooseBetTypeListener(this));
		add(betTypes, c);
		selectPockets = getStyledJLabel("");
		c.ipady = 5;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = row++;
		selectPockets.setVisible(false);
		entryfields.add(selectPockets);
		add(selectPockets, c);

		numberBet = new JSpinner(new SpinnerNumberModel(1, 1, Wheel.LARGEST_NUMBER, 1));
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = row;
		numberBet.setVisible(false);
		entryfields.add(numberBet);
		add(numberBet, c);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(HIGHEST_NUMBER - 3);
		formatter.setCommitsOnValidEdit(true);
		split2Bet = new JFormattedTextField(formatter);
		split2Bet.setPreferredSize(new Dimension(50, 20));
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row;
		split2Bet.setVisible(false);
		entryfields.add(split2Bet);
		add(split2Bet, c);

		split2BetChoice = new JComboBox<String>();
		split2BetChoice.addItem("Horizontal");
		split2BetChoice.addItem("Vertical");
		c.gridx = 1;
		c.gridy = row;
		split2BetChoice.setVisible(false);
		entryfields.add(split2BetChoice);
		add(split2BetChoice, c);

		formatter.setMaximum(HIGHEST_NUMBER - 4);
		split4Bet = new JFormattedTextField(formatter);
		split4Bet.setPreferredSize(new Dimension(50, 20));
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = row++;
		split4Bet.setVisible(false);
		entryfields.add(split4Bet);
		add(split4Bet, c);

		JLabel sliderHeading = getStyledJLabel("Select Points to Bet");
		c.ipady = 15;
		c.gridx = 0;
		c.gridy = row++;
		add(sliderHeading, c);

		int minPoints = 1;
		if (((Player) playerbox.getSelectedItem()).getAvailablePoints() == 0)
			minPoints = 0;
		pointsSlider = new JSlider(minPoints, ((Player) playerbox.getSelectedItem()).getAvailablePoints());
		pointsSlider.addChangeListener(new PointSliderChangeListener(this));
		pointsSlider.setBackground(this.getBackgroundColor());
		c.ipady = 10;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = row++;
		add(pointsSlider, c);

		if (pointsSlider.getValue() == 0)
			pointsLabel = getStyledJLabel("Player has no points");
		else {
			pointsLabel = getStyledJLabel(String.valueOf(pointsSlider.getValue()));
		}
		c.ipady = 0;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = row++;
		add(pointsLabel, c);

		JButton add = getStyledButton("Add Bet");
		add.addActionListener(new AddNewBet(ge, this, playerbox, betTypes, pointsSlider, numberBet, split2Bet,
				split2BetChoice, split4Bet));

		c.gridx = 0;
		c.gridwidth = 1;
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
		this.setTitle("Add Bet");
		setVisible(true);
	}

	public void updatePointsLabel(String points) {
		pointsLabel.setText(points);
	}

	public void updatePointsSlider(int newMin, int newMax) {
		pointsSlider.setMaximum(newMax);
		pointsSlider.setMinimum(newMin);
		pointsSlider.setValue(newMax / 2);
	}

	public void hideBetAdditionals() {
		for (JComponent j : entryfields) {
			j.setVisible(false);
		}
		this.setSize(this.getPreferredSize());
	}

	public void showNumberBet() {
		for (JComponent j : entryfields) {
			j.setVisible(false);
		}
		selectPockets.setText("Select a Pocket from the table");
		selectPockets.setVisible(true);
		numberBet.setVisible(true);
		this.setSize(this.getPreferredSize());
	}

	public void showSplit2Bet() {
		for (JComponent j : entryfields) {
			j.setVisible(false);
		}
		selectPockets.setText("Choose Two Pockets from the table");
		selectPockets.setVisible(true);
		split2Bet.setVisible(true);
		split2BetChoice.setVisible(true);
		this.setSize(this.getPreferredSize());
	}

	public void showSplit4Bet() {
		for (JComponent j : entryfields) {
			j.setVisible(false);
		}
		selectPockets.setText("Enter a Basenumber Pocket from the table");
		selectPockets.setVisible(true);
		split4Bet.setVisible(true);
		this.setSize(this.getPreferredSize());
	}

}
