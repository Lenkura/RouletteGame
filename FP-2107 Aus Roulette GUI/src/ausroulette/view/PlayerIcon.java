package ausroulette.view;

import javax.swing.ImageIcon;

public enum PlayerIcon {
	ICON1, ICON2, ICON3, ICON4, ICON5, ICON6, ICON7;

	private ImageIcon icon;
	private String playerID;

	private PlayerIcon() {
		icon = new ImageIcon(String.format("images/%s.png", this.name().toLowerCase()));
		playerID = "";
	}

	@Override
	public String toString() {
		return this.name();

	}

	public ImageIcon getIcon() {
		return this.icon;
	}

	public String getPlayerID() {
		return this.playerID;
	}

	public void setPlayerID(String ID) {
		this.playerID = ID;
	}
}
