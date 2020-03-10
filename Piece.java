
public abstract class Piece {

	public Game game;
	public String position;
	public boolean color;
	public String [] possibleMoves;
	
	public abstract void updatePossibleMoves();

	protected int getRow(){
		return position.charAt(1) - '0' - 1;
	}

	protected int getColumn(){
		return position.charAt(0) - 'a';
	}

}