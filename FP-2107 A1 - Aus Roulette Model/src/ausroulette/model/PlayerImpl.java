package ausroulette.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ausroulette.model.bet.Bet;
import ausroulette.util.Validator;

public class PlayerImpl implements Player {
	private final String id;
	private final String name;
	private final int initialPoints;
	private int points;
	private ArrayList<Bet> playerBets;

	public PlayerImpl(String id, String name, int initialPoints) {
		Validator v = new Validator();
		v.checkID(id);
		v.checkName(name);
		v.checkAmount(initialPoints);
		this.id = id;
		this.name = name;
		this.initialPoints = initialPoints;
		this.points = initialPoints;
		this.playerBets = new ArrayList<Bet>();
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getInitialPoints() {
		return this.initialPoints;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	@Override
	public int getAvailablePoints() {
		int availablePoints = points - this.getCurrentBetTotal();
		return availablePoints;

	}

	@Override
	public void addPoints(int points) {
		if (points < 0)
			throw new IllegalArgumentException("Positive points amounts only");
		this.points += points;
	}

	@Override
	public int getCurrentBetTotal() {
		int betPoints = 0;
		for (Bet b : playerBets) {
			betPoints = betPoints + b.getAmount();
		}
		return betPoints;
	}

	@Override
	public void acceptBet(Bet bet) {
		playerBetcheck(bet);
		this.playerBets.add(bet);

	}

	@Override
	public void cancelBet(Bet bet) {
		playerBetcheck(bet);
		if (!(bet.getWinningPocket() == null))
			throw new IllegalArgumentException("Bet already finalised");
		this.playerBets.remove(bet);
	}

	@Override
	public void applyBetOutcome(Bet bet) {
		playerBetcheck(bet);
		if ((bet.getWinningPocket() == null))
			throw new IllegalArgumentException("Bet not finalised");
		if (bet.isWin())
			this.addPoints(bet.getAmountWon());
		else
			this.points -= bet.getAmount();
	}

	@Override
	public void resetBets() {
		this.playerBets.clear();

	}

	@Override
	public Collection<Bet> getBets() {
		return Collections.unmodifiableList(this.playerBets);
	}

	@Override
	public int compareTo(Player player) {
		return this.name.compareTo(player.getName());
	}

	@Override
	public boolean equals(Player player) { // Equal methods? //Bad need to compare actual attributes, see pocket
		if (this.getId().equals(player.getId()))
			return true;
		else
			return false;
	}

	public boolean equals(Object player) {
		if (player instanceof Player)
			return this.equals((Player) player);
		else
			return false;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public String toString() {
		return String.format(
				"Player ID: \"%s\", Name: \"%s\"\nPoints: %d, Available Points: %d\nCurrent Bets: %d, Total Bet: %d",
				this.id, this.name, this.points, this.getAvailablePoints(), this.getBets().size(),
				this.getCurrentBetTotal());

	}

	private void playerBetcheck(Bet bet) {
		if (bet == null)
			throw new NullPointerException("Bet cannot be null");
		if (!(bet.getPlayer() == this) && !playerBets.contains(bet))
			throw new IllegalArgumentException("Bet not associated with this player");
	}
}
