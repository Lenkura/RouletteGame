package ausroulette.model.bet;

import ausroulette.model.Player;
import ausroulette.model.wheel.Pocket;
import ausroulette.util.Validator;

public abstract class BetImpl implements Bet {
	private Player player;
	private int amount;
	private Pocket winningPocket;
	private BetType betType;
	// Make betType a common characteristic

	public BetImpl(Player player, int amount, BetType betType) {
		this.player = player;
		this.amount = amount;
		this.betType = betType;
		Validator v = new Validator();
		v.checkPlayer(player);
		v.checkAmount(amount);
		if (!(player.getAvailablePoints() >= amount))
			throw new IllegalStateException("Player has insufficient points to make this bet");
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public Pocket getWinningPocket() {
		return this.winningPocket;
	}

	@Override
	public int finaliseBet(Pocket winningPocket) {
		int amountWon = 0;
		this.winningPocket = winningPocket;
		if (this.isWin())
			amountWon = this.getAmountWon();
		return amountWon;
	}

	@Override
	public abstract boolean isWin();

	@Override
	public abstract int getAmountWon();

	@Override
	public boolean equals(Bet bet) {
		boolean equal = true;
		if (!(this.player == bet.getPlayer()))
			equal = false;
		if (!(this.amount == bet.getAmount()))
			equal = false;
		if (!(this.getBetType() == bet.getBetType()))
			equal = false;
		return equal;
	}

	@Override
	public boolean equals(Object bet) {
		if (bet instanceof Bet)
			return this.equals((Bet) bet);
		else
			return false;
	}

	public int hashCode() {
		return this.player.hashCode() + this.amount + this.getBetType().hashCode();
	}

	public String toString() {
		return String.format("%s bet %d on", this.getPlayer().getId(), this.getAmount());
	}

	@Override
	public BetType getBetType() {
		return this.betType;
	}

}
