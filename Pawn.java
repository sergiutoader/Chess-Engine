
public class Pawn extends Piece{

	public Pawn(String position, boolean color, Game game){

		this.position = position;
		this.color = color;
		// Pionul poate avea maxim 4 mutari posibile la un moment de timp
		this.possibleMoves = new String[4];
		this.game = game;
		
	}

	public void updatePossibleMoves(){
		int idx = 0;

		// resetare vector possibleMoves
		for(int i = 0 ; i < 4; i++){
			this.possibleMoves[i] = null;
		}

		// cucerire piesa stanga fata (negru)
		if( (this.getRow() > 0)
			&& (this.getColumn() > 0)
			&& (!(this.game.getPiece(this.getRow() - 1, this.getColumn() - 1) instanceof Empty))
			&& (this.game.getPiece(this.getRow() - 1, this.getColumn() - 1).color == true)){
			
			this.possibleMoves[idx++] = this.game.getPosition(
				this.getRow() - 1, this.getColumn() - 1);
		}

		// cucerire piesa dreapta fata (negru)
		if( (this.getRow() > 0)
			&& (this.getColumn() < 7)
			&& (!(this.game.getPiece(this.getRow() - 1, this.getColumn() + 1) instanceof Empty))
			&& (this.game.getPiece(this.getRow() - 1, this.getColumn() + 1).color == true) ){
			
			this.possibleMoves[idx++] = this.game.getPosition(
				this.getRow() - 1, this.getColumn() + 1);
		}

		// mutare 2 pozitii in fata(negru)
		if( (this.getRow() == 6)
			&& (this.game.getPiece(this.getRow() - 1, this.getColumn()) instanceof Empty)
			&& (this.game.getPiece(this.getRow() - 2, this.getColumn()) instanceof Empty)){
			
			this.possibleMoves[idx++] = this.game.getPosition(
				this.getRow() - 2, this.getColumn());
		}

		// mutare o pozitie in fata (negru)
		if((this.getRow() > 0)
			&& (this.game.getPiece(this.getRow() - 1, this.getColumn()) instanceof Empty)){
			
			this.possibleMoves[idx++] = this.game.getPosition(
				this.getRow() - 1, this.getColumn());
		}

		// TODO - regula en poissant
	}
}