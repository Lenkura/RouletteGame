package ausroulette.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class ListCellRendererIcon extends JLabel implements ListCellRenderer<PlayerIcon> {
	public ListCellRendererIcon() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends PlayerIcon> list, PlayerIcon value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if (index == -1 && value == null) {
			setText("No Icons Left");
		} else
			this.setIcon(value.getIcon());
		if (isSelected) {
			this.setBackground(Color.DARK_GRAY);
		} else {
			this.setBackground(Color.WHITE);
		}
		return this;
	}
}
