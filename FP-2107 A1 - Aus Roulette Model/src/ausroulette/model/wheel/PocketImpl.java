package ausroulette.model.wheel;

import ausroulette.util.Validator;

public class PocketImpl implements Pocket {
	private int number;
	private PocketColor color;

	public PocketImpl(int number, PocketColor color) {
		Validator v = new Validator();
		v.validPocketNumber(number);
		v.validPocketColor(color);
		this.number = number;
		this.color = color;
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public PocketColor getColor() {
		return this.color;
	}

	@Override
	public boolean equals(Pocket pocket) {
		boolean equal = true;
		if (!(this.getNumber() == pocket.getNumber()))
			equal = false;
		if (!(this.getColor().equals(pocket.getColor())))
			equal = false;
		return equal;
	}

	@Override
	public boolean equals(Object pocket) {
		if (!(pocket instanceof Pocket))
			return false;
		else
			return this.equals(pocket);
	}

	public int hashCode() {
		return this.number + this.color.hashCode();

	}

	@Override
	public String toString() {
		String numberf = "#" + number;
		return String.format("Pocket: %3s %s", numberf, color);
	}

}
