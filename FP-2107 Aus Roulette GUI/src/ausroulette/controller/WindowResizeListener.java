package ausroulette.controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import ausroulette.view.WheelBetTablePanel;

public class WindowResizeListener extends ComponentAdapter {
	private JPanel panel;
	private WheelBetTablePanel splitPanel;

	public WindowResizeListener(JPanel p) {
		panel = p;
	}

	public WindowResizeListener(WheelBetTablePanel p) {
		splitPanel = p;
	}

	public void componentResized(ComponentEvent componentEvent) {
		if (panel != null)
			panel.revalidate();
		if (splitPanel != null)
			splitPanel.resetDivider();
	}
}
