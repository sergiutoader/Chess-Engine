

public class Knight extends Piece {

	public Knight(String position, boolean color, Game game){

		this.position = position;
		this.color = color;
		this.possibleMoves = new String[8];
		this.game = game;
	}

	public void updatePossibleMoves(){}
}