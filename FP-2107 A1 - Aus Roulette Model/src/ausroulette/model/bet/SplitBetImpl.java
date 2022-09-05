package ausroulette.model.bet;

import java.util.Arrays;

import ausroulette.model.Player;
import ausroulette.util.Validator;

public class SplitBetImpl extends BetImpl implements SplitBet {
	private int[] numbers;
	private final int PAYOUT_SPLIT2 = 17;
	private final int PAYOUT_SPLIT4 = 8;
	private final int TWOWAYSPLIT_LENGTH = 2;
	private final int FOURWAYSPLIT_LENGTH = 4;

	public SplitBetImpl(Player player, int amount, int[] numbers) {
		super(player, amount, (numbers.length == 2) ? BetType.SPLIT_2 : BetType.SPLIT_4);
		this.numbers = numbers;
		Arrays.sort(this.numbers);
		Validator v = new Validator();
		v.validPocketNumber(this.getFirstNumber());
		v.validPocketNumber(this.getLastNumber());

		if (!(numbers.length == TWOWAYSPLIT_LENGTH || numbers.length == FOURWAYSPLIT_LENGTH))
			throw new IllegalArgumentException("Split bets are made up of either 2 or 4 numbers");

		if (numbers.length == TWOWAYSPLIT_LENGTH) {
			v.checkSplitBet2(this);
		}
		if (numbers.length == FOURWAYSPLIT_LENGTH) {
			v.checkSplitBet4(this);
		}

	}

	public SplitBetImpl(Player player, int amount, int number1, int number2) {
		this(player, amount, new int[] { number1, number2 });

	}

	public SplitBetImpl(Player player, int amount, int number1, int number2, int number3, int number4) {
		this(player, amount, new int[] { number1, number2, number3, number4 });
	}

	@Override
	public boolean isWin() {
		boolean win = false;
		for (int i : numbers)
			if (i == this.getWinningPocket().getNumber())
				win = true;
		return win;
	}

	@Override
	public int getAmountWon() {
		int amountWon = 0;
		if (this.getWinningPocket() == null)
			return amountWon;
		if (this.getBetType() == BetType.SPLIT_2)
			amountWon = this.getAmount() * PAYOUT_SPLIT2;
		if (this.getBetType() == BetType.SPLIT_4)
			amountWon = this.getAmount() * PAYOUT_SPLIT4;
		return amountWon;
	}

	@Override
	public int getFirstNumber() {
		return numbers[0];
	}

	@Override
	public int getLastNumber() {
		return getNumber(SplitBet.SplitPos.LAST);
	}

	@Override
	public int getNumber(SplitPos pos) {
		int number = 99;
		switch (pos) {
		case FIRST:
			number = numbers[0];
			break;
		case SECOND:
			number = numbers[1];
			break;
		case THIRD:
			number = numbers[2];
			break;
		case FORTH:
			number = numbers[3];
			break;
		case LAST:
			if (numbers.length == TWOWAYSPLIT_LENGTH)
				number = numbers[1];
			if (numbers.length == FOURWAYSPLIT_LENGTH)
				number = numbers[3];
			break;
		}
		return number;
	}

	@Override
	public boolean equals(Bet bet) {
		boolean equal = true;
		if (super.equals(bet)) {
			if (!(bet instanceof SplitBet))
				equal = false;
			if (!(this.getFirstNumber() == ((SplitBetImpl) bet).getFirstNumber()))
				equal = false;
			if (!(this.getLastNumber() == ((SplitBetImpl) bet).getLastNumber()))
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
		return super.hashCode() + numbers.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s %s Split %s", super.toString(),
				this.getBetType() == BetType.SPLIT_2 ? "2-way" : "4-way", Arrays.toString(numbers));
	}
}
