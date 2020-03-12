import java.io.*;

public class Game {
	public Piece[][] grid;

	public boolean side; // true = white, false = black;

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

	// metoda folosita pentru debug - afiseaza in fisierul grid.txt grid-ul
	// daca fisierul e deschis cu sublime, se va actualiza dupa fiecare mutare
	// a engine-ului
	public void printGrid() throws FileNotFoundException {
		StringBuilder gridb = new StringBuilder();
		PrintWriter wr = new PrintWriter(new File("grid.txt"));
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

	public void opponentMove(String command) {
		// calculare indicii pentru pozitia anterioara
		// si curenta in functie de stringul command
		int prevPositionRow = getRow(command.substring(0, 2));
		int prevPositionColumn = getColumn(command.substring(0, 2));
		int currPositionRow = getRow(command.substring(2));
		int currPositionColumn = getColumn(command.substring(2));

		// swap intre piesa curenta si empty
		Piece aux = this.grid[prevPositionRow][prevPositionColumn];

		this.grid[currPositionRow][currPositionColumn] = aux;
		this.grid[currPositionRow][currPositionColumn].position = command.substring(2);

		this.grid[prevPositionRow][prevPositionColumn] = new Empty(getPosition(prevPositionRow, prevPositionColumn),
				false, this);

		enPassant(prevPositionRow, prevPositionColumn, currPositionRow, currPositionColumn);

	}

	private void enPassant(int prevPositionRow, int prevPositionColumn, int currPositionRow, int currPositionColumn) {

		if (this.grid[currPositionRow][currPositionColumn] instanceof Pawn) {
			// conditii (negru): linia curenta 5, mutare pion alb pe diagonala stanga sau
			// dreapta, pion negru aflat cu o pozitie in spate fata de pozitia curenta
			if (this.side == false) {
				if (prevPositionRow == 4 && currPositionRow == 5) {
					if (prevPositionColumn == currPositionColumn + 1) {
						if ((this.grid[currPositionRow - 1][currPositionColumn] instanceof Pawn)
								&& (this.grid[currPositionRow - 1][currPositionColumn].color == false)) {
							this.grid[currPositionRow - 1][currPositionColumn] = new Empty(
									getPosition(currPositionRow - 1, currPositionColumn), false, this);
						}
					} else if (prevPositionColumn == currPositionColumn - 1) {
						if ((this.grid[currPositionRow - 1][currPositionColumn] instanceof Pawn)
								&& (this.grid[currPositionRow - 1][currPositionColumn].color == false)) {
							this.grid[currPositionRow - 1][currPositionColumn] = new Empty(
									getPosition(currPositionRow - 1, currPositionColumn), false, this);
						}
					}
				}
			}
			// conditii (alb): linia curenta 2, mutare pion negru pe diagonala stanga sau
			// dreapta, pion alb aflat cu o pozitie in spate fata de pozitia curenta
			else {
				if (prevPositionRow == 3 && currPositionRow == 2) {
					if (prevPositionColumn == currPositionColumn + 1) {
						if ((this.grid[currPositionRow + 1][currPositionColumn] instanceof Pawn)
								&& (this.grid[currPositionRow + 1][currPositionColumn].color == true)) {
							this.grid[currPositionRow + 1][currPositionColumn] = new Empty(
									getPosition(currPositionRow + 1, currPositionColumn), false, this);
						}
					} else if (prevPositionColumn == currPositionColumn - 1) {
						if ((this.grid[currPositionRow + 1][currPositionColumn] instanceof Pawn)
								&& (this.grid[currPositionRow + 1][currPositionColumn].color == true)) {
							this.grid[currPositionRow + 1][currPositionColumn] = new Empty(
									getPosition(currPositionRow + 1, currPositionColumn), false, this);
						}
					}
				}
			}
		}
	}

	public boolean isCheck(String kingPosition) {
		
		int i, j, k;

		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (!(this.grid[i][j] instanceof Empty) && (this.grid[i][j].color != this.side)) {
					this.grid[i][j].updatePossibleMoves(!(this.side));
					for (k = 0; k < this.grid[i][j].possibleMoves.size(); k++) {
						if (this.grid[i][j] instanceof Pawn
								&& (this.grid[i][j].possibleMoves.get(k).indexOf(kingPosition) >= 0))
							return true;
					}
				}
			}
		}
		return false;
	}

	public void makeMove(BufferedOutputStream bout) throws IOException {

		int i, j, k;
		String kingPosition = null;		
		String nextPosition = null;


		// Search for king position
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (this.grid[i][j] instanceof King && this.grid[i][j].color == this.side) {
					kingPosition = this.grid[i][j].position;
					break;
				}
			}
		}
		
		if (this.isCheck(kingPosition) ) {
			bout.write("resign\n".getBytes() );
			bout.flush();
		}


		if (this.side == false) {

			for (i = 0; i < 8; i++) {
				for (j = 0; j < 8; j++) {

					if (this.grid[i][j] instanceof Pawn && grid[i][j].color == this.side) {

						// calculare mutari posibile
						this.grid[i][j].updatePossibleMoves(this.side);

						if (this.grid[i][j].possibleMoves.size() > 0) {
							nextPosition = this.grid[i][j].possibleMoves.get(0);
						}

						if (nextPosition != null) {

							// afisare mutare
							bout.write(String.format("move " + getPosition(i, j) + nextPosition + "\n").getBytes());
							bout.flush();

							// actualizare grid

							this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = this.grid[i][j];
							// actualizare camp pentru pozitie
							this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)].position = nextPosition;

							// setare camp gol pe pozitia anterioara
							this.grid[i][j] = new Empty(getPosition(i, j), false, this);

							return;
						}
					}
				}
			}
		} else {
			for (i = 7; i >= 0; i--) {
				for (j = 7; j >= 0; j--) {

					if (this.grid[i][j] instanceof Pawn && grid[i][j].color == this.side) {

						// calculare mutari posibile
						this.grid[i][j].updatePossibleMoves(this.side);

						if (this.grid[i][j].possibleMoves.size() > 0) {
							nextPosition = this.grid[i][j].possibleMoves.get(0);
						}

						if (nextPosition != null) {

							// afisare mutare
							bout.write(String.format("move " + getPosition(i, j) + nextPosition + "\n").getBytes());
							bout.flush();

							// actualizare grid

							this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = this.grid[i][j];
							// actualizare camp pentru pozitie
							this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)].position = nextPosition;

							// setare camp gol pe pozitia anterioara
							this.grid[i][j] = new Empty(getPosition(i, j), false, this);

							return;
						}
					}
				}
			}
		}

		// se face resign daca nu se poate executa nicio mutare
		bout.write("resign\n".getBytes());
		bout.flush();
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
	public boolean freeSpace(int row, int column) {
		if(this.grid[row][column].color == this.side)return false;
		return true;
	}

}
