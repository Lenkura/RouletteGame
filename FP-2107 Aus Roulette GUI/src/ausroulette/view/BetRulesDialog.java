package ausroulette.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ausroulette.controller.FrameCloserListener;

@SuppressWarnings("serial")
public class BetRulesDialog extends AbstractJDialog {

	public BetRulesDialog() {
		this.setLayout(new GridLayout(0, 1));
		JLabel heading = getStyledHeadingLabel("Bet Type Rules");
		add(heading);
		Font subheadingFont = new Font("Verdana", Font.PLAIN, 22);
		JLabel numberBetHeading = getStyledJLabel("Number Bet");
		numberBetHeading.setFont(subheadingFont);
		JLabel numberBetDescription = getStyledJLabel(
				"   A bet is placed on a given pocket number. The bet wins if the winning (final) pocket of a spin of the wheel has the samenumber as specified by the bet.");
		JLabel numberBetPayout = getStyledJLabel("   Payout: 35:1");
		add(numberBetHeading);
		add(numberBetDescription);
		add(numberBetPayout);

		JLabel splitBetHeading = getStyledJLabel("Split Bet: 2-way Split or 4-way Split");
		splitBetHeading.setFont(subheadingFont);
		JLabel splitBetDescription = getStyledJLabel(
				"   A bet is placed on either 2 or 4 adjoining numbers on the betting table. The bet wins if the winning (final) pocket matches any of the number specified by the split bet.");
		JLabel splitBetPayout = getStyledJLabel("   Payouts: 2-way Split -  17:1   4-way Split - 8:1");
		add(splitBetHeading);
		add(splitBetDescription);
		add(splitBetPayout);
		JLabel colorBetHeading = getStyledJLabel("Colour Bet");
		colorBetHeading.setFont(subheadingFont);
		JLabel colorBetDescription = getStyledJLabel(
				"   A bet is placed on either of the two colours, red or black. The bet win if the winning (final) pocket of a spin of the wheel has the same colour as specified by the bet.");
		JLabel colorBetPayout = getStyledJLabel("   Payout: 1:1");
		add(colorBetHeading);
		add(colorBetDescription);
		add(colorBetPayout);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok = getStyledButton("OK");
		ok.addActionListener(new FrameCloserListener(this));
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(ok);
		buttonPanel.setBackground(this.getBackgroundColor());
		add(buttonPanel);

		pack();
		this.setTitle("Bet Rules");
		this.setLocationRelativeTo(this);
		setVisible(true);
	}

	@Override
	public JLabel getStyledJLabel(String text) {
		JLabel label = super.getStyledJLabel(text);
		label.setHorizontalAlignment(JLabel.LEFT);
		return label;

	}
}
