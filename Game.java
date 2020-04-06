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

	public int eval(Piece[][] grid, Boolean side) {
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

	// metoda folosita pentru a interpreta si inregistra pe grid o mutare a oponentului
	public void opponentMove(String command) {
		// calculare indicii pentru pozitia anterioara
		// si curenta in functie de stringul command
		int prevRow = getRow(command.substring(0, 2));
		int prevColumn = getColumn(command.substring(0, 2));
		int currRow = getRow(command.substring(2));
		int currColumn = getColumn(command.substring(2));

		// daca comanda are dimensiune 5, oponentul a ajuns cu pionul in baza
		// engine-ului si a schimbat pionul in alta piesa
		// se inregistreaza pe gridboard aceasta modificare
		if (command.length() == 5) {
			this.grid[currRow][currColumn] = null;
			switch (command.charAt(4)) {
			case 'q':
				this.grid[currRow][currColumn] = new Queen(getPosition(currRow, currColumn), !(this.side), this);
				break;
			case 'r':
				this.grid[currRow][currColumn] = new Rook(getPosition(currRow, currColumn), !(this.side), this);
				break;
			case 'b':
				this.grid[currRow][currColumn] = new Bishop(getPosition(currRow, currColumn), !(this.side), this);
				break;
			case 'n':
				this.grid[currRow][currColumn] = new Knight(getPosition(currRow, currColumn), !(this.side), this);
				break;
			}
		} else {
			// se pune piesa de pe pozitia anterioara pe pozitia actuala
			this.grid[currRow][currColumn] = this.grid[prevRow][prevColumn];
			this.grid[currRow][currColumn].position = command.substring(2);
		}
		// se actualizeaza pozitia veche cu empty
		this.grid[prevRow][prevColumn] = new Empty(getPosition(prevRow, prevColumn), false, this);

		engineEnPassant(prevRow, prevColumn, currRow, currColumn);
		opponentEnPassant(prevRow, prevColumn, currRow, currColumn);
		opponentCastling(prevRow, prevColumn, currRow, currColumn);
	}

	// verifica daca engine-ul poate efectua o mutare de tip en passant si seteaza
	// campul enPassant al pionului care poate efectua acea mutare cu pozitia pe
	// care se poate muta
	private void engineEnPassant(int prevRow, int prevColumn, int currRow, int currColumn) {

		if (this.side == false && prevRow == 1 && currRow == 3) {
			if (currColumn > 0 && this.grid[currRow][currColumn - 1] instanceof Pawn
					&& this.grid[currRow][currColumn - 1].color == false) {
				this.grid[currRow][currColumn - 1].enPassant = this.getPosition(currRow - 1, currColumn);
			}
			if (currColumn < 7 && this.grid[currRow][currColumn + 1] instanceof Pawn
					&& this.grid[currRow][currColumn + 1].color == false) {
				this.grid[currRow][currColumn + 1].enPassant = this.getPosition(currRow - 1, currColumn);
			}
		} else if (this.side == true && prevRow == 6 && currRow == 4) {
			if (currColumn > 0 && this.grid[currRow][currColumn - 1] instanceof Pawn
					&& this.grid[currRow][currColumn - 1].color == true) {
				this.grid[currRow][currColumn - 1].enPassant = this.getPosition(currRow + 1, currColumn);
			}
			if (currColumn < 7 && this.grid[currRow][currColumn + 1] instanceof Pawn
					&& this.grid[currRow][currColumn + 1].color == true) {
				this.grid[currRow][currColumn + 1].enPassant = this.getPosition(currRow + 1, currColumn);
			}
		}
	}

	// verifica daca oponentul a efectuat o mutare de tip en passant si actualizeaza
	// grid-ul
	private void opponentEnPassant(int prevRow, int prevColumn, int currRow, int currColumn) {

		if (this.grid[currRow][currColumn] instanceof Pawn) {
			// conditii (negru): linia curenta 5, mutare pion alb pe diagonala stanga sau
			// dreapta, pion negru aflat cu o pozitie in spate fata de pozitia curenta
			if (this.side == false) {
				if (prevRow == 4 && currRow == 5) {
					if (prevColumn == currColumn + 1) {
						if ((this.grid[currRow - 1][currColumn] instanceof Pawn)
								&& (this.grid[currRow - 1][currColumn].color == false)) {
							this.grid[currRow - 1][currColumn] = new Empty(getPosition(currRow - 1, currColumn), false,
									this);
						}
					} else if (prevColumn == currColumn - 1) {
						if ((this.grid[currRow - 1][currColumn] instanceof Pawn)
								&& (this.grid[currRow - 1][currColumn].color == false)) {
							this.grid[currRow - 1][currColumn] = new Empty(getPosition(currRow - 1, currColumn), false,
									this);
						}
					}
				}
			}
			// conditii (alb): linia curenta 2, mutare pion negru pe diagonala stanga sau
			// dreapta, pion alb aflat cu o pozitie in spate fata de pozitia curenta
			else {
				if (prevRow == 3 && currRow == 2) {
					if (prevColumn == currColumn + 1) {
						if ((this.grid[currRow + 1][currColumn] instanceof Pawn)
								&& (this.grid[currRow + 1][currColumn].color == true)) {
							this.grid[currRow + 1][currColumn] = new Empty(getPosition(currRow + 1, currColumn), false,
									this);
						}
					} else if (prevColumn == currColumn - 1) {
						if ((this.grid[currRow + 1][currColumn] instanceof Pawn)
								&& (this.grid[currRow + 1][currColumn].color == true)) {
							this.grid[currRow + 1][currColumn] = new Empty(getPosition(currRow + 1, currColumn), false,
									this);
						}
					}
				}
			}
		}
	}

	// verifica daca oponentul a efectuat o mutare de tip rocada si actualizeaza
	// grid-ul
	private void opponentCastling(int prevRow, int prevColumn, int currRow, int currColumn) {
		if (this.grid[currRow][currColumn] instanceof King) {
			// rocada de 2
			if (currRow == prevRow && currColumn == prevColumn + 2) {
				this.grid[currRow][currColumn - 1] = this.grid[currRow][7];
				this.grid[currRow][currColumn - 1].position = getPosition(currRow, currColumn - 1);

				this.grid[currRow][7] = new Empty(getPosition(currRow, 7), false, this);
			}
			// rocada de 3
			else if (currRow == prevRow && currColumn == prevColumn - 2) {
				this.grid[currRow][currColumn + 1] = this.grid[currRow][0];
				this.grid[currRow][currColumn + 1].position = getPosition(currRow, currColumn + 1);

				this.grid[currRow][0] = new Empty(getPosition(currRow, 0), false, this);
			}
		}

	}

	// metoda care intoarce pozitia
	public String getKingPosition(Piece[][] grid, Boolean color) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(grid[i][j] instanceof King && grid[i][j].color == color) {
					return this.getPosition(i, j);
				}
			}
		}
		return null;
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


	// intorce o lista de mutari complete (care contin atat sursa cat si destinatia)
	public ArrayList<String> updateAllPossibleMoves(Piece[][] grid, Boolean color) {
		int i, j;
		allMoves = new ArrayList<String>();
		for(i = 0; i <= 7; i++) {
			for(j = 0; j <= 7; j++) {
				if (!(grid[i][j] instanceof Empty) && (grid[i][j].color == color)) {
					grid[i][j].updatePossibleMoves(color);
					for(String s : grid[i][j].possibleMoves) {
						allMoves.add(s);
					}
				}
			}
		}
		return allMoves;
	}


	// minimax simplu pentru calculul celei mai bune mutari
	public Pair <Integer, String> minimax(Piece[][] grid, Boolean color, int depth) {
		if(depth == 0) {
			return new Pair<Integer, String>(this.eval(grid, color), "");
		}

		this.updateAllPossibleMoves(grid, color);
		System.out.println(this.allMoves + " " + color + " " + depth + " " + eval(grid, color));
		if(this.allMoves.size() == 0) {
			// daca nu avem mutari legale, intoarcem un string gol si scor 0
			return new Pair<Integer, String>(-10000, "");
		}
		int max = -10000;
		String bestMove = "";
		for(String move : this.allMoves) {
			// grid auxiliar folosit pentru a retine grid-ul
			Piece[][] gridAux = this.cloneGrid(grid);

			gridAux = this.applyMove(gridAux, move);

			// if(this.eval(gridAux, color) != 0 && depth == 3)
			// System.out.println(move + " " + this.eval(gridAux, color) + " depth = " + depth);

			Pair <Integer, String> p = minimax(gridAux, (!color) , depth - 1);

			if(-p.first > max) {
				max = -p.first;
				bestMove = move; 
			}
		} 

		return new Pair<Integer, String>(max, bestMove);
	}


	// metoda care actualizeaza grid-ul in urma unei mutari
	Piece[][] applyMove(Piece[][] grid, String move) {
		Piece[][] newGrid = this.cloneGrid(grid);

		int i = getRow(move.substring(0, 2));
		int j = getColumn(move.substring(0, 2));

		String nextPosition = move.substring(2);
		int nextRow = getRow(nextPosition);
		int nextColumn = getColumn(nextPosition);
		
		// if (grid[i][j] instanceof Pawn && grid[i][j].color == side) {
		// 		if(this.side == false) {
		// 			if(pawnBlackMove(i, j, kingPosition, nextPosition, bout)){
		// 				return;
		// 			}
		// 		} else {
		// 			if(pawnWhiteMove(i, j, kingPosition, nextPosition, bout)){
		// 				return;
		// 			}
		// 		}
		// }

		newGrid[nextRow][nextColumn] = grid[i][j];	
		newGrid[nextRow][nextColumn].position = this.getPosition(nextRow, nextColumn);

		newGrid[i][j] = new Empty(getPosition(i, j), false, this);
		return newGrid;
	}

	public void makeMove2(BufferedOutputStream bout) throws IOException {
		Pair <Integer, String> pair = minimax(this.grid, this.side, 3);
		String move = pair.second;
		if(move.equals("")) {
			bout.write(String.format("resign\n").getBytes());
			bout.flush();
			return;
		}
		
		this.grid = applyMove(this.grid, move);
		this.printGrid();
		// afisare mutare
		bout.write(String.format("move " + move + "\n").getBytes());
		bout.flush();
	}

	public void makeMove(BufferedOutputStream bout) throws IOException  {

		String kingPosition = this.getKingPosition(this.grid, this.side);
		ArrayList <String> allMoves = updateAllPossibleMoves(this.grid, this.side);
		
		for(String move : allMoves) {
			int i = getRow(move.substring(0, 2));
			int j = getColumn(move.substring(0, 2));

			String nextPosition = move.substring(2);
			int nextRow = getRow(nextPosition);
			int nextColumn = getColumn(nextPosition);

			// Mutare pion
			if (this.grid[i][j] instanceof Pawn && this.grid[i][j].color == this.side) {
				if(this.side == false) {
					if(pawnBlackMove(i, j, kingPosition, nextPosition, bout)){
						return;
					}
				} else {
					if(pawnWhiteMove(i, j, kingPosition, nextPosition, bout)){
						return;
					}
				}
			} else if (!(this.grid[i][j] instanceof Empty) && this.grid[i][j].color == this.side) {
				// salvare piesa
				Piece aux = clonePiece(this.grid[nextRow][nextColumn]);
				// actualizare grid
				this.grid[nextRow][nextColumn] = this.grid[i][j];
				// actualizare camp pentru pozitie
				this.grid[nextRow][nextColumn].position = nextPosition;

				// setare camp gol pe pozitia anterioara
				this.grid[i][j] = new Empty(getPosition(i, j), false, this);
							
				// verificare daca regele a fost mutat in sah
				if(this.grid[nextRow][nextColumn] instanceof King && this.isCheck(nextPosition)) {
					// refacere grid
					this.grid[i][j] = this.grid[nextRow][nextColumn];
					// refacere camp pentru pozitie
					this.grid[i][j].position = this.getPosition(i, j);
					// setare camp gol pe pozitia urmatoare
					this.grid[nextRow][nextColumn] = aux;
					continue;
				}

				// verificare daca regele este in sah dupa efectuarea mutarii
				if (this.isCheck(kingPosition)) {
					// actualizare grid
					this.grid[i][j] = this.grid[nextRow][nextColumn];
					// actualizare camp pentru pozitie
					this.grid[i][j].position = this.getPosition(i, j);

					// setare camp gol pe pozitia anterioara
					this.grid[nextRow][nextColumn] = aux;
					continue;
				}

				// afisare mutare
				bout.write(String.format("move " + move + "\n").getBytes());
				bout.flush();
				return;
			}
		}
		bout.write(String.format("resign\n").getBytes());
		bout.flush();
	}

	// metoda folosita in cazul in care engine-ul joaca pe alb si are mutare legala pentru un pion
	private boolean pawnWhiteMove(int i, int j, String kingPosition, String nextPosition, BufferedOutputStream bout)
			throws IOException {
		Piece aux = null;
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
		if (this.grid[i][j] instanceof Pawn && this.getRow(nextPosition) == 7) {
			this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = new Queen(
					nextPosition, true, this);
		} else {
			// salvare piesa
			aux = clonePiece(this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)]);
			// actualizare grid
			this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = this.grid[i][j];
			// actualizare camp pentru pozitie
			this.grid[this.getRow(nextPosition)][this
					.getColumn(nextPosition)].position = nextPosition;
		}

		// setare camp gol pe pozitia anterioara
		this.grid[i][j] = new Empty(getPosition(i, j), false, this);

		// verificare daca regele este in sah dupa efectuarea mutarii
		if (this.isCheck(kingPosition)) {
			// refacere grid
			String prevPosition = this.getPosition(i, j);
			this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = aux;
			this.grid[i][j] = new Pawn(prevPosition, true, this);
			this.grid[i][j].position = prevPosition;
			return false;
		}

		
		// afisare mutare
		bout.write(String.format("move " + getPosition(i, j) + nextPosition + "\n").getBytes());
		bout.flush();
		this.printGrid();
		return true;	
	}
	
	// metoda folosita in cazul in care engine-ul joaca pe alb si are mutare legala pentru un pion
	private boolean pawnBlackMove(int i, int j, String kingPosition, String nextPosition, BufferedOutputStream bout)
			throws IOException {
		Piece aux = null;
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
			this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = new Queen(nextPosition, false, this);
		} else {
			// salvare piesa
			aux = clonePiece(this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)]);
			// actualizare grid
			this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = this.grid[i][j];
			// actualizare camp pentru pozitie
			this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)].position = nextPosition;
		}
		// setare camp gol pe pozitia anterioara
		this.grid[i][j] = new Empty(getPosition(i, j), false, this);

		// verificare daca regele este in sah dupa efectuarea mutarii
		if (this.isCheck(kingPosition)) {
			// refacere grid
			String prevPosition = this.getPosition(i, j);
			this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = aux;
			this.grid[i][j] = new Pawn(prevPosition, false, this);
			this.grid[i][j].position = prevPosition;
			return false;
		}

		// afisare mutare
		bout.write(String.format("move " + getPosition(i, j) + nextPosition + "\n").getBytes());
		bout.flush();
		this.printGrid();
		return true;
	}

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

	// metoda folosita pentru debug - afiseaza in fisierul grid.txt grid-ul
	// daca fisierul e deschis cu sublime, se va actualiza dupa fiecare mutare
	// a engine-ului
	public void printGrid() throws FileNotFoundException {
		StringBuilder gridb = new StringBuilder();
		PrintWriter wr = new PrintWriter(new File("grid.txt"));
		wr.append("Engine evaluates to " + this.eval(this.grid, this.side) + "\n");
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