package ausroulette.model.bet;

/**
 * Represents bet types in the Aussie Roulette game in the <b>Further
 * Programming Assignment</b>.
 * <p>
 * You <b>must not</b> alter the enum values defined here.
 * <p>
 * You <b>must</b>promote interoperability with other implementations of the
 * specifications and <b>not</b> alter the public interface of this enum.
 * However, you <b>may</b> override existing methods in the Enum hierarchy.
 * 
 * @see ausroulette.model.bet.Bet
 * @see ausroulette.model.bet.ColorBet
 * @see ausroulette.model.bet.NumberBet
 * @see ausroulette.model.bet.SplitBet
 * 
 * @author Ross Nye / YOU?
 */
public enum BetType {
	ZERO {
		public String toString() {
			return "Zero";
		}
	},
	NUMBER {
		public String toString() {
			return "Number";
		}
	},
	SPLIT_2 {
		public String toString() {
			return "2-way Split";
		}
	},
	SPLIT_4 {
		public String toString() {
			return "4-way Split";
		}
	},
	RED {
		public String toString() {
			return "Red";
		}
	},
	BLACK {
		public String toString() {
			return "Black";
		}
	};
}
