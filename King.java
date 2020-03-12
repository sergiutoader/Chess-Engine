
import java.util.*;

public class King extends Piece {

	public King(String position, boolean color, Game game){

		this.position = position;
		this.color = color;
		this.possibleMoves = new ArrayList <String>();
		this.game = game;	
		
	}

	public void updatePossibleMoves(boolean side){
		
		/*int row, column;
		Game game;
		
		row = this.getRow();
		column = this.getColumn();
		game = this.game;
		
		if(row > 0 && game.freeSpace(row - 1, column) && game.isCheck(game.getPosition(row - 1, column) ) )
			this.possibleMoves.add(game.getPosition(row - 1, column) );
		if(row < 7 && game.freeSpace(row + 1, column) && game.isCheck(game.getPosition(row + 1, column) ) )
			this.possibleMoves.add(game.getPosition(row + 1, column) );
		if(column > 0 && game.freeSpace(row, column - 1) && game.isCheck(game.getPosition(row, column - 1) ) )
			this.possibleMoves.add(game.getPosition(row, column - 1) );
		if(column < 7 && game.freeSpace(row, column + 1) && game.isCheck(game.getPosition(row, column + 1) ) )
			this.possibleMoves.add(game.getPosition(row, column + 1) );
		
		if(row > 0 && column > 0 && game.freeSpace(row - 1, column - 1) && game.isCheck(game.getPosition(row - 1, column - 1) ) )
			this.possibleMoves.add(game.getPosition(row - 1, column - 1) );
		if(row > 0 && column < 7 && game.freeSpace(row - 1, column + 1) && game.isCheck(game.getPosition(row - 1, column + 1) ) )
			this.possibleMoves.add(game.getPosition(row - 1, column + 1) );
		if(row < 7 && column > 0 && game.freeSpace(row + 1, column - 1) && game.isCheck(game.getPosition(row + 1, column - 1) ) )
			this.possibleMoves.add(game.getPosition(row + 1, column - 1) );
		if(row < 7 && column < 7 && game.freeSpace(row + 1, column + 1) && game.isCheck(game.getPosition(row + 1, column + 1) ) )
			this.possibleMoves.add(game.getPosition(row + 1, column + 1) );
		*/
	}
}