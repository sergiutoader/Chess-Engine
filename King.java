public class King extends Piece {

	public King(String position, boolean color, Game game){

		this.position = position;
		this.color = color;
		this.possibleMoves = new String[8];
		this.game = game;	
		
	}

	public void updatePossibleMoves(){}
}