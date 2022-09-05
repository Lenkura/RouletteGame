package ausroulette.view;

public class BetTablePocketCoordinates {
	private int PocketNumber;
	private int x;
	private int y;

	public BetTablePocketCoordinates(int PocketNumber, int x, int y) {
		this.PocketNumber = PocketNumber;
		this.x = x;
		this.y = y;
	}

	public int getPocketNumber() {
		return PocketNumber;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
