
public class Bishop extends Piece {

	public Bishop(String position, boolean color, Game game){

		this.position = position;
		this.color = color;
		this.possibleMoves = new String[13];
		this.game = game;		
	}

	public void updatePossibleMoves(){}
}