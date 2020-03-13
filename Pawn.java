
import java.util.*;

public class Pawn extends Piece {

	public Pawn(String position, boolean color, Game game) {

		this.position = position;
		this.color = color;
		// Pionul poate avea maxim 4 mutari posibile la un moment de timp
		this.possibleMoves = new ArrayList<String>();
		this.game = game;

	}

	public void updatePossibleMoves(boolean side) {

		// resetare arrayList possibleMoves
		this.possibleMoves = new ArrayList<String>();

		int row = this.getRow();
		int column = this.getColumn();

		if (side == false) {

			// cucerire piesa stanga fata (negru)
			if ((row > 0) && (column > 0) && (!(this.game.getPiece(row - 1, column - 1) instanceof Empty))
					&& (this.game.getPiece(row - 1, column - 1).color == true)) {

				this.possibleMoves.add(this.game.getPosition(row - 1, column - 1));
			}

			// cucerire piesa dreapta fata (negru)
			if ((row > 0) && (column < 7) && (!(this.game.getPiece(row - 1, column + 1) instanceof Empty))
					&& (this.game.getPiece(row - 1, column + 1).color == true)) {

				this.possibleMoves.add(this.game.getPosition(row - 1, column + 1));
			}

			// mutare 2 pozitii in fata(negru)
			if ((row == 6) && (this.game.getPiece(row - 1, column) instanceof Empty)
					&& (this.game.getPiece(row - 2, column) instanceof Empty)) {

				this.possibleMoves.add(this.game.getPosition(row - 2, column));
			}

			// mutare o pozitie in fata (negru)
			if ((row > 0) && (this.game.getPiece(row - 1, column) instanceof Empty)) {

				this.possibleMoves.add(this.game.getPosition(row - 1, column));
			}
		} else {

			// cucerire piesa stanga fata (alb)
			if ((row < 7) && (column > 0) && (!(this.game.getPiece(row + 1, column - 1) instanceof Empty))
					&& (this.game.getPiece(row + 1, column - 1).color == false)) {

				this.possibleMoves.add(this.game.getPosition(row + 1, column - 1));
			}

			// cucerire piesa dreapta fata (alb)
			if ((row < 7) && (column < 7) && (!(this.game.getPiece(row + 1, column + 1) instanceof Empty))
					&& (this.game.getPiece(row + 1, column + 1).color == false)) {

				this.possibleMoves.add(this.game.getPosition(row + 1, column + 1));
			}

			// mutare 2 pozitii in fata(alb)
			if ((row == 1) && (this.game.getPiece(row + 1, column) instanceof Empty)
					&& (this.game.getPiece(row + 2, column) instanceof Empty)) {

				this.possibleMoves.add(this.game.getPosition(row + 2, column));
			}

			// mutare o pozitie in fata (alb)
			if ((row < 7) && (this.game.getPiece(row + 1, column) instanceof Empty)) {

				this.possibleMoves.add(this.game.getPosition(row + 1, column));
			}
		}

		// TODO - regula en passant
	}
}