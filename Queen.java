
public class Queen extends Piece {

	public Queen(String position, boolean color, Game game){

		this.position = position;
		this.color = color;
		this.possibleMoves = new String[27];
		this.game = game;
		
	}

	public void updatePossibleMoves(){}
}