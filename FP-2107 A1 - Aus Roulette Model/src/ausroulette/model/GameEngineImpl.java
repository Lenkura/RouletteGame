package ausroulette.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ausroulette.model.bet.Bet;
import ausroulette.model.bet.ColorBetImpl;
import ausroulette.model.bet.NumberBetImpl;
import ausroulette.model.bet.SplitBetImpl;
import ausroulette.model.wheel.Pocket;
import ausroulette.model.wheel.PocketColor;
import ausroulette.model.wheel.Wheel;
import ausroulette.model.wheel.WheelImpl;
import ausroulette.util.Validator;
import ausroulette.view.GameCallback;

public class GameEngineImpl implements GameEngine {
	private Map<String, Player> players = new HashMap<>();
	private Collection<GameCallback> GameCallback = new ArrayList<GameCallback>();

	private final WheelImpl wheel = new WheelImpl();;
	private int spinNumber;

	public GameEngineImpl() {
		this.spinNumber = 0;
	}

	@Override
	public int registerCallback(GameCallback callback) {
		GameCallback.add(callback);
		return GameCallback.size();
	}

	@Override
	public int removeCallback(GameCallback callback) {
		GameCallback.remove(callback);
		return GameCallback.size();
	}

	@Override
	public Player addPlayer(String id, String name, int initialPoints) {
		Validator v = new Validator();
		v.checkID(id);
		v.checkName(name);
		v.checkAmount(initialPoints);
		if (players.containsKey(id))
			throw new IllegalArgumentException("ID taken");
		Player newplayer = new PlayerImpl(id, name, initialPoints);
		players.put(id, newplayer);
		for (GameCallback g : GameCallback) {
			g.playerAdded(newplayer);
		}
		return newplayer;

	}

	@Override
	public Player removePlayer(String id) {
		Validator v = new Validator();
		v.checkID(id);
		if (!(players.containsKey(id)))
			throw new NullPointerException("ID not found");
		if (!(players.get(id).getBets().isEmpty()))
			throw new NullPointerException("Player has placed bets");
		Player removedplayer = players.get(id);
		players.remove(id);
		for (GameCallback g : GameCallback) {
			g.playerRemoved(removedplayer);
		}
		return removedplayer;
	}

	@Override
	public Collection<Player> getAllPlayers() {
		Collection<Player> showcaseplayer = players.values();
		return showcaseplayer;
	}

	@Override
	public Player addPoints(String id, int points) {
		Validator v = new Validator();
		v.checkID(id);
		v.checkAmount(points);
		if (!(players.containsKey(id)))
			throw new NullPointerException("ID not found");
		Player addpointplayer = players.get(id);
		addpointplayer.addPoints(points);
		for (GameCallback g : GameCallback) {
			g.pointsAdded(addpointplayer, points);
		}
		return addpointplayer;
	}

	@Override
	public void cancelBet(Bet bet) {
		Player removeBetPlayer = bet.getPlayer();
		removeBetPlayer.cancelBet(bet);
		for (GameCallback g : GameCallback) {
			g.betCancelled(bet);
		}

	}

	@Override
	public Bet placeColorBet(String id, int amount, PocketColor color) {
		Validator v = new Validator();
		v.checkID(id);
		v.checkAmount(amount);
		playerResourceCheck(id, amount);

		if (color == PocketColor.GREEN) {
			return this.placeNumberBet(id, amount, 0);
		} else {
			Player betplayer = players.get(id);
			Bet bet = new ColorBetImpl(betplayer, amount, color);
			Bet existingBet = findExistingBet(betplayer, bet);
			if (existingBet != null) {
				bet = new ColorBetImpl(betplayer, amount + existingBet.getAmount(), color);
				betplayer.cancelBet(existingBet);
			}
			betplayer.acceptBet(bet);
			callbackBetAccepted(betplayer, bet, existingBet);
			return bet;
		}
	}

	private void callbackBetAccepted(Player betplayer, Bet bet, Bet existingBet) {
		for (GameCallback g : GameCallback) {
			g.betAccepted(betplayer, bet, existingBet);
		}
	}

	@Override
	public Bet placeNumberBet(String id, int amount, int number) {
		Validator v = new Validator();
		v.checkID(id);
		v.checkAmount(amount);
		playerResourceCheck(id, amount);
		Player betplayer = players.get(id);
		Bet bet = new NumberBetImpl(betplayer, amount, number);
		Bet existingBet = findExistingBet(betplayer, bet);
		if (existingBet != null) {
			bet = new NumberBetImpl(betplayer, amount + existingBet.getAmount(), number);
			betplayer.cancelBet(existingBet);
		}
		betplayer.acceptBet(bet);
		callbackBetAccepted(betplayer, bet, existingBet);
		return bet;
	}

	@Override
	public Bet placeSplitBet2(String id, int amount, int number1, int number2) {
		int[] numbers = new int[] { number1, number2 };
		return placeSplitBet(id, amount, numbers);
	}

	@Override
	public Bet placeSplitBet2(String id, int amount, int baseNumber) {
		int[] numbers = new int[] { baseNumber, baseNumber + 1 };
		return placeSplitBet(id, amount, numbers);
	}

	@Override
	public Bet placeSplitBet4(String id, int amount, int number1, int number2, int number3, int number4) {
		int[] numbers = new int[] { number1, number2, number3, number4 };
		return placeSplitBet(id, amount, numbers);
	}

	@Override
	public Bet placeSplitBet4(String id, int amount, int baseNumber) {
		int[] numbers = new int[] { baseNumber, baseNumber + 1, baseNumber + 3, baseNumber + 4 };
		return placeSplitBet(id, amount, numbers);
	}

	@Override
	public Bet placeSplitBet(String id, int amount, int[] numbers) {
		Arrays.sort(numbers);
		Validator v = new Validator();
		v.checkID(id);
		v.checkAmount(amount);
		playerResourceCheck(id, amount);
		Player betplayer = players.get(id);
		Bet bet = new SplitBetImpl(betplayer, amount, numbers);
		Bet existingBet = findExistingBet(betplayer, bet);
		if (existingBet != null) {
			bet = new SplitBetImpl(betplayer, amount + existingBet.getAmount(), numbers);
			betplayer.cancelBet(existingBet);
		}
		betplayer.acceptBet(bet);
		callbackBetAccepted(betplayer, bet, existingBet);
		return bet;
	}

	@Override
	public Pocket spin(int ticks, int delay, int startingNumber) {
		if (ticks < Wheel.NUMBER_OF_POCKETS || delay < 0 || delay > MAX_SPIN_DELAY)
			throw new IllegalArgumentException("Invalid tick or delay number");
		Pocket pocket = wheel.moveToNumber(startingNumber);
		this.spinNumber++;
		for (GameCallback g : GameCallback) {
			g.spinStart(this.spinNumber, pocket);
		}
		for (int i = 1; i <= ticks; i++) {
			pocket = wheel.nextPocket();
			for (GameCallback g : GameCallback) {
				if (i < ticks)
					g.spinTick(this.spinNumber, i, pocket);
				else
					g.spinResult(this.spinNumber, pocket);
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return pocket;
	}

	@Override
	public Pocket spinToWin(int ticks, int delay, int startingNumber) {
		if (players.isEmpty())
			throw new IllegalStateException("No Players");
		boolean nobets = true;
		for (Player p : players.values()) {
			if (!(p.getBets().isEmpty()))
				nobets = false;
		}
		if (nobets)
			throw new IllegalStateException("No Bets");
		Pocket winningpocket = this.spin(ticks, delay, startingNumber);
		this.applyWinLoss(this.spinNumber, winningpocket);
		return winningpocket;
	}

	@Override
	public Pocket spinToWin(int ticks, int delay) {
		return this.spinToWin(ticks, delay, wheel.generateRandomNumber());
	}

	@Override
	public void applyWinLoss(int spinNumber, Pocket winningPocket) {
		int amountWon;
		int amountLost;
		int allPlayerTotalWin = 0;
		int allPlayerTotalLoss = 0;
		for (Player p : players.values()) {
			amountWon = 0;
			amountLost = 0;
			Collection<Bet> bets = p.getBets();

			for (Bet b : bets) {
				int winnings = b.finaliseBet(winningPocket);
				if (winnings == 0) {
					amountLost += b.getAmount();
					allPlayerTotalLoss += b.getAmount();
				} else {
					amountWon += winnings;
					allPlayerTotalWin += winnings;
				}

				p.applyBetOutcome(b);
				for (GameCallback g : GameCallback) {
					g.betResult(spinNumber, p, b);
					;
				}
			}
			p.resetBets();
			for (GameCallback g : GameCallback) {
				g.spinPlayerTotal(spinNumber, p, bets, amountWon, amountLost);
			}

		}

		for (GameCallback g : GameCallback) {
			g.spinHouseResult(spinNumber, allPlayerTotalWin, allPlayerTotalLoss);
		}
	}

	/**
	 * Checks for if a player has an existing bet with the same parameters
	 * 
	 * @param betplayer Player making the bet
	 * @param newbet    Bet the player is attempting to make
	 */
	private Bet findExistingBet(Player betplayer, Bet newbet) {
		Bet existingBet = null;
		Collection<Bet> playerbets = betplayer.getBets();
		for (Bet b : playerbets)
			if (b.getBetType() == newbet.getBetType())
				switch (newbet.getBetType()) {
				case ZERO:
				case RED:
				case BLACK:
					existingBet = b;
					break;
				case NUMBER:
					if (((NumberBetImpl) newbet).getNumber() == ((NumberBetImpl) b).getNumber())
						existingBet = b;
					break;
				case SPLIT_2:
				case SPLIT_4:
					if (((SplitBetImpl) b).getFirstNumber() == ((SplitBetImpl) newbet).getFirstNumber()
							&& ((SplitBetImpl) b).getLastNumber() == ((SplitBetImpl) newbet).getLastNumber())
						existingBet = b;
					break;
				}
		return existingBet;
	}

	/**
	 * Prior to placing a bet, check if the player is valid and has enough points
	 * 
	 * @param id     id of the player to make a bet
	 * @param amount amount of the bet to be made
	 */

	private void playerResourceCheck(String id, int amount) {
		if (!(players.containsKey(id)))
			throw new IllegalArgumentException("ID not found");
		if (players.get(id).getAvailablePoints() < amount)
			throw new IllegalStateException("Insufficient Points");
	}
}
