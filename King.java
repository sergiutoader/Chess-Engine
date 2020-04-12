
import java.util.*;

public class King extends Piece {

	public King(String position, boolean color, Game game) {

		this.position = position;
		this.color = color;
		this.possibleMoves = new ArrayList<String>();
		this.game = game;
		this.score = 20000;

	}

	public void updatePossibleMoves(boolean side, Game game) {
		
		// resetare arrayList possibleMoves 
		this.possibleMoves = new ArrayList<String>();
		int row, column;
		this.game = game;
		
		row = this.getRow();
		column = this.getColumn();

		if (row > 0 && game.freeSpace(row - 1, column, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row - 1, column));

		}
		if (row < 7 && game.freeSpace(row + 1, column, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row + 1, column));
		}
		if (column > 0 && game.freeSpace(row, column - 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row, column - 1));
		}
		if (column < 7 && game.freeSpace(row, column + 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row, column + 1));
		}
		if (row > 0 && column > 0 && game.freeSpace(row - 1, column - 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row - 1, column - 1));
		}
		if (row > 0 && column < 7 && game.freeSpace(row - 1, column + 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row - 1, column + 1));
		}
		if (row < 7 && column > 0 && game.freeSpace(row + 1, column - 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row + 1, column - 1));
		}
		if (row < 7 && column < 7 && game.freeSpace(row + 1, column + 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row + 1, column + 1));
		}
	}

}