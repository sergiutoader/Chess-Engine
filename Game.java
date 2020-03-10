import java.io.*;

public class Game {
	public Piece[][] grid;

	public boolean side; // true = white, false = black;

	public Piece getPiece(int row, int column){
		return this.grid[row][column];
	}

	public Game(boolean side){
		this.side = side;
		grid = new Piece [8][8];
		
		// Spatii goale
		for(int i = 2; i < 6; i++){
			for(int j = 0; j <= 7; j++){
				grid[i][j] = new Empty(getPosition(i,j), false, this);
			}
		}
		// Pioni
		for(int j = 0; j <= 7; j++){
			grid[1][j] = new Pawn(getPosition(1,j), true, this);
			grid[6][j] = new Pawn(getPosition(6,j), false, this);
		}

		// Cai
		grid[0][1] = new Knight(getPosition(0,1), true, this);
		grid[0][6] = new Knight(getPosition(0,6), true, this);
		grid[7][1] = new Knight(getPosition(7,1), false, this);
		grid[7][6] = new Knight(getPosition(7,6), false, this);

		// Nebuni
		grid[0][2] = new Bishop(getPosition(0,2), true, this);
		grid[0][5] = new Bishop(getPosition(0,5), true, this);
		grid[7][2] = new Bishop(getPosition(7,2), false, this);
		grid[7][5] = new Bishop(getPosition(7,5), false, this);

		// Ture
		grid[0][0] = new Rook(getPosition(0,0), true, this);
		grid[0][7] = new Rook(getPosition(0,7), true, this);
		grid[7][0] = new Rook(getPosition(7,0), false, this);
		grid[7][7] = new Rook(getPosition(7,7), false, this);

		// Regine
		grid[0][3] = new Queen(getPosition(0,3), true, this);
		grid[7][4] = new Queen(getPosition(7,4), false, this);

		// Regii
		grid[0][4] = new King(getPosition(0,4), true, this);
		grid[7][3] = new King(getPosition(7,3), false, this);


	}

	public void opponentMove(String command){
		// calculare indicii pentru pozitia anterioara
		// si curenta in functie de stringul command
		int prevPositionRow = getRow(command.substring(0,2));
		int prevPositionColumn = getColumn(command.substring(0,2));
		int currPositionRow = getRow(command.substring(2));
		int currPositionColumn = getColumn(command.substring(2));

		// swap intre piesa curenta si empty
		Piece aux = this.grid[prevPositionRow][prevPositionColumn];

		this.grid[currPositionRow][currPositionColumn] = aux;
		this.grid[currPositionRow][currPositionColumn].position = command.substring(2);

		this.grid[prevPositionRow][prevPositionColumn] = new Empty(
			getPosition(prevPositionRow, prevPositionColumn), false, this);

	}

	public void makeMove(BufferedOutputStream bout) throws IOException{
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){

				if(this.grid[i][j] instanceof Pawn && grid[i][j].color == this.side){

					// calculare mutari posibile 
					this.grid[i][j].updatePossibleMoves();

					String nextPosition = this.grid[i][j].possibleMoves[0];

					if(nextPosition != null) {

						//afisare mutare
						bout.write(String.format("move " + getPosition(i,j) +
							nextPosition + "\n").getBytes());
						bout.flush();
						
						// actualizare grid
						Piece aux = this.grid[this.getRow(nextPosition)][
							this.getColumn(nextPosition)];

						this.grid[this.getRow(nextPosition)][this.getColumn(
							nextPosition)] = this.grid[i][j];
						// actualizare camp pentru pozitie
						this.grid[this.getRow(nextPosition)][this.getColumn(
							nextPosition)].position = nextPosition;

						// setare camp gol pe pozitia anterioara
						this.grid[i][j] = new Empty(getPosition(i, j), false, this);
						
						return;
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
		result.append((char)('a' + column));
		result.append(row + 1);
		return result.toString();
	}

	// metode care intorc indicele si coloana in functie de string

	public int getRow(String position){
		return position.charAt(1) - '0' - 1;
	}

	public int getColumn(String position){
		return position.charAt(0) - 'a';
	}

}
