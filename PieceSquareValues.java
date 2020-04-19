
public class PieceSquareValues {

	public static final int[][] WhitePawnSquare = {
		{0,   0,   0,   0,   0,   0,   0,   0},
		{0,   0,   0, -40, -40,   0,   0,   0},
		{1,   2,   3, -10, -10,   3,   2,   1},
		{2,   4,   6,   8,   8,   6,   4,   2},
		{3,   6,   9,  12,  12,   9,   6,   3},
		{4,   8,  12,  16,  16,  12,   8,   4},
		{5,  10,  15,  20,  20,  15,  10,   5},
		{0,   0,   0,   0,   0,   0,   0,   0}
	} ;


	public static final int[][] BlackPawnSquare = {
		{0,   0,   0,   0,   0,   0,   0,   0},
		{5,  10,  15,  20,  20,  15,  10,   5},
		{4,   8,  12,  16,  16,  12,   8,   4},
		{3,   6,   9,  12,  12,   9,   6,   3},
		{2,   4,   6,   8,   8,   6,   4,   2},
		{1,   2,   3, -10, -10,   3,   2,   1},
		{0,   0,   0, -40, -40,   0,   0,   0},
		{0,   0,   0,   0,   0,   0,   0,   0}
	} ;


	public static final int[][] EarlyWhitePawnSquare = {
		{0,   0,   0,   0,   0,   0,   0,   0},
		{20,-40, -40, -40, -40, -40, -40, -40},
		{0,  20,   0,  20,   0,  20,   0,  20},
		{0,   0,  20,   0,  20,   0,  20,   0},
		{0,   0,   0,   0,   0,   0,   0,   0},
		{0,   0,   0,   0,   0,   0,   0,   0},
		{0,   0,   0,   0,   0,   0,   0,   0},
		{0,   0,   0,   0,   0,   0,   0,   0}
	} ;


	public static final int[][] EarlyBlackPawnSquare = {
		{0,   0,   0,   0,   0,   0,   0,   0},
		{0,   0,   0,   0,   0,   0,   0,   0},
		{0,   0,   0,   0,   0,   0,   0,   0},
		{0,   0,   0,   0,   0,   0,   0,   0},
		{0,   0,  20,   0,  20,   0,  20,   0},
		{0,  20,   0,  20,   0,  20,   0,  20},
		{20,-40, -40, -40, -40, -40, -40, -40},
		{0,   0,   0,   0,   0,   0,   0,   0}
	} ;


	public static final int[][] WhiteKnightSquare = {
		{-10, -30, -10, -10, -10, -10, -30, -10},
		{-10,   0,   0,   0,   0,   0,   0, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10,   0,   5,  10,  10,   5,   0, -10},
		{-10,   0,   5,  10,  10,   5,   0, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10,   0,   0,   0,   0,   0,   0, -10},
		{-10, -10, -10, -10, -10, -10, -10, -10}
	};

	public static final int[][] BlackKnightSquare = {
		{-10, -10, -10, -10, -10, -10, -10, -10},
		{-10,   0,   0,   0,   0,   0,   0, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10,   0,   5,  10,  10,   5,   0, -10},
		{-10,   0,   5,  10,  10,   5,   0, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10,   0,   0,   0,   0,   0,   0, -10},
		{-10, -30, -10, -10, -10, -10, -30, -10}
	};

	public static final int[][] WhiteBishopSquare = {
		{-10, -10, -20, -10, -10, -20, -10, -10},
		{-10,   0,   0,   0,   0,   0,   0, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10,   0,   5,  10,  10,   5,   0, -10},
		{-10,   0,   5,  10,  10,   5,   0, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10, -10, -10, -10, -10, -10, -10, -10}	
	};
	public static final int[][] BlackBishopSquare = {
		{-10, -10, -10, -10, -10, -10, -10, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10,   0,   5,  10,  10,   5,   0, -10},
		{-10,   0,   5,  10,  10,   5,   0, -10},
		{-10,   0,   5,   5,   5,   5,   0, -10},
		{-10,   0,   0,   0,   0,   0,   0, -10},
		{-10, -10, -20, -10, -10, -20, -10, -10}
	};
	

	public static final int[][] WhiteRookSquare = {
		{  0,  0,   0,  20,   0,  20,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0}
	};
	public static final int[][] BlackRookSquare = {
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,  20,   0,  20,   0,   0}
	};
	
	public static final int[][] WhiteQueenSquare = {
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0}
	};

	public static final int[][] BlackQueenSquare = {
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0},
		{  0,  0,   0,   0,   0,   0,   0,   0}
	};
	
	
	public static final int[][] WhiteKingSquare = {
		{  0,  20,  80, -20,   0, -20,  80,  20},
		{-20, -20, -20, -20, -20, -20, -20, -20},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40}
	};

	public static final int[][] BlackKingSquare = {
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-40, -40, -40, -40, -40, -40, -40, -40},
		{-20, -20, -20, -20, -20, -20, -20, -20},
		{  0,  20,  80, -20,   0, -20,  80,  20}
	};

	public static final int[][] KingEndgameSquare = {
		{  0,  10,  20,  30,  30,  20,  10,   0},
		{ 10,  20,  30,  40,  40,  30,  20,  10},
		{ 20,  30,  40,  50,  50,  40,  30,  20},
		{ 30,  40,  50,  60,  60,  50,  40,  30},
		{ 30,  40,  50,  60,  60,  50,  40,  30},
		{ 20,  30,  40,  50,  50,  40,  30,  20},
		{ 10,  20,  30,  40,  40,  30,  20,  10},
		{  0,  10,  20,  30,  30,  20,  10,   0}
	};
	
}