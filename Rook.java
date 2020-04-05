
import java.util.*;

public class Rook extends Piece {

	public Rook(String position, boolean color, Game game){

		this.position = position;
		this.color = color;
		this.possibleMoves = new ArrayList <String>();
		this.game = game;
		this.score = 5;
	}

	public void updatePossibleMoves(boolean side) {
		
		// resetare arrayList possibleMoves
		this.possibleMoves = new ArrayList <String>();
		
		int row, column, i, j;
		Game game;
		
		row = this.getRow();
		column = this.getColumn();
		game = this.game;
		
		// Sus
		i = row;
		j = column;
		while(true) {
			i++;
			
			if(i < 0 || i > 7 || j < 0 || j > 7)break;
			
			if(game.freeSpace(i, j, this.color) )this.possibleMoves.add(game.getPosition(i, j) );
			if(!(game.getPiece(i, j) instanceof Empty) )break;
		}
		
		// Jos
		i = row;
		j = column;
		while(true) {
			i--;
			
			if(i < 0 || i > 7 || j < 0 || j > 7)break;
			
			if(game.freeSpace(i, j, this.color) )this.possibleMoves.add(game.getPosition(i, j) );
			if(!(game.getPiece(i, j) instanceof Empty) )break;
		}
				
		// Stanga
		i = row;
		j = column;
		while(true) {
			j--;
			
			if(i < 0 || i > 7 || j < 0 || j > 7)break;
			
			if(game.freeSpace(i, j, this.color) )this.possibleMoves.add(game.getPosition(i, j) );
			if(!(game.getPiece(i, j) instanceof Empty) )break;
		}
		
		// Dreapta
		i = row;
		j = column;
		while(true) {
			j++;
			
			if(i < 0 || i > 7 || j < 0 || j > 7)break;
			
			if(game.freeSpace(i, j, this.color) )this.possibleMoves.add(game.getPosition(i, j) );
			if(!(game.getPiece(i, j) instanceof Empty) )break;
		}
	}
}