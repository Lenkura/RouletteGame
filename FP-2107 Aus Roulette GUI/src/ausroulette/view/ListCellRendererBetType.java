package ausroulette.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ausroulette.model.bet.BetType;

@SuppressWarnings("serial")
public class ListCellRendererBetType extends JLabel implements ListCellRenderer<BetType> {
	public ListCellRendererBetType() {
		setOpaque(true);
		this.setFont(new Font("Dialog", Font.PLAIN, 18));
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends BetType> list, BetType value, int index,
			boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());
		if (isSelected) {
			this.setBackground(Color.BLACK);
			this.setForeground(Color.WHITE);
		} else {
			this.setForeground(Color.DARK_GRAY);
			this.setBackground(Color.WHITE);
		}
		return this;
	}

}
