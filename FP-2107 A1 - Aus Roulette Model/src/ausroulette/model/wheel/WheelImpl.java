package ausroulette.model.wheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ausroulette.util.Validator;

public class WheelImpl implements Wheel {
	private List<Pocket> wheel;
	private Pocket currentPocket;

	public WheelImpl() {
		this.wheel = generateWheel();
		this.currentPocket = wheel.get(0);
		;
	}

	@Override
	public int generateRandomNumber() {
		Random r = new Random();
		return r.nextInt(Wheel.LARGEST_NUMBER + 1);
	}

	@Override
	public Pocket moveToNumber(int number) {
		Validator v = new Validator();
		v.validPocketNumber(number);
		Pocket toPocket = null;
		for (int i = 0; i < wheel.size(); i++) {
			if (wheel.get(i).getNumber() == number) {
				toPocket = wheel.get(i);
			}
		}
		this.currentPocket = toPocket;
		return toPocket;
	}

	@Override
	public Pocket nextPocket() {
		Pocket nextPocket = null;
		for (int i = 0; i < this.wheel.size(); i++)
			if (this.currentPocket.equals(wheel.get(i)))
				if (i == this.wheel.size() - 1)
					nextPocket = wheel.get(0);
				else
					nextPocket = wheel.get(i + 1);

		this.currentPocket = nextPocket;
		return nextPocket;
	}

	public static List<Pocket> generateWheel() {
		List<Pocket> wheel = new ArrayList<Pocket>();
		for (int i = 0; i < POCKET_NUMBERS.length; i++) {
			PocketColor color = null;
			if (i % 2 == 0)
				color = PocketColor.BLACK;
			if (i % 2 == 1)
				color = PocketColor.RED;
			if (i == 0)
				color = PocketColor.GREEN;
			Pocket pocket = new PocketImpl(POCKET_NUMBERS[i], color);
			wheel.add(pocket);
		}
		return wheel;
	}

}
