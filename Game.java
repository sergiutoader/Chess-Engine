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
		grid = new Piece[8][8];

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

	// Functia de evaluare a grid-ului
	public int eval() {
		int i, j;
		int score = 0;
		for(i = 0; i <= 7; i++) {
			for(j = 0; j <= 7; j++) {
				if (!(grid[i][j] instanceof Empty) && (grid[i][j].color == side)) {
					score += grid[i][j].score;
				} else if (!(grid[i][j] instanceof Empty) && (grid[i][j].color != side)){
					score -= grid[i][j].score;
				}
			}
		}
		return score;
	}


	// intorce o lista de mutari complete (care contin atat sursa cat si destinatia)
	public ArrayList<String> updateAllPossibleMoves() {
		int i, j;
		allMoves = new ArrayList<String>();
		for(i = 0; i <= 7; i++) {
			for(j = 0; j <= 7; j++) {
				if (!(grid[i][j] instanceof Empty) && (grid[i][j].color == this.side)) {
					grid[i][j].updatePossibleMoves(this.side);
					for(String s : grid[i][j].possibleMoves) {
						allMoves.add(s);
					}
				}
			}
		}
		return allMoves;
	}

	// Algoritm minimax
	public Pair <Integer, String> minimax(Game game, int depth) {
		if(depth == -1) {
			return new Pair<Integer, String>(game.eval(), "");
		}

		game.updateAllPossibleMoves();

		if(game.allMoves.isEmpty()) {
			return new Pair<Integer, String>(-10000, "");
		}

		int bestScore = -10000;
		String bestMove = "";

		for(String move : game.allMoves) {
			Game copy = game.cloneGame();
			copy.applyMove(move);

			int score = -minimax(copy, depth - 1).first;

			if(score > bestScore) {
				bestScore = score;
				bestMove = move; 
			}
		}

		return new Pair <Integer, String> (bestScore, bestMove);
	}

	// metoda care actualizeaza grid-ul in urma unei mutari
	public void applyMove(String move) {

		int i = getRow(move.substring(0, 2));
		int j = getColumn(move.substring(0, 2));
		String nextPosition = move.substring(2);
		int nextRow = getRow(nextPosition);
		int nextColumn = getColumn(nextPosition);

		if (this.grid[i][j] instanceof Pawn && this.grid[i][j].color == this.side) {
			if(this.side == false) {
				pawnBlackMove(move);
					
			} else {
				pawnWhiteMove(move);
			}
		}
		else {
			grid[nextRow][nextColumn] = grid[i][j];	
			grid[nextRow][nextColumn].position = this.getPosition(nextRow, nextColumn);

			grid[i][j] = new Empty(getPosition(i, j), false, this);
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
		
		applyMove(move);

		// afisare mutare
		bout.write(String.format("move " + move + "\n").getBytes());
		bout.flush();
	}

	// metoda folosita in cazul in care engine-ul joaca pe alb si are mutare legala pentru un pion
	private void pawnWhiteMove(String move) {

		int i = getRow(move.substring(0, 2));
		int j = getColumn(move.substring(0, 2));

		String nextPosition = move.substring(2);
		int nextRow = getRow(nextPosition);
		int nextColumn = getColumn(nextPosition);

		// daca este o mutare de tip en passant, se elimina pionul din spatele
		// destinatiei
		if (this.grid[i][j].enPassant != null) {
			int row = this.getRow(nextPosition) - 1;
			int column = this.getColumn(nextPosition);
			this.grid[row][column] = new Empty(this.getPosition(row, column), false, this);
			// se reseteaza campul pentru pionul mutat
			this.grid[i][j].enPassant = null;
		}

		// daca pionul ajunge in baza oponentului, se transforma in regina
		if (this.grid[i][j] instanceof Pawn && nextRow == 7) {
			this.grid[nextRow][nextColumn] = new Queen(
					nextPosition, true, this);
		} else {
			// actualizare grid
			this.grid[nextRow][nextColumn] = this.grid[i][j];
			// actualizare camp pentru pozitie
			this.grid[nextRow][nextColumn].position = nextPosition;
		}

		// setare camp gol pe pozitia anterioara
		this.grid[i][j] = new Empty(getPosition(i, j), false, this);

	}

		// metoda folosita in cazul in care engine-ul joaca pe alb si are mutare legala pentru un pion
	private void pawnBlackMove(String move) {
		
		int i = getRow(move.substring(0, 2));
		int j = getColumn(move.substring(0, 2));

		String nextPosition = move.substring(2);
		int nextRow = getRow(nextPosition);
		int nextColumn = getColumn(nextPosition);

		// daca este o mutare de tip en passant, se elimina pionul din spatele
		// destinatiei
		if (this.grid[i][j].enPassant != null) {
			int row = this.getRow(nextPosition) + 1;
			int column = this.getColumn(nextPosition);
			this.grid[row][column] = new Empty(this.getPosition(row, column), false, this);
			// se reseteaza campul pentru pionul mutat
			this.grid[i][j].enPassant = null;
		}

		// daca pionul ajunge in baza oponentului, se transforma in regina
		if (this.getRow(nextPosition) == 0) {
			this.grid[nextRow][nextColumn] = new Queen(nextPosition, false, this);
		} else {

			// actualizare grid
			this.grid[nextRow][nextColumn] = this.grid[i][j];
			// actualizare camp pentru pozitie
			this.grid[nextRow][nextColumn].position = nextPosition;
		}
		// setare camp gol pe pozitia anterioara
		this.grid[i][j] = new Empty(getPosition(i, j), false, this);
	}

	// verifica daca regele engine-ului se afla in sah
	public boolean isCheck(String kingPosition) {

		int i, j, k;

		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (!(this.grid[i][j] instanceof Empty) && (this.grid[i][j].color != this.side)) {
					this.grid[i][j].updatePossibleMoves(!(this.side));

					for (k = 0; k < this.grid[i][j].possibleMoves.size(); k++) {
						if (this.grid[i][j].possibleMoves.get(k).indexOf(kingPosition) >= 0)
							return true;
					}
				}
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
	public Game cloneGame() {
		Game copy = new Game(!(this.side));
		copy.grid = cloneGrid(this.grid);
		return copy;
	}

	// metoda folosita pentru debug - afiseaza in fisierul grid.txt grid-ul
	// daca fisierul e deschis cu sublime, se va actualiza dupa fiecare mutare
	// a engine-ului
	public void printGrid() throws FileNotFoundException {
		StringBuilder gridb = new StringBuilder();
		PrintWriter wr = new PrintWriter(new File("grid.txt"));
		wr.append("Engine evaluates to " + this.eval() + "\n");
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