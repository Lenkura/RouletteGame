package ausroulette.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.view.AboutDialog;

public class AboutDialogListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unused")
		AboutDialog a = new AboutDialog();
		
	}

}
