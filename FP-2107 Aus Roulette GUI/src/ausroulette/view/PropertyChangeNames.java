package ausroulette.view;

public enum PropertyChangeNames {
	NEW_PLAYER, REMOVE_PLAYER, NEW_BET, CANCEL_BET, ADD_POINTS, SELECTED_PLAYER, SPIN_START, SPIN_TICK, BET_RESULT,
	SPIN_RESULT, SPIN_PLAYER_TOTAL, SPIN_HOUSE_RESULT;

	public String toString() {
		return this.name();
	}
}