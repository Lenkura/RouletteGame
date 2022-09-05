package ausroulette.view;

public class WheelSettings {
	private String turnSetting;
	private String delaySetting;
	private int startingNumber;

	public WheelSettings() {
		delaySetting = "Default";
		turnSetting = "Default";
		startingNumber = 0;
	}

	public String getTurnSetting() {
		return turnSetting;
	}

	public void setTurnSetting(String turnSetting) {
		this.turnSetting = turnSetting;
	}

	public String getDelaySetting() {
		return delaySetting;
	}

	public void setDelaySetting(String delaySetting) {
		this.delaySetting = delaySetting;
	}

	public int getStartingNumber() {
		return startingNumber;
	}

	public void setStartingNumber(int startingNumber) {
		this.startingNumber = startingNumber;
	}

	public int getDelayNumber() {
		switch (delaySetting) {
		case ("Fast"):
			return 10;

		case ("Default"):
			return 100;

		case ("Slow"):
			return 250;

		case ("Very Slow"):
			return 1000;
		default:
			return 100;
		}
	}

	public int getTurnNumber() {
		int fullTurn = 37;
		switch (turnSetting) {
		case ("Quick"):
			return fullTurn;
		case ("Short"):
			return (int) (Math.random() * 2 * fullTurn + fullTurn);

		case ("Default"):
			return (int) (Math.random() * 2 * fullTurn + (3 * fullTurn));

		case ("Long"):
			return (int) (Math.random() * 2 * fullTurn + (3 * fullTurn));

		default:
			return (int) (Math.random() * 2 * fullTurn + (3 * fullTurn));
		}
	}
}
