
import java.util.*;

public class King extends Piece {

	public King(String position, boolean color, Game game) {

		this.position = position;
		this.color = color;
		this.possibleMoves = new ArrayList<String>();
		this.game = game;
		this.score = 20000;
		this.hasMoved = false;
	}

	public King(String position, boolean color, Game game, boolean hasMoved) {
		this(position, color, game);
		this.hasMoved = hasMoved;
	}

	public void updatePossibleMoves(boolean side, Game game) {
		
		// resetare arrayList possibleMoves 
		this.possibleMoves = new ArrayList<String>();
		int row, column;
		this.game = game;
		
		row = this.getRow();
		column = this.getColumn();
		// verificare rocada
		if(game.castling == false && side == true && this.hasMoved == false) {

			if(game.grid[0][7] instanceof Rook && game.grid[0][7].hasMoved == false) {
				if(game.grid[0][5] instanceof Empty && game.grid[0][6] instanceof Empty) {


					Game copy = game.cloneGame(game);
					copy.side = !(copy.side);
					boolean isCheck = false;
					String[] positions = {"e1","f1"};
					for(int i = 0 ; i < 8; i++) {
						for(int j = 0; j < 8; j++) {
							if(copy.grid[i][j] instanceof Empty == false && copy.grid[i][j] instanceof King == false && copy.grid[i][j].color == copy.side) {
								copy.grid[i][j].updatePossibleMoves(copy.side, copy);
								for(int t = 0; t < copy.grid[i][j].possibleMoves.size(); t++) {
									for(int k = 0; k < 2; k++) {
										if(copy.grid[i][j].possibleMoves.get(t).indexOf(positions[k]) >= 0) {
											isCheck = true;
											break;
										}
									}
									if(isCheck) break;
								}
							}
							if(isCheck) break;
						}
						if(isCheck) break;
					}

					if(isCheck == false) {
						this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(0, 6));
					}
				}
			} 
			if(game.castling == false && game.grid[0][0] instanceof Rook && game.grid[0][0].hasMoved == false) {
				if(game.grid[0][1] instanceof Empty && game.grid[0][2] instanceof Empty && game.grid[0][3] instanceof Empty) {
					
					Game copy = game.cloneGame(game);
					copy.side = !(copy.side);
					boolean isCheck = false;
					String[] positions = {"e1","d1","c1"};
					for(int i = 0 ; i < 8; i++) {
						for(int j = 0; j < 8; j++) {
							if(copy.grid[i][j] instanceof Empty == false && copy.grid[i][j] instanceof King == false && copy.grid[i][j].color == copy.side) {
								copy.grid[i][j].updatePossibleMoves(copy.side, copy);
								for(int t = 0; t < copy.grid[i][j].possibleMoves.size(); t++) {
									for(int k = 0; k < 2; k++) {
										if(copy.grid[i][j].possibleMoves.get(t).indexOf(positions[k]) >= 0) {
											isCheck = true;
											break;
										}
									}
									if(isCheck) break;
								}
							}
							if(isCheck) break;
						}
						if(isCheck) break;
					}

					if(isCheck == false) {
						this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(0, 2));
					}
					
				}
			}
		} else if(side == false && this.hasMoved == false) {
			if(game.grid[7][7] instanceof Rook && game.grid[7][7].hasMoved == false) {
				if(game.grid[7][5] instanceof Empty && game.grid[7][6] instanceof Empty) {
					Game copy = game.cloneGame(game);
					copy.side = !(copy.side);
					boolean isCheck = false;
					String[] positions = {"e8","f8"};
					for(int i = 0 ; i < 8; i++) {
						for(int j = 0; j < 8; j++) {
							if(copy.grid[i][j] instanceof Empty == false && copy.grid[i][j] instanceof King == false && copy.grid[i][j].color == copy.side) {
								copy.grid[i][j].updatePossibleMoves(copy.side, copy);
								for(int t = 0; t < copy.grid[i][j].possibleMoves.size(); t++) {
									for(int k = 0; k < 2; k++) {
										if(copy.grid[i][j].possibleMoves.get(t).indexOf(positions[k]) >= 0) {
											isCheck = true;
											break;
										}
									}
									if(isCheck) break;
								}
							}
							if(isCheck) break;
						}
						if(isCheck) break;
					}

					if(isCheck == false){
						this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(7, 6));
					}
					
				}
			}
			if(game.grid[7][0] instanceof Rook && game.grid[7][0].hasMoved == false) {
				if(game.grid[7][1] instanceof Empty && game.grid[7][2] instanceof Empty && game.grid[7][3] instanceof Empty) {
					Game copy = game.cloneGame(game);
					copy.side = !(copy.side);
					boolean isCheck = false;
					String[] positions = {"e8","d8","c8"};
					for(int i = 0 ; i < 8; i++) {
						for(int j = 0; j < 8; j++) {
							if(copy.grid[i][j] instanceof Empty == false && copy.grid[i][j] instanceof King == false && copy.grid[i][j].color == copy.side) {
								copy.grid[i][j].updatePossibleMoves(copy.side, copy);
								for(int t = 0; t < copy.grid[i][j].possibleMoves.size(); t++) {
									for(int k = 0; k < 2; k++) {
										if(copy.grid[i][j].possibleMoves.get(t).indexOf(positions[k]) >= 0) {
											isCheck = true;
											break;
										}
									}
									if(isCheck) break;
								}
							}
							if(isCheck) break;
						}
						if(isCheck) break;
					}

					if(isCheck == false) {
						this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(7, 2));
					}
				}
			}
		}

		if (row > 0 && game.freeSpace(row - 1, column, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row - 1, column));

		}
		if (row < 7 && game.freeSpace(row + 1, column, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row + 1, column));
		}
		if (column > 0 && game.freeSpace(row, column - 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row, column - 1));
		}
		if (column < 7 && game.freeSpace(row, column + 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row, column + 1));
		}
		if (row > 0 && column > 0 && game.freeSpace(row - 1, column - 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row - 1, column - 1));
		}
		if (row > 0 && column < 7 && game.freeSpace(row - 1, column + 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row - 1, column + 1));
		}
		if (row < 7 && column > 0 && game.freeSpace(row + 1, column - 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row + 1, column - 1));
		}
		if (row < 7 && column < 7 && game.freeSpace(row + 1, column + 1, side)) {
			this.possibleMoves.add(game.getPosition(row, column) + game.getPosition(row + 1, column + 1));
		}
	}

}