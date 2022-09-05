package ausroulette.view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import ausroulette.model.Player;

@SuppressWarnings("serial")
public abstract class AbstractJDialog extends JDialog {
	private Color foregroundColor = new Color(20, 41, 94);
	private Color backgroundColor = new Color(151, 191, 110);
	private Font labelFont = new Font("Verdana", Font.PLAIN, 18);
	private Font headingFont = new Font("Verdana", Font.PLAIN, 25);

	public AbstractJDialog() {
		this.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
		this.getRootPane().setBackground(backgroundColor);
		this.getContentPane().setBackground(backgroundColor);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		this.setLocationRelativeTo(null);
		setResizable(false);

	}

	public JButton getStyledButton(String text) {
		JButton button = new JButton(text);
		button.setForeground(backgroundColor);
		button.setBackground(foregroundColor);
		return button;

	}

	public JLabel getStyledJLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(labelFont);
		label.setForeground(foregroundColor);
		label.setBackground(backgroundColor);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		return label;
	}

	public JLabel getStyledHeadingLabel(String text) {
		JLabel label = new JLabel(text);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, this.getForegroundColor()));
		label.setFont(headingFont);
		label.setForeground(foregroundColor);
		label.setBackground(backgroundColor);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
		return label;
	}

	public JComboBox<Player> getStyledPlayerJComboBox() {
		JComboBox<Player> playerbox = new JComboBox<Player>();
		ListCellRendererPlayer lcrp = new ListCellRendererPlayer();
		playerbox.setRenderer(lcrp);
		playerbox.setForeground(backgroundColor);
		playerbox.setBackground(foregroundColor);

		return playerbox;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Font getLabelFont() {
		return labelFont;
	}

	public Font getHeadingFont() {
		return headingFont;
	}
}
