public class TetrominoMaker
{
    public static int getNumberOfTypes(){
	return SquareType.values().length;
    }
    public static Poly getPoly(int n){
	SquareType[][] poly;
	switch (n){
	    case 0:
		//Create an 4x4 poly with all elements as empty squares
		poly = createEmptySquarePoly(4, 4);
		poly[1][0] = SquareType.I; // [ - - - - ]
		poly[1][1] = SquareType.I; // [ x x x x ]
		poly[1][2] = SquareType.I; // [ - - - - ]
		poly[1][3] = SquareType.I; // [ - - - - ]
		break;
	    case 1:
		poly = createEmptySquarePoly(3, 3);
		poly[0][0] = SquareType.L; // [ x - - ]
		poly[1][0] = SquareType.L; // [ x x x ]
		poly[1][1] = SquareType.L; // [ - - - ]
		poly[1][2] = SquareType.L;
		break;
	    case 2:
		poly = createEmptySquarePoly(3, 3);
		poly[0][2] = SquareType.J; // [ - - x ]
		poly[1][0] = SquareType.J; // [ x x x ]
		poly[1][1] = SquareType.J; // [ - - - ]
		poly[1][2] = SquareType.J;
		break;
	    case 3:
		poly = createEmptySquarePoly(2, 2);
		poly[0][0] = SquareType.S; // [ x x ]
		poly[0][1] = SquareType.S; // [ x x ]
		poly[1][0] = SquareType.S;
		poly[1][1] = SquareType.S;
		break;
	    case 4:
		poly = createEmptySquarePoly(3, 3);
		poly[0][1] = SquareType.Z; // [ - x x ]
		poly[0][2] = SquareType.Z; // [ x x - ]
		poly[1][0] = SquareType.Z; // [ - - - ]
		poly[1][1] = SquareType.Z;
		break;
	    case 5:
		poly = createEmptySquarePoly(3, 3);
		poly[0][1] = SquareType.T; // [ - x - ]
		poly[1][0] = SquareType.T; // [ x x x ]
		poly[1][1] = SquareType.T; // [ - - - ]
		poly[1][2] = SquareType.T;
		break;
	    case 6:
		poly = createEmptySquarePoly(3, 3);
		poly[0][0] = SquareType.O; // [ x x - ]
		poly[0][1] = SquareType.O; // [ - x x ]
		poly[1][1] = SquareType.O; // [ - - - ]
		poly[1][2] = SquareType.O;
		break;
	    default:
		throw new IllegalArgumentException("Invalid index " + n);
	}
	Poly polyObject = new Poly(poly);
	return polyObject;
    }
    private static SquareType[][] createEmptySquarePoly(int width, int height){
	SquareType[][] poly = new SquareType[width][height];
	for (int x = 0; x < width; x++){
	    for (int y = 0; y < height; y++)
		poly[y][x] = SquareType.EMPTY;
	}
	return poly;
    }

}
