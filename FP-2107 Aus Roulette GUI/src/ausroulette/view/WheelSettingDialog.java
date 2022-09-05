package ausroulette.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import ausroulette.controller.FrameCloserListener;
import ausroulette.controller.SaveWheelSettings;
import ausroulette.model.GameEngine;
import ausroulette.model.wheel.Wheel;

@SuppressWarnings("serial")
public class WheelSettingDialog extends AbstractJDialog {

	public WheelSettingDialog(GameEngine ge, WheelSettings settings) {
		int row = 0;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel heading = getStyledHeadingLabel("Wheel Settings");
		c.ipady = 20;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = row++;
		add(heading, c);

		String[] turn = { "Quick", "Short", "Default", "Long" };
		ButtonGroup turnGroup = new ButtonGroup();
		int topOfRadio = row;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row++;
		JLabel turnHeading = getStyledJLabel("Turn Settings");
		Font subheadingFont = new Font("Verdana", Font.BOLD, 14);
		Border subHeadingBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, this.getForegroundColor());
		turnHeading.setFont(subheadingFont);
		turnHeading.setBorder(subHeadingBorder);
		turnHeading.setHorizontalAlignment(JLabel.CENTER);
		add(turnHeading, c);
		for (String s : turn) {
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.BOTH;
			c.ipady = 5;
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = row++;
			JRadioButton turnButton = new JRadioButton(s);
			turnButton.setActionCommand(s);
			turnButton.setForeground(this.getForegroundColor());
			turnButton.setBackground(this.getBackgroundColor());
			turnGroup.add(turnButton);
			if (turnButton.getActionCommand().equals(settings.getTurnSetting())) {
				turnButton.setSelected(true);
			}
			add(turnButton, c);
		}
		row = topOfRadio;
		c.gridx = 1;
		c.gridy = row++;
		JLabel delayHeading = getStyledJLabel("Delay Settings");
		delayHeading.setFont(subheadingFont);
		delayHeading.setBorder(subHeadingBorder);
		delayHeading.setHorizontalAlignment(JLabel.CENTER);
		add(delayHeading, c);
		String[] delay = { "Fast", "Default", "Slow", "Very Slow" };
		ButtonGroup delayGroup = new ButtonGroup();

		for (String s : delay) {
			c.anchor = GridBagConstraints.CENTER;
			c.fill = GridBagConstraints.BOTH;
			c.gridwidth = 1;
			c.gridx = 1;
			c.gridy = row++;
			JRadioButton delayButton = new JRadioButton(s);
			delayButton.setActionCommand(s);
			delayButton.setForeground(this.getForegroundColor());
			delayButton.setBackground(this.getBackgroundColor());
			delayGroup.add(delayButton);
			if (delayButton.getActionCommand().equals(settings.getDelaySetting())) {
				delayButton.setSelected(true);
			}
			add(delayButton, c);
		}

		c.gridx = 0;
		c.gridy = row;
		add(getStyledJLabel("Starting Pocket"), c);

		c.gridx = 1;
		c.gridy = row++;
		c.fill = GridBagConstraints.NONE;
		JSpinner pocketNumber = new JSpinner(
				new SpinnerNumberModel(settings.getStartingNumber(), 0, Wheel.LARGEST_NUMBER, 1));
		add(pocketNumber, c);

		JButton save = getStyledButton("Save");
		save.addActionListener(new SaveWheelSettings(turnGroup, delayGroup, pocketNumber, this,settings));
		c.ipady = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = row;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 10, 5, 10);
		add(save, c);
		JButton cancel = getStyledButton("Cancel");
		cancel.addActionListener(new FrameCloserListener(this));
		c.gridx = 1;
		c.gridy = row++;
		add(cancel, c);

		pack();
		this.setTitle("Wheel Settings");
		setVisible(true);
	}

}
