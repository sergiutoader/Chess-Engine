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

		enPassant(prevRow, prevColumn, currRow, currColumn);
		castling(prevRow, prevColumn, currRow, currColumn);
	}

	// verifica daca oponentul a efectuat o mutare de tip en passant si actualizeaza
	// grid-ul
	private void enPassant(int prevRow, int prevColumn, int currRow, int currColumn) {

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
	private void castling(int prevRow, int prevColumn, int currRow, int currColumn) {
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

	public void makeMove(BufferedOutputStream bout) throws IOException {

		int i, j;
		String kingPosition = null;
		String nextPosition = null;

		PrintWriter wr = new PrintWriter(new File("moves.txt"));

		// Search for king position
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (this.grid[i][j] instanceof King && this.grid[i][j].color == this.side) {
					kingPosition = this.grid[i][j].position;
					break;
				}
			}
		}

		if (this.isCheck(kingPosition)) {
			bout.write("resign\n".getBytes());
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

							// daca pionul ajunge in baza oponentului, se transforma in regina
							if (this.grid[i][j] instanceof Pawn && this.getRow(nextPosition) == 0) {
								this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = new Queen(
										nextPosition, false, this);
							} else {
								// actualizare grid
								this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = this.grid[i][j];
								// actualizare camp pentru pozitie
								this.grid[this.getRow(nextPosition)][this
										.getColumn(nextPosition)].position = nextPosition;
							}
							// setare camp gol pe pozitia anterioara
							this.grid[i][j] = new Empty(getPosition(i, j), false, this);

							return;
						}
					} else if ((this.grid[i][j] instanceof Empty == false) && grid[i][j].color == this.side) {

						// calculare mutari posibile
						this.grid[i][j].updatePossibleMoves(this.side);

						wr.append(this.grid[i][j].possibleMoves.toString() + "\n");

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

							// daca pionul ajunge in baza oponentului, se transforma in regina
							if (this.grid[i][j] instanceof Pawn && this.getRow(nextPosition) == 7) {
								this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = new Queen(
										nextPosition, true, this);
							} else {
								// actualizare grid
								this.grid[this.getRow(nextPosition)][this.getColumn(nextPosition)] = this.grid[i][j];
								// actualizare camp pentru pozitie
								this.grid[this.getRow(nextPosition)][this
										.getColumn(nextPosition)].position = nextPosition;
							}

							// setare camp gol pe pozitia anterioara
							this.grid[i][j] = new Empty(getPosition(i, j), false, this);

							return;
						}
					} else if ((this.grid[i][j] instanceof Empty == false) && grid[i][j].color == this.side) {

						// calculare mutari posibile
						this.grid[i][j].updatePossibleMoves(this.side);

						wr.append(this.grid[i][j].possibleMoves.toString() + "\n");

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
		wr.close();

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
	public boolean freeSpace(int row, int column, boolean color) {
		if (this.grid[row][column].color == color && (this.grid[row][column] instanceof Empty == false)) {
			return false;
		}
		return true;
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

}
