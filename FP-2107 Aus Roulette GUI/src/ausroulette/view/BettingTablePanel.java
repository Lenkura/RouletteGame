package ausroulette.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import ausroulette.controller.WindowResizeListener;
import ausroulette.model.bet.Bet;
import ausroulette.model.bet.NumberBet;
import ausroulette.model.bet.SplitBet;

@SuppressWarnings("serial")
public class BettingTablePanel extends JPanel implements PropertyChangeListener {
	private int width;
	private int height;
	private int fontAdjustFactor = 40;
	private Font numberFont = new Font("SANS_SERIF", Font.BOLD, 30);
	private Font wordFont = new Font("SANS_SERIF", Font.BOLD, 30);
	private Font betInfo = new Font("SANS_SERIF", Font.BOLD, 15);
	private boolean startup = false;

	ArrayList<Bet> madeBets = new ArrayList<>();
	ArrayList<BetTablePocketCoordinates> pocketCoordinates = new ArrayList<>();

	public BettingTablePanel() {
		this.setBackground(Color.GRAY);
		this.addComponentListener(new WindowResizeListener(this));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int maxColumns = 5;
		int maxRows = 13;
		int colorColumnSpan = 6;
		int colorColumnLabelAdjust = 2;
		int numberCenteringFactor = 10;

		int zeroAdjustFactor = 7;
		if (startup)
			if (this.getHeight() != height && this.getWidth() != width) {
				numberFont = new Font("SANS_SERIF", Font.BOLD, (this.getHeight() + this.getWidth()) / fontAdjustFactor);
				wordFont = new Font("SANS_SERIF", Font.BOLD, (this.getHeight() + this.getWidth()) / fontAdjustFactor);
				betInfo = new Font("SANS_SERIF", Font.BOLD,
						(this.getHeight() + this.getWidth()) / (fontAdjustFactor * 2));
				height = this.getHeight();
				width = this.getWidth();
			}
		startup = true;
		// create green 0 rectangle
		g.setColor(Color.GREEN);
		g.fillRect(2 * this.getWidth() / maxColumns, 1, ((maxColumns) * this.getWidth() / maxColumns),
				(this.getHeight() / maxRows));
		g.setColor(Color.WHITE);
		g.setFont(numberFont);
		g.drawString("0", zeroAdjustFactor * this.getWidth() / maxColumns / 2,
				this.getHeight() / (maxRows + zeroAdjustFactor));
		// create black side panel
		g.setColor(Color.BLACK);
		g.fillRect(0, this.getHeight() / maxRows, 2 * (this.getWidth() / maxColumns),
				colorColumnSpan * this.getHeight() / maxRows);
		g.setColor(Color.WHITE);
		g.setFont(wordFont);
		g.drawString("Black", 0, colorColumnLabelAdjust * this.getHeight() / (colorColumnSpan));
		// create red side panel
		g.setColor(Color.RED);
		g.fillRect(0, (this.getHeight() - colorColumnSpan * this.getHeight() / maxRows),
				2 * (this.getWidth() / maxColumns), colorColumnSpan * this.getHeight() / maxRows);
		g.setColor(Color.WHITE);
		g.setFont(wordFont);
		g.drawString("Red", 0,
				this.getHeight() - colorColumnSpan * this.getHeight() / maxRows / colorColumnLabelAdjust);
		int x;
		int y;
		int count = 1;
		for (int row = 1; row < maxRows; row++) {

			for (int col = 2; col < (maxColumns); col++) {

				// Set x coordinates of rectangle
				x = col * this.getWidth() / (maxColumns);

				// Set y coordinates of rectangle
				y = row * this.getHeight() / maxRows;

				BetTablePocketCoordinates btpc = new BetTablePocketCoordinates(count, x, y);
				pocketCoordinates.add(btpc);
				if (count % 2 == 0)
					g.setColor(Color.BLACK);
				else
					g.setColor(Color.RED);

				g.fillRect(x, y, (this.getWidth() / (maxColumns)), this.getHeight() / maxRows);

				g.setColor(Color.WHITE);
				g.setFont(numberFont);
				g.drawString(String.format("%d", count),
						x - numberCenteringFactor + (this.getWidth() / (maxColumns)) / 2,
						y + numberCenteringFactor + (this.getHeight() / maxRows) / 2);

				count++;
			}
		}

		if (madeBets.size() != 0) {
			g.setColor(Color.YELLOW);
			g.fillOval(0, 0, this.getWidth() / (maxColumns) / 3, (this.getHeight() / maxRows) / 3);
			g.setColor(Color.BLACK);
			g.setFont(betInfo);
			g.drawString("= Pocket has bet", this.getWidth() / (maxColumns) / 3 + 1,
					(this.getHeight() / maxRows) / 4);
		}
		double betIndicatorAdjustment = 0.9;
		for (Bet b : madeBets) {
			g.setColor(Color.YELLOW);
			int betMadeWidth = (int) (this.getWidth() / (maxColumns) * betIndicatorAdjustment);
			int betMadeHeight = (int) ((this.getHeight() / maxRows) * betIndicatorAdjustment);
			int pocketx = 0;
			int pockety = 0;
			switch (b.getBetType()) {
			case BLACK:
				g.fillOval(0, (this.getHeight() / maxRows) + ((this.getWidth() / maxColumns) / 2), betMadeWidth,
						betMadeHeight);
				break;
			case NUMBER:
				for (BetTablePocketCoordinates btpc : pocketCoordinates)
					if (btpc.getPocketNumber() == ((NumberBet) b).getNumber()) {
						pocketx = btpc.getX();
						pockety = btpc.getY();
					}
				betMadeWidth = betMadeWidth / 3;
				betMadeHeight = betMadeHeight / 3;
				g.fillOval(pocketx, pockety, betMadeWidth, betMadeHeight);
				break;
			case RED:
				g.fillOval(0, (this.getHeight() - colorColumnSpan * this.getHeight() / maxRows) + betMadeHeight,
						betMadeWidth, betMadeHeight);
				break;
			case SPLIT_2:
				for (BetTablePocketCoordinates btpc : pocketCoordinates)
					if (btpc.getPocketNumber() == ((SplitBet) b).getFirstNumber()) {
						pocketx = btpc.getX();
						pockety = btpc.getY();
					}
				betMadeWidth = betMadeWidth / 3;
				betMadeHeight = betMadeHeight / 3;
				g.fillOval(pocketx, pockety, betMadeWidth, betMadeHeight);

				for (BetTablePocketCoordinates btpc : pocketCoordinates)
					if (btpc.getPocketNumber() == ((SplitBet) b).getLastNumber()) {
						pocketx = btpc.getX();
						pockety = btpc.getY();
					}
				g.fillOval(pocketx, pockety, betMadeWidth, betMadeHeight);
				break;
			case SPLIT_4:
				int baseNumber = ((SplitBet) b).getFirstNumber();
				for (BetTablePocketCoordinates btpc : pocketCoordinates)
					if (btpc.getPocketNumber() == baseNumber) {
						pocketx = btpc.getX();
						pockety = btpc.getY();
					}
				betMadeWidth = betMadeWidth / 3;
				betMadeHeight = betMadeHeight / 3;
				g.fillOval(pocketx, pockety, betMadeWidth, betMadeHeight);

				for (BetTablePocketCoordinates btpc : pocketCoordinates)
					if (btpc.getPocketNumber() == baseNumber + 1) {
						pocketx = btpc.getX();
						pockety = btpc.getY();
					}
				g.fillOval(pocketx, pockety, betMadeWidth, betMadeHeight);
				for (BetTablePocketCoordinates btpc : pocketCoordinates)
					if (btpc.getPocketNumber() == baseNumber + 3) {
						pocketx = btpc.getX();
						pockety = btpc.getY();
					}
				g.fillOval(pocketx, pockety, betMadeWidth, betMadeHeight);
				for (BetTablePocketCoordinates btpc : pocketCoordinates)
					if (btpc.getPocketNumber() == baseNumber + 4) {
						pocketx = btpc.getX();
						pockety = btpc.getY();
					}
				g.fillOval(pocketx, pockety, betMadeWidth, betMadeHeight);
				break;
			case ZERO:
				g.fillOval(2 * this.getWidth() / maxColumns, 1, betMadeWidth, betMadeHeight);
				break;
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertyChangeNames.NEW_BET.toString())) {
			Bet bet = (Bet) evt.getNewValue();
			madeBets.add(bet);
			repaint();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.CANCEL_BET.toString())) {
			Bet bet = (Bet) evt.getOldValue();
			madeBets.remove(bet);
			repaint();
		}
		if (evt.getPropertyName().equals(PropertyChangeNames.SPIN_HOUSE_RESULT.toString())) {
			madeBets.clear();
			repaint();
		}

	}
}
