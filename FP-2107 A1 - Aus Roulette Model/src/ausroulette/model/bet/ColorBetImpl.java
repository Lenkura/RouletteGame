package ausroulette.model.bet;

import ausroulette.model.Player;
import ausroulette.model.wheel.PocketColor;
import ausroulette.util.Validator;

public class ColorBetImpl extends BetImpl implements ColorBet {
	private PocketColor color;
	private final int PAYOUT = 1;

	public ColorBetImpl(Player player, int amount, PocketColor color) {
		super(player, amount, BetType.valueOf(color.name()));
		this.color = color;
		Validator v = new Validator();
		v.validPocketColor(color);
		if (!(color == PocketColor.BLACK || color == PocketColor.RED))
			throw new IllegalArgumentException("Color has to be Red or Black");
	}

	@Override
	public boolean isWin() {
		if (this.getWinningPocket().getColor().equals(this.getColor()))
			return true;
		else
			return false;
	}

	@Override
	public int getAmountWon() {
		if (this.getWinningPocket() == null)
			return 0;
		else
			return this.getAmount() * PAYOUT;
	}

	@Override
	public PocketColor getColor() {
		return color;
	}

	@Override
	public boolean equals(Bet bet) {
		boolean equal = true;
		if (super.equals(bet)) {
			if (!(bet instanceof ColorBet))
				equal = false;
			if (!(this.getColor() == ((ColorBetImpl) bet).getColor()))
				equal = false;
		} else
			equal = false;
		return equal;
	}

	@Override
	public boolean equals(Object bet) {
		if (super.equals(bet))
			return this.equals((Bet) bet);
		else
			return false;
	}

	@Override
	public int hashCode() { 
		return super.hashCode() + color.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s %s", super.toString(), this.color == PocketColor.RED ? "Red" : "Black");

	}
}
