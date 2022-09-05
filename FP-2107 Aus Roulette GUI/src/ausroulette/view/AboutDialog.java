package ausroulette.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ausroulette.controller.FrameCloserListener;

@SuppressWarnings("serial")
public class AboutDialog extends AbstractJDialog {

	public AboutDialog() {
		this.setLayout(new GridLayout(0, 1));
		JLabel heading = getStyledHeadingLabel("About");
		add(heading);
		add(getStyledJLabel("Name : Raymond Louey"));
		add(getStyledJLabel("Student Number : s3853718"));
		add(getStyledJLabel("Image Sources:"));
		add(getStyledJLabel(
				"Wheel - https://images.vexels.com/media/users/3/151205/isolated/preview/8857efb275fbf2435db40a222d05b1e6-roulette-wheel-icon-by-vexels.png"));
		add(getStyledJLabel(
				"Add Player Icon: https://cdn2.iconfinder.com/data/icons/user-profile-1/100/player-profile-add-512.png"));
		add(getStyledJLabel(
				"Add Points Icon: https://www.flaticon.com/svg/vstatic/svg/4291/4291393.svg?token=exp=1620471787~hmac=46fa51a9d3eaa1cb5ef8d17102d27c81"));
		add(getStyledJLabel("Add Bet Icon: https://cdn1.iconfinder.com/data/icons/casino-poker/100/Casino-13-512.png"));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton ok = getStyledButton("OK");
		ok.addActionListener(new FrameCloserListener(this));
		ok.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.add(ok);
		buttonPanel.setBackground(this.getBackgroundColor());
		add(buttonPanel);
		pack();
		this.setLocationRelativeTo(this);
		this.setTitle("About");
		setVisible(true);

	}

	@Override
	public JLabel getStyledJLabel(String text) {
		JLabel label = super.getStyledJLabel(text);
		label.setHorizontalAlignment(JLabel.LEFT);
		return label;

	}
}
