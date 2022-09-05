package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class FrameCloserListener implements ActionListener {
	private JDialog frame;

	public FrameCloserListener(JDialog dialog) {
		this.frame = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.dispose();
	}

}
