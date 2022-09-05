package ausroulette.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;

import ausroulette.controller.FrameCloserListener;
import ausroulette.model.Player;
import ausroulette.model.bet.Bet;
import ausroulette.model.bet.NumberBet;
import ausroulette.model.bet.SplitBet;
import ausroulette.model.bet.SplitBet.SplitPos;

@SuppressWarnings("serial")
public class ResultsDialog extends AbstractJDialog {

	public ResultsDialog(RouletteMenu rouletteMenu, ArrayList<Bet> finalisedBets, Map<Player, Integer> playerWonLoss,
			int houseLost, int houseWon) {
		int row = 0;
		int maxCellWidth = 5;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel heading = getStyledHeadingLabel("Results of Spin");
		c.gridwidth = maxCellWidth;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = row++;
		add(heading, c);

		JLabel winningPocket = getStyledJLabel("Winning Pocket");
		Font winningPocketFont = new Font("Verdana", Font.PLAIN, 45);
		winningPocket.setFont(winningPocketFont);
		c.ipady = 20;
		c.gridwidth = maxCellWidth;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = row++;
		add(winningPocket, c);

		JLabel winningPocketNumber = getStyledJLabel(
				String.format("%s", finalisedBets.get(0).getWinningPocket().getNumber()));
		winningPocketNumber.setFont(winningPocketFont);
		c.ipady = 5;
		c.gridwidth = maxCellWidth;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = row++;
		add(winningPocketNumber, c);

		Font subHeadingFont = new Font("Verdana", Font.PLAIN, 28);
		JLabel betSubHeading = getStyledJLabel("Bet Results");
		betSubHeading.setFont(subHeadingFont);
		c.ipady = 5;
		c.gridwidth = maxCellWidth;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = row++;
		add(betSubHeading, c);
		String[] playercolumns = { "  Name  ", "  Bet Type  ", "  Pockets  ", "  Result  ", "  Amount Won  " };
		int col = 0;

		for (int i = 0; i < playercolumns.length; i++) {
			c.gridx = col++;
			c.gridy = row;
			c.gridwidth = 1;
			if (i == 0)
				c.insets = new Insets(0, 10, 0, 0);
			else if (i == playercolumns.length - 1)
				c.insets = new Insets(0, 0, 0, 10);
			else {
				c.insets = new Insets(0, 0, 0, 0);
			}
			add(getStyledJLabel(playercolumns[i]), c);
		}
		row++;
		JLabel betInfo = null;
		Color foregroundWin = Color.WHITE;
		Color backgroundWin = new Color(0, 153, 0);
		Color foregroundLoss = Color.LIGHT_GRAY;
		Color backgroundLoss = Color.DARK_GRAY;
		col = 0;
		for (Bet b : finalisedBets) {
			for (int i = 0; i < playercolumns.length; i++) {
				c.gridx = col++;
				c.gridy = row;
				c.gridwidth = 1;
				switch (i) {
				case 0:
					betInfo = getStyledJLabel(b.getPlayer().getName());
					break;
				case 1:
					betInfo = getStyledJLabel(b.getBetType().toString());
					break;
				case 2:
					betInfo = getStyledJLabel(this.betNumber(b));
					break;
				case 3:
					String result;
					if (b.isWin())
						result = "Win!";
					else
						result = "Loss";
					betInfo = getStyledJLabel(result);
					break;
				case 4:
					String winnings;
					if (b.isWin())
						winnings = String.format("+%d", b.getAmountWon());
					else
						winnings = String.format("-%d", b.getAmount());
					betInfo = getStyledJLabel(winnings);
					break;
				}
				if (b.isWin()) {
					betInfo.setForeground(foregroundWin);
					betInfo.setBackground(backgroundWin);
				} else {
					betInfo.setForeground(foregroundLoss);
					betInfo.setBackground(backgroundLoss);
				}
				if (i == 0)
					c.insets = new Insets(0, 10, 0, 0);
				else if (i == playercolumns.length - 1)
					c.insets = new Insets(0, 0, 0, 10);
				else {
					c.insets = new Insets(0, 0, 0, 0);
				}
				add(betInfo, c);

			}
			row++;
			col = 0;
		}

		JLabel playerSubheading = getStyledJLabel("Player Net Gain/Loss");
		playerSubheading.setFont(subHeadingFont);
		c.ipady = 5;
		c.gridwidth = maxCellWidth;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = row++;
		add(playerSubheading, c);

		for (Entry<Player, Integer> p : playerWonLoss.entrySet()) {
			JLabel player = getStyledJLabel(p.getKey().getName());
			JLabel netWinnings = getStyledJLabel(String.format("%s%d", p.getValue() > 0 ? "+" : "", p.getValue()));
			if (p.getValue() > 0) {
				player.setForeground(foregroundWin);
				player.setBackground(backgroundWin);
				netWinnings.setForeground(foregroundWin);
				netWinnings.setBackground(backgroundWin);
			} else {
				player.setForeground(foregroundLoss);
				player.setBackground(backgroundLoss);
				netWinnings.setForeground(foregroundLoss);
				netWinnings.setBackground(backgroundLoss);
			}
			c.ipady = 5;
			c.gridwidth = maxCellWidth / 2;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = row++;
			c.insets = new Insets(0, 10, 0, 0);
			add(player, c);
			c.gridx = maxCellWidth / 2;
			// uneven table, one needs to be larger
			c.gridwidth = (maxCellWidth / 2) + 1;
			c.insets = new Insets(0, 0, 0, 10);
			add(netWinnings, c);
		}

		JLabel houseSubheading = getStyledJLabel("House Net Gain/Loss");
		houseSubheading.setFont(subHeadingFont);
		c.ipady = 5;
		c.gridwidth = maxCellWidth;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = row++;
		add(houseSubheading, c);
		JLabel houseWinnings = getStyledJLabel(
				String.format("%s%d", houseWon - houseLost > 0 ? "+" : "", houseWon - houseLost));
		c.ipady = 5;
		c.gridwidth = maxCellWidth;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = row++;
		c.insets = new Insets(0, 10, 0, 0);
		if (houseWon - houseLost > 0) {
			houseWinnings.setForeground(foregroundWin);
			houseWinnings.setBackground(backgroundWin);
		} else {
			houseWinnings.setForeground(foregroundLoss);
			houseWinnings.setBackground(backgroundLoss);
		}

		add(houseWinnings, c);
		c.ipady = 0;
		c.insets = new Insets(5, 0, 5, 5);
		c.gridwidth = 1;
		c.gridx = maxCellWidth - 1;
		c.gridy = row++;
		JButton ok = getStyledButton("OK");
		ok.addActionListener(new FrameCloserListener(this));
		add(ok, c);
		rouletteMenu.clearStoredResults();
		pack();
		this.setTitle("Spin Result");
		setVisible(true);

	}

	private String betNumber(Bet b) {
		String pockets = null;

		switch (b.getBetType()) {
		case BLACK:
			pockets = "Black";
			break;
		case RED:
			pockets = "Red";
			break;
		case SPLIT_2:
			pockets = String.format("%d,%d", ((SplitBet) b).getFirstNumber(), ((SplitBet) b).getLastNumber());
			break;
		case SPLIT_4:
			pockets = String.format("%d,%d,%d,%d", ((SplitBet) b).getFirstNumber(),
					((SplitBet) b).getNumber(SplitPos.SECOND), ((SplitBet) b).getNumber(SplitPos.THIRD),
					((SplitBet) b).getLastNumber());
			break;
		case NUMBER:
		case ZERO:
			pockets = String.valueOf(((NumberBet) b).getNumber());
			break;

		}
		return pockets;
	}

//	private JLabel getTableJlabel(String content) {
//		JLabel betInfo = new JLabel(content);
//		Font betTableFont = new Font("Dialog", Font.PLAIN, 25);
//		betInfo.setOpaque(true);
//		betInfo.setFont(betTableFont);
//		betInfo.setHorizontalAlignment(JLabel.CENTER);
//		betInfo.setBorder(new EmptyBorder(0, 10, 0, 10));
//		return betInfo;
//
//	}

}
