
import java.util.*;

public class Pawn extends Piece{

	public Pawn(String position, boolean color, Game game){

		this.position = position;
		this.color = color;
		// Pionul poate avea maxim 4 mutari posibile la un moment de timp
		this.possibleMoves = new ArrayList <String>();
		this.game = game;
		
	}

	public void updatePossibleMoves(boolean side){

		// resetare vector possibleMoves
		this.possibleMoves = new ArrayList <String>();

		if(side == false) {
		
			// cucerire piesa stanga fata (negru)
			if( (this.getRow() > 0)
				&& (this.getColumn() > 0)
				&& (!(this.game.getPiece(this.getRow() - 1, this.getColumn() - 1) instanceof Empty))
				&& (this.game.getPiece(this.getRow() - 1, this.getColumn() - 1).color == true)){
				
				this.possibleMoves.add(this.game.getPosition(this.getRow() - 1, this.getColumn() - 1) );
			}
	
			// cucerire piesa dreapta fata (negru)
			if( (this.getRow() > 0)
				&& (this.getColumn() < 7)
				&& (!(this.game.getPiece(this.getRow() - 1, this.getColumn() + 1) instanceof Empty))
				&& (this.game.getPiece(this.getRow() - 1, this.getColumn() + 1).color == true) ){
				
				this.possibleMoves.add(this.game.getPosition(this.getRow() - 1, this.getColumn() + 1) );
			}
	
			// mutare 2 pozitii in fata(negru)
			if( (this.getRow() == 6)
				&& (this.game.getPiece(this.getRow() - 1, this.getColumn()) instanceof Empty)
				&& (this.game.getPiece(this.getRow() - 2, this.getColumn()) instanceof Empty)){
				
				this.possibleMoves.add(this.game.getPosition(this.getRow() - 2, this.getColumn() ) );
			}
	
			// mutare o pozitie in fata (negru)
			if((this.getRow() > 0)
				&& (this.game.getPiece(this.getRow() - 1, this.getColumn()) instanceof Empty)){
				
				this.possibleMoves.add(this.game.getPosition(this.getRow() - 1, this.getColumn() ) );
			}
		}
		else {
			
			// cucerire piesa stanga fata (alb)
			if( (this.getRow() < 7)
				&& (this.getColumn() > 0)
				&& (!(this.game.getPiece(this.getRow() + 1, this.getColumn() - 1) instanceof Empty))
				&& (this.game.getPiece(this.getRow() + 1, this.getColumn() - 1).color == false)){
				
				this.possibleMoves.add(this.game.getPosition(this.getRow() + 1, this.getColumn() - 1) );
			}
	
			// cucerire piesa dreapta fata (alb)
			if( (this.getRow() < 7)
				&& (this.getColumn() < 7)
				&& (!(this.game.getPiece(this.getRow() + 1, this.getColumn() + 1) instanceof Empty))
				&& (this.game.getPiece(this.getRow() + 1, this.getColumn() + 1).color == false) ){
				
				this.possibleMoves.add(this.game.getPosition(this.getRow() + 1, this.getColumn() + 1) );
			}
	
			// mutare 2 pozitii in fata(alb)
			if( (this.getRow() == 1)
				&& (this.game.getPiece(this.getRow() + 1, this.getColumn()) instanceof Empty)
				&& (this.game.getPiece(this.getRow() + 2, this.getColumn()) instanceof Empty)){
				
				this.possibleMoves.add(this.game.getPosition(this.getRow() + 2, this.getColumn() ) );
			}
	
			// mutare o pozitie in fata (alb)
			if((this.getRow() < 7)
				&& (this.game.getPiece(this.getRow() + 1, this.getColumn()) instanceof Empty)){
				
				this.possibleMoves.add(this.game.getPosition(this.getRow() + 1, this.getColumn() ) );
			}
		}

		// TODO - regula en passant
	}
}