package ausroulette.model.bet;

import ausroulette.model.Player;
import ausroulette.util.Validator;

public class NumberBetImpl extends BetImpl implements NumberBet {
	private int number;
	private final int PAYOUT = 35;

	public NumberBetImpl(Player player, int amount, int number) {
		super(player, amount, number == 0 ? BetType.ZERO : BetType.NUMBER);
		this.number = number;
		Validator v = new Validator();
		v.validPocketNumber(number);
	}

	@Override
	public boolean isWin() {
		if (this.getWinningPocket().getNumber() == this.number)
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
	public int getNumber() {
		return this.number;
	}

	@Override
	public boolean equals(Bet bet) {
		boolean equal = true;
		if (super.equals(bet)) {
			if (!(bet instanceof NumberBet))
				equal = false;
			if (!(this.getNumber() == ((NumberBetImpl) bet).getNumber()))
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
	public int hashCode() { // Equal methods? //Bad need to compare actual attributes, see pocket
		return super.hashCode() + this.number;
	}

	@Override
	public String toString() {
		if (this.getBetType() == BetType.NUMBER)
			return String.format("%s Number #%d", super.toString(), this.number);
		else
			return String.format("%s Zero", super.toString());

	}

}
