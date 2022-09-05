package ausroulette.util;

import ausroulette.model.Player;
import ausroulette.model.bet.SplitBet;
import ausroulette.model.bet.SplitBet.SplitPos;
import ausroulette.model.wheel.PocketColor;
import ausroulette.model.wheel.Wheel;

/**
 * Contains various validation methods for inputs for other classes including,
 * but not limited to Player IDs and names, bet amounts and types as well as
 * ensuring bet formats are correct
 * 
 */

public class Validator {

	public Validator() {

	}

	/**
	 * Checks the supplied ID
	 * 
	 * @param id String supplied for the players ID
	 */
	public void checkID(String id) {
		if (id == null)
			throw new NullPointerException("ID cannot be empty");
		if (id.trim().isEmpty())
			throw new IllegalArgumentException("ID cannot be empty");
	}

	/**
	 * Checks the supplied Name
	 * 
	 * @param id String supplied for the players Name
	 */
	public void checkName(String name) {
		if (name == null)
			throw new NullPointerException("Name cannot be empty");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("Name cannot be empty");
	}

	/**
	 * Checks the player isn't null when managing bets
	 * 
	 * @param player Player in the game
	 */
	public void checkPlayer(Player player) {
		if (player == null)
			throw new NullPointerException("Player not Found");
	}

	/**
	 * Checks if any betting amounts are not positive
	 * 
	 * @param amount points put towards a bet
	 */
	public void checkAmount(int amount) {
		if (amount <= 0)
			throw new IllegalArgumentException("Points must be positive");
	}

	/**
	 * Checks if the pocket number is within the wheel
	 * 
	 * @param number Checks if any betting amounts are not positive
	 */
	public void validPocketNumber(int number) {
		if (number < 0 || number > (Wheel.LARGEST_NUMBER))
			throw new IllegalArgumentException("Ïnvalid Pocket Number");
	}

	/**
	 * Checks if the pocket color is correct
	 * 
	 * @param color Supplied Pocket color
	 */
	public void validPocketColor(PocketColor color) {
		if (color == null)
			throw new NullPointerException("Enter pocket color");
	}

	/**
	 * Checks if the 2-way split bet is a valid configuration
	 * 
	 * @param bet 2 way Split bet to be checked
	 */
	public void checkSplitBet2(SplitBet bet) {
		int TABLE_WIDTH = 3;
		// Checks if the bet 'wraps' around the table horizontally
		if (bet.getFirstNumber() % TABLE_WIDTH == 0 && !(bet.getNumber(SplitPos.SECOND) % TABLE_WIDTH == 0))
			throw new IllegalArgumentException(
					"2-Way Splitbets should be two adjacent pockets vertically or horizontally on the betting table");
		// Checks if bet picks adjacent squares horizontally or vertically
		if (!(bet.getNumber(SplitPos.FIRST) + 1 == bet.getNumber(SplitPos.SECOND)
				|| bet.getNumber(SplitPos.FIRST) + TABLE_WIDTH == bet.getNumber(SplitPos.SECOND)))
			throw new IllegalArgumentException(
					"2-Way Splitbets should be two adjacent pockets vertically or horizontally on the betting table");
	}

	/**
	 * Checks if the 4-way split bet is a valid configuration
	 * 
	 * @param bet 4 way Split bet to be checked
	 */
	public void checkSplitBet4(SplitBet bet) {
		int TABLE_WIDTH = 3;
		// Checks if first and second, third and forth are consecutive
		if (!(bet.getNumber(SplitPos.SECOND) - bet.getNumber(SplitPos.FIRST) == 1
				&& bet.getNumber(SplitPos.FORTH) - bet.getNumber(SplitPos.THIRD) == 1))
			throw new IllegalArgumentException(
					"4-Way Split bets should form a square on the betting table, with the basenumber starting in the top left");
		// Checks if first and third, second and forth are three apart
		if (!(bet.getNumber(SplitPos.THIRD) - bet.getNumber(SplitPos.FIRST) == TABLE_WIDTH
				&& bet.getNumber(SplitPos.FORTH) - bet.getNumber(SplitPos.SECOND) == TABLE_WIDTH))
			throw new IllegalArgumentException(
					"4-Way Split bets should form a square on the betting table, with the basenumber starting in the top left");
		// First or third number cannot be on the right-hand column. Similarly the
		// second and forth cannot be on the left
		if (bet.getNumber(SplitPos.FIRST) % TABLE_WIDTH == 0 || bet.getNumber(SplitPos.THIRD) % TABLE_WIDTH == 0
				|| bet.getNumber(SplitPos.SECOND) % TABLE_WIDTH == 1
				|| bet.getNumber(SplitPos.FORTH) % TABLE_WIDTH == 1)
			throw new IllegalArgumentException(
					"4-Way Split bets should form a square on the betting table, with the basenumber starting in the top left");
	}
}
