package ausroulette.view;

import javax.swing.JSplitPane;

import ausroulette.controller.WindowResizeListener;

@SuppressWarnings("serial")
public class WheelBetTablePanel extends JSplitPane {
	private WheelPanel w;
	private BettingTablePanel b;
	private RouletteMenu rouletteMenu;

	public WheelBetTablePanel(GuiCallback g, RouletteMenu rouletteMenu, WheelSettings settings) {
		this.setOrientation(HORIZONTAL_SPLIT);
		this.rouletteMenu = rouletteMenu;
		b = new BettingTablePanel();
		w = new WheelPanel(settings);
		add(w, LEFT);
		add(b, RIGHT);
		g.addPropertyChangeListener(w);
		g.addPropertyChangeListener(b);
		this.setDividerLocation(rouletteMenu.getWidth() / 2);
		this.setEnabled(false);
		this.addComponentListener(new WindowResizeListener(this));
		w.invalidate();
		setVisible(true);
	}

	public void resetDivider() {
		this.setDividerLocation(rouletteMenu.getWidth() / 2);
	}
}