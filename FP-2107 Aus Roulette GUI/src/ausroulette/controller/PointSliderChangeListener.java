package ausroulette.controller;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ausroulette.view.AddNewBetDialog;

public class PointSliderChangeListener implements ChangeListener {
	private AddNewBetDialog abd;

	public PointSliderChangeListener(AddNewBetDialog addNewBetDialog) {
		this.abd = addNewBetDialog;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		String points;
		int sliderValue = ((JSlider) e.getSource()).getValue();
		if (sliderValue == 0)
			points = "Player has no points";
		else
			points = String.valueOf(sliderValue);
		abd.updatePointsLabel(points);

	}

}
