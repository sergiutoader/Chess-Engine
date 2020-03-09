
public class Rook extends Piece {

	public Rook(String position, boolean color, Game game){

		this.position = position;
		this.color = color;
		this.possibleMoves = new String[14];
		this.game = game;	
		
	}

	public void updatePossibleMoves(){}
}