package ausroulette.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;

import javax.swing.SwingUtilities;

import ausroulette.model.Player;
import ausroulette.model.bet.Bet;
import ausroulette.model.wheel.Pocket;

public class GuiCallback implements GameCallback {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	@Override
	public void playerAdded(Player player) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.NEW_PLAYER.toString(), null, player);
			}
		});
	}

	@Override
	public void playerRemoved(Player player) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.REMOVE_PLAYER.toString(), player, null);
			}
		});
	}

	@Override
	public void pointsAdded(Player player, int points) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.ADD_POINTS.toString(), points, player);
			}
		});
	}

	@Override
	public void betAccepted(Player player, Bet bet, Bet existingBet) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.NEW_BET.toString(), existingBet, bet);
			}
		});
	}

	@Override
	public void betCancelled(Bet bet) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.CANCEL_BET.toString(), bet, null);
			}
		});
	}

	@Override
	public void spinStart(int spinNumber, Pocket pocket) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.SPIN_START.toString(), spinNumber, pocket);
			}
		});

	}

	@Override
	public void spinTick(int spinNumber, int tick, Pocket pocket) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.SPIN_TICK.toString(), tick, pocket);
			}
		});
	}

	@Override
	public void spinResult(int spinNumber, Pocket pocket) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.SPIN_RESULT.toString(), spinNumber, pocket);
			}
		});
	}

	@Override
	public void betResult(int spinNumber, Player player, Bet bet) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.BET_RESULT.toString(), player, bet);
			}
		});
	}

	@Override
	public void spinPlayerTotal(int spinNumber, Player player, Collection<Bet> bets, int amountWon, int amountLost) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				int amount = amountWon - amountLost;
				pcs.firePropertyChange(PropertyChangeNames.SPIN_PLAYER_TOTAL.toString(), player, amount);
			}
		});

	}

	@Override
	public void spinHouseResult(int spinNumber, int allPlayerTotalWin, int allPlayerTotalLoss) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pcs.firePropertyChange(PropertyChangeNames.SPIN_HOUSE_RESULT.toString(), allPlayerTotalWin,
						allPlayerTotalLoss);
			}
		});
	}
}
