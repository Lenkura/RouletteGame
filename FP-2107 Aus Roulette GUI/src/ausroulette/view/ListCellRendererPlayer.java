package ausroulette.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ausroulette.model.Player;

@SuppressWarnings("serial")
public class ListCellRendererPlayer extends JLabel implements ListCellRenderer<Player> {
	public ListCellRendererPlayer() {
		setOpaque(true);
		this.setFont(new Font("Dialog", Font.PLAIN, 18));
	}
	@Override
	public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if (index == -1 && value == null) {
			setText("No Players");
		} else
			setText(value.getName());
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
