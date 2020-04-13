import java.io.*;
import java.util.ArrayList;

class Pair<F, S> {
    public F first;
    public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}

public class Game {
	public Piece[][] grid;

	public boolean side; // true = white, false = black;
	public ArrayList<String> allMoves;
	public Piece getPiece(int row, int column) {
		return this.grid[row][column];
	}

	public Game(boolean side) {
		this.side = side;
		this.grid = new Piece[8][8];

		// Spatii goale
		for (int i = 2; i < 6; i++) {
			for (int j = 0; j <= 7; j++) {
				grid[i][j] = new Empty(getPosition(i, j), false, this);
			}
		}
		// Pioni
		for (int j = 0; j <= 7; j++) {
			grid[1][j] = new Pawn(getPosition(1, j), true, this);
			grid[6][j] = new Pawn(getPosition(6, j), false, this);
		}

		// Cai
		grid[0][1] = new Knight(getPosition(0, 1), true, this);
		grid[0][6] = new Knight(getPosition(0, 6), true, this);
		grid[7][1] = new Knight(getPosition(7, 1), false, this);
		grid[7][6] = new Knight(getPosition(7, 6), false, this);

		// Nebuni
		grid[0][2] = new Bishop(getPosition(0, 2), true, this);
		grid[0][5] = new Bishop(getPosition(0, 5), true, this);
		grid[7][2] = new Bishop(getPosition(7, 2), false, this);
		grid[7][5] = new Bishop(getPosition(7, 5), false, this);

		// Ture
		grid[0][0] = new Rook(getPosition(0, 0), true, this);
		grid[0][7] = new Rook(getPosition(0, 7), true, this);
		grid[7][0] = new Rook(getPosition(7, 0), false, this);
		grid[7][7] = new Rook(getPosition(7, 7), false, this);

		// Regine
		grid[0][3] = new Queen(getPosition(0, 3), true, this);
		grid[7][3] = new Queen(getPosition(7, 3), false, this);

		// Regii
		grid[0][4] = new King(getPosition(0, 4), true, this);
		grid[7][4] = new King(getPosition(7, 4), false, this);

	}

	public Game(Boolean side, Piece[][] grid) {
		this.side = side;
		this.grid = grid;
	}

	// pentru debug
	public int evalByColor(Piece[][] grid, Boolean side) {
		int i, j;
		int score = 0;
		for(i = 0; i <= 7; i++) {
			for(j = 0; j <= 7; j++) {
				if(grid[i][j] instanceof Pawn && (grid[i][j].color == side)) {
					if(side == true) {
						score += PieceSquareValues.WhitePawnSquare[i][j];
					} else {
						score += PieceSquareValues.BlackPawnSquare[i][j];
					}
				}

				if(grid[i][j] instanceof Pawn && (grid[i][j].color != side)) {
					if(side == true) {
						score -= PieceSquareValues.WhitePawnSquare[i][j];
					} else {
						score -= PieceSquareValues.BlackPawnSquare[i][j];
					}
					
				}

				if (!(grid[i][j] instanceof Empty) && (grid[i][j].color == side)) {
					score += grid[i][j].score;
				} else if (!(grid[i][j] instanceof Empty) && (grid[i][j].color != side)){
					score -= grid[i][j].score;
				}
			}
		}

		return score;
	}

	// Functia de evaluare a grid-ului
	public int eval(Game game) {
		int i, j;
		int score = 0;
		for(i = 0; i <= 7; i++) {
			for(j = 0; j <= 7; j++) {

				// punctare in functie de pozitia pe care se afla pionii
				if(game.grid[i][j] instanceof Pawn && (game.grid[i][j].color == game.side)) {
					if(game.side == true) {
						score += PieceSquareValues.WhitePawnSquare[i][j];
					} else {
						score += PieceSquareValues.BlackPawnSquare[i][j];
					}
					
				}

				if(game.grid[i][j] instanceof Pawn && (game.grid[i][j].color != game.side)) {
					if(game.side == true) {
						score -= PieceSquareValues.WhitePawnSquare[i][j];
					} else {
						score -= PieceSquareValues.BlackPawnSquare[i][j];
					}
					
				}

				if (!(game.grid[i][j] instanceof Empty) && (game.grid[i][j].color == game.side)) {
					score += game.grid[i][j].score;
				} else if (!(game.grid[i][j] instanceof Empty) && (game.grid[i][j].color != game.side)){
					score -= game.grid[i][j].score;
				}
			}
		}
		return score;
	}


	// intorce o lista de mutari complete (care contin atat sursa cat si destinatia)
	public void updateAllPossibleMoves(Game game) {
		int i, j;
		game.allMoves = new ArrayList<String>();
		for(i = 0; i <= 7; i++) {
			for(j = 0; j <= 7; j++) {
				if (!(game.grid[i][j] instanceof Empty) && (game.grid[i][j].color == game.side)) {
					game.grid[i][j].updatePossibleMoves(game.side, game);
					for(String s : game.grid[i][j].possibleMoves) {
						game.allMoves.add(s);
					}
				}
			}
		}
	}

	// verifica daca numarul de regi de pe grid este diferit de 2
	public boolean ended(Game game) {
		int kingCount = 0;
		for (int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(game.grid[i][j] instanceof King) {
					kingCount++;
				}
			}
		}
		return kingCount != 2;
	}

	// Algoritm minimax
	public Pair <Integer, String> minimax(Game game, int depth) {
		if(depth == 0 || game.ended(game)) {
			return new Pair<Integer, String>(game.eval(game), "");
		}

		game.updateAllPossibleMoves(game);		

		if(game.allMoves.isEmpty()) {
			return new Pair<Integer, String>(-100000, "");
		}

		int bestScore = -100000;
		String bestMove = "";

		for(String move : game.allMoves) {
			Game copy = game.cloneGame(game);
			copy.applyMove(copy, move);

			copy.side = !(copy.side);

			int score = -minimax(copy, depth - 1).first;

			if(score > bestScore) {
				bestScore = score;
				bestMove = move; 
			}

		}
		
		return new Pair <Integer, String> (bestScore, bestMove);
	}

	// metoda care actualizeaza grid-ul in urma unei mutari
	public void applyMove(Game game, String move) {

		int i = getRow(move.substring(0, 2));
		int j = getColumn(move.substring(0, 2));
		String nextPosition = move.substring(2);
		int nextRow = getRow(nextPosition);
		int nextColumn = getColumn(nextPosition);

		if (game.grid[i][j] instanceof Pawn && game.grid[i][j].color == game.side) {
			if(game.side == false) {
				game.pawnBlackMove(game, move);
					
			} else {
				game.pawnWhiteMove(game, move);
			}
		}
		else {
			game.grid[nextRow][nextColumn] = game.grid[i][j];	
			game.grid[nextRow][nextColumn].position = game.getPosition(nextRow, nextColumn);

			game.grid[i][j] = new Empty(getPosition(i, j), false, game);
		}	

	}

	// Functie care trimite o comanda la xboard in functie de ce intoarce algoritmul minimax
	public void makeMove(BufferedOutputStream bout) throws IOException {
		Pair <Integer, String> pair = minimax(this, 3);

		String move = pair.second;

		if(move.equals("")) {
			bout.write(String.format("resign\n").getBytes());
			bout.flush();
			return;
		}
		
		this.applyMove(this, move);

		// afisare mutare
		bout.write(String.format("move " + move + "\n").getBytes());
		bout.flush();
	}

	// metoda folosita in cazul in care engine-ul joaca pe alb si are mutare legala pentru un pion
	private void pawnWhiteMove(Game game, String move) {

		int i = getRow(move.substring(0, 2));
		int j = getColumn(move.substring(0, 2));

		String nextPosition = move.substring(2);
		int nextRow = getRow(nextPosition);
		int nextColumn = getColumn(nextPosition);

		// daca este o mutare de tip en passant, se elimina pionul din spatele
		// destinatiei
		if (game.grid[i][j].enPassant != null) {
			int row = game.getRow(nextPosition) - 1;
			int column = game.getColumn(nextPosition);
			game.grid[row][column] = new Empty(game.getPosition(row, column), false, game);
			// se reseteaza campul pentru pionul mutat
			game.grid[i][j].enPassant = null;
		}

		// daca pionul ajunge in baza oponentului, se transforma in regina
		if (game.grid[i][j] instanceof Pawn && nextRow == 7) {
			game.grid[nextRow][nextColumn] = new Queen(
					nextPosition, true, game);
		} else {
			// actualizare grid
			game.grid[nextRow][nextColumn] = game.grid[i][j];
			// actualizare camp pentru pozitie
			game.grid[nextRow][nextColumn].position = nextPosition;
		}

		// setare camp gol pe pozitia anterioara
		game.grid[i][j] = new Empty(getPosition(i, j), false, game);

	}

		// metoda folosita in cazul in care engine-ul joaca pe alb si are mutare legala pentru un pion
	private void pawnBlackMove(Game game, String move) {
		
		int i = getRow(move.substring(0, 2));
		int j = getColumn(move.substring(0, 2));

		String nextPosition = move.substring(2);
		int nextRow = getRow(nextPosition);
		int nextColumn = getColumn(nextPosition);

		// daca este o mutare de tip en passant, se elimina pionul din spatele
		// destinatiei
		if (game.grid[i][j].enPassant != null) {
			int row = game.getRow(nextPosition) + 1;
			int column = game.getColumn(nextPosition);
			game.grid[row][column] = new Empty(game.getPosition(row, column), false, game);
			// se reseteaza campul pentru pionul mutat
			game.grid[i][j].enPassant = null;
		}

		// daca pionul ajunge in baza oponentului, se transforma in regina
		if (game.getRow(nextPosition) == 0) {
			game.grid[nextRow][nextColumn] = new Queen(nextPosition, false, game);
		} else {

			// actualizare grid
			game.grid[nextRow][nextColumn] = game.grid[i][j];
			// actualizare camp pentru pozitie
			game.grid[nextRow][nextColumn].position = nextPosition;
		}
		// setare camp gol pe pozitia anterioara
		game.grid[i][j] = new Empty(getPosition(i, j), false, game);
	}

	public String getKingPosition(Game game) {
		String kingPosition = "";
		int i, j, k;
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if(game.grid[i][j] instanceof King && game.grid[i][j].color == game.side) {
					kingPosition = game.getPosition(i, j);
					return kingPosition;
				}
			}
		}
		return null;
	}

	// verifica daca regele engine-ului se afla in sah
	public boolean isCheck(Game game) {

		String kingPosition = game.getKingPosition(game);
		int i, j, k;

		if(kingPosition == null) {
			return true;
		}

		Game copy = game.cloneGame(game);
		copy.side = !(copy.side);

		copy.updateAllPossibleMoves(copy);
		for(k = 0; k < copy.allMoves.size(); k++) {
			if(copy.allMoves.get(k) != null && copy.allMoves.get(k).indexOf(kingPosition) >= 0) {
				return true;
			}
		}
		return false;

	}


	// Alte functii ajutatoare

	// intoarce pozitia sub forma de string
	public String getPosition(int row, int column) {
		StringBuilder result = new StringBuilder();
		result.append((char) ('a' + column));
		result.append(row + 1);
		return result.toString();
	}

	// metode care intorc indicele si coloana in functie de string

	public int getRow(String position) {
		return position.charAt(1) - '0' - 1;
	}

	public int getColumn(String position) {
		return position.charAt(0) - 'a';
	}

	// vad daca in spatiul cautat se afla cumva vreo piesa de-a mea
	public boolean freeSpace(int row, int column, boolean color) {
		if (this.grid[row][column].color == color && (this.grid[row][column] instanceof Empty == false)) {
			return false;
		}
		return true;
	}

	// intoarce o clona a piesei p
	public Piece clonePiece(Piece p) {
		if(p instanceof Empty) {
			return new Empty(p.position, p.color, p.game);
		}
		if(p instanceof Pawn) {
			return new Pawn(p.position, p.color, p.game);
		}
		if(p instanceof Knight) {
			return new Knight(p.position, p.color, p.game);
		}
		if(p instanceof Bishop) {
			return new Bishop(p.position, p.color, p.game);
		}
		if(p instanceof Rook) {
			return new Rook(p.position, p.color, p.game);
		}
		if(p instanceof Queen) {
			return new Queen(p.position, p.color, p.game);
		}
		if(p instanceof King) {
			return new King(p.position, p.color, p.game);
		}
		return new Empty(p.position, p.color, p.game);
	}

	public Piece[][] cloneGrid(Piece [][] grid) {
		Piece[][] newGrid = new Piece[8][8];
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				newGrid[i][j] = clonePiece(grid[i][j]);
			}
		}
		return newGrid;
	}

	// creaza o copie a jocului cu culoarea opusa celei curente
	public Game cloneGame(Game game) {
		return new Game((game.side), game.cloneGrid(game.grid));
	}




	// metoda folosita pentru debug - afiseaza in fisierul grid.txt grid-ul
	// daca fisierul e deschis cu sublime, se va actualiza dupa fiecare mutare
	// a engine-ului

	public void showGrid() {
		StringBuilder gridb = new StringBuilder();
		for (int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if (grid[i][j] instanceof Empty) {
					gridb.append("0  ");
				}

				else if (grid[i][j].color == false) {
					if (grid[i][j] instanceof Pawn) {
						gridb.append("p  ");
					}
					if (grid[i][j] instanceof Bishop) {
						gridb.append("b  ");
					}
					if (grid[i][j] instanceof Knight) {
						gridb.append("kn ");
					}
					if (grid[i][j] instanceof King) {
						gridb.append("ki ");
					}
					if (grid[i][j] instanceof Queen) {
						gridb.append("q  ");
					}
					if (grid[i][j] instanceof Rook) {
						gridb.append("r  ");
					}

				} else {
					if (grid[i][j] instanceof Pawn) {
						gridb.append("P  ");
					}
					if (grid[i][j] instanceof Bishop) {
						gridb.append("B  ");
					}
					if (grid[i][j] instanceof Knight) {
						gridb.append("KN ");
					}
					if (grid[i][j] instanceof King) {
						gridb.append("KI ");
					}
					if (grid[i][j] instanceof Queen) {
						gridb.append("Q  ");
					}
					if (grid[i][j] instanceof Rook) {
						gridb.append("R  ");
					}

				}

				if (j == 7) {
					gridb.append("\n");
				}
			}
		}

		System.out.println(gridb.toString() + "\n");

	}

	public void printGrid() throws FileNotFoundException {
		StringBuilder gridb = new StringBuilder();
		PrintWriter wr = new PrintWriter(new File("grid.txt"));
		wr.append("Engine evaluates to " + this.eval(this) + "\n");
		for (int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if (grid[i][j] instanceof Empty) {
					gridb.append("0  ");
				}

				else if (grid[i][j].color == false) {
					if (grid[i][j] instanceof Pawn) {
						gridb.append("p  ");
					}
					if (grid[i][j] instanceof Bishop) {
						gridb.append("b  ");
					}
					if (grid[i][j] instanceof Knight) {
						gridb.append("kn ");
					}
					if (grid[i][j] instanceof King) {
						gridb.append("ki ");
					}
					if (grid[i][j] instanceof Queen) {
						gridb.append("q  ");
					}
					if (grid[i][j] instanceof Rook) {
						gridb.append("r  ");
					}

				} else {
					if (grid[i][j] instanceof Pawn) {
						gridb.append("P  ");
					}
					if (grid[i][j] instanceof Bishop) {
						gridb.append("B  ");
					}
					if (grid[i][j] instanceof Knight) {
						gridb.append("KN ");
					}
					if (grid[i][j] instanceof King) {
						gridb.append("KI ");
					}
					if (grid[i][j] instanceof Queen) {
						gridb.append("Q  ");
					}
					if (grid[i][j] instanceof Rook) {
						gridb.append("R  ");
					}

				}

				if (j == 7) {
					gridb.append("\n");
				}
			}
		}
		wr.append(gridb.toString() + "\n");
		wr.close();
	}
}