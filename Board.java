import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board
{
    private int width;
    private int height;
    private SquareType[][] squares;
    private List<BoardListener> boardListeners;
    private final static Random RND = new Random();
    private Poly falling = null;
    private Point fallingPos = null;
    private final static int MARGIN = 2;
    private final static int DOUBLE_MARGIN = 2 * MARGIN;
    private boolean gameOver = false;

    public Poly getFalling() {
	return falling;
    }

    public Point getFallingPos() {
	return fallingPos;
    }


    public Board(final int width, final int height, Poly falling) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height+DOUBLE_MARGIN][width+DOUBLE_MARGIN];
	generateEmptyBoard();

	this.boardListeners = new ArrayList<>();
    }

    public boolean isRunning(){
	return !gameOver;
    }

    public SquareType getVisibleSquareAt(int x, int y){
	x += MARGIN;
	y += MARGIN;
	if (this.falling == null) {
	    return getSquareType(x, y);
	}
	if (pointIsOnFallingSquare(x, y)) {
	    // Coordinates of top left corner
	    int x0 = this.fallingPos.x;
	    int y0 = this.fallingPos.y;
	    // Calculate the square x and y corresponds to on the falling square
	    int fx = x - x0;
	    int fy = y - y0;
	    if (this.falling.getPoly()[fx][fy] == SquareType.EMPTY) {
		return getSquareType(x, y);
	    } else
		return this.falling.getPoly()[fx][fy];
	}
	return getSquareType(x, y);

    }
    private boolean pointIsOnFallingSquare(int x, int y){
	// Create the points that the falling square is within
	int x0 = this.fallingPos.x;
	int x1 = x0 + this.falling.getWidth() - 1;
	int y0 = this.fallingPos.y;
	int y1 = y0 + this.falling.getHeight() - 1;
	if (x >= x0 && x <= x1 && y >= y0 && y <= y1) return true;
	return false;
    }

    private boolean fullRow(final int row){
	for (int column = 0; column < width; column++){
	    if (getVisibleSquareAt(column, row) == SquareType.EMPTY) {
		return false;
	    }
	}
	return true;
    }

    private void handleFilledRows(){
	for (int row = 0; row < height; row++){
	    if (fullRow(row)){
		emptyFullRow(row);
	    }
	}
    }

    private void emptyFullRow(int row){
	while (row > 0) {
	    for (int c = 0; c < width; c++) {
		squares[row+MARGIN][c+MARGIN] = squares[row-1+MARGIN][c+MARGIN];
	    }
	    row--;
	}
    }

    public void tick(){
	if (gameOver) return;

	// one step forward in the game
	if (falling == null) {
	    addFalling();
	} else {
	    // if there is a block currently falling
	    fallingPos.y++;
	    if (hasCollision()) {
		fallingPos.y--;
		placeFallingBlock();
		falling = null;
		handleFilledRows();
	    }
	}
	notifyListeners();

    }

    private void placeFallingBlock(){
	SquareType[][] fallingPoly = falling.getPoly();
	for (int fx = 0; fx < falling.getHeight(); fx++) {
	    for (int fy = 0; fy < falling.getWidth(); fy++) {
		if (fallingPoly[fx][fy] != SquareType.EMPTY) {
		    int currentX = fallingPos.x + fx;
		    int currentY = fallingPos.y + fy;
		    squares[currentY][currentX] = fallingPoly[fx][fy];
		}
	    }
	}
    }

    public void rotate(Direction direction){
	if (falling == null) return;
	Poly rotatedPoly = new Poly(new SquareType[falling.getHeight()][falling.getWidth()]);
	if (direction == Direction.RIGHT) {
	    rotatedPoly = rotateRight();
	}
	else if (direction == Direction.LEFT){
	    rotatedPoly = rotateLeft();
	}

	Poly currentPoly = falling;
	falling = rotatedPoly;
	if (hasCollision()){
	    falling = currentPoly;
	}

    }

    private Poly rotateLeft() {
	if (falling == null) return null;

	int size = falling.getHeight();
	SquareType[][] currentPoly = falling.getPoly();
	SquareType[][] newPoly = new SquareType[size][size];
	for (int i = 0; i < 3; i++) {
	    newPoly = new SquareType[size][size];
	    for (int r = 0; r < size; r++) {
		for (int c = 0; c < size; c++) {
		    newPoly[c][size - 1 - r] = currentPoly[r][c];
		}
	    }
	    currentPoly = newPoly;
	}
	return new Poly(newPoly);
    }

    private Poly rotateRight() {
	if (falling == null) return null;

	int size = falling.getHeight();
	SquareType[][] currentPoly = falling.getPoly();
	SquareType[][] newPoly = new SquareType[size][size];

	for (int r = 0; r < size; r++) {
	    for (int c = 0; c < size; c++){
		newPoly[c][size-1-r] = currentPoly[r][c];
	    }
	}
	return new Poly(newPoly);
    }

    private void addFalling(){
	falling = TetrominoMaker.getPoly(RND.nextInt(TetrominoMaker.getNumberOfTypes()-2));
	fallingPos = new Point(width / 2, MARGIN);
	if (hasCollision()) gameOver = true;
	notifyListeners();
    }

    public SquareType getSquareType(int x, int y){
	return squares[y][x];
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public void randomizeBoard(){
	SquareType[] types = SquareType.values();
	for (int x = 0; x < width; x++){
	    for (int y = 0; y < height; y++)
		squares[y][x] = types[RND.nextInt(types.length)];
	}
	notifyListeners();
    }


    private void generateEmptyBoard(){
	// fill board with OUTSIDE squares
	for (int x = 0; x < width+DOUBLE_MARGIN; x++){
	    for (int y = 0; y < height+DOUBLE_MARGIN; y++)
		squares[y][x] = SquareType.OUTSIDE;
	}
	for (int x = 0; x < width; x++){
	    for (int y = 0; y < height; y++)
		squares[y+MARGIN][x+MARGIN] = SquareType.EMPTY;
	}
    }

    private void notifyListeners(){
	for (BoardListener bl : boardListeners) {
	    bl.boardChanged();
	}
    }

    public void resetBoard(){
	generateEmptyBoard();
	gameOver = false;
	falling = null;
    }


    public void move(Direction direction){
	if (falling != null) {
	    if (direction == Direction.LEFT) {
		fallingPos.x--;
		// Check if the block collides after moving it;
		if (hasCollision())
		    fallingPos.x++; // If it collides move it back
	    }
	    if (direction == Direction.RIGHT) {
		fallingPos.x++;
		if (hasCollision())
		    fallingPos.x--;
	    }

	    notifyListeners();
	}
    }
    private boolean hasCollision(){
	SquareType[][] fallingPoly = falling.getPoly();
	for (int fx = 0; fx < falling.getHeight(); fx++){
	    for (int fy = 0; fy < falling.getWidth(); fy++){
		if (fallingPoly[fx][fy] != SquareType.EMPTY){
		    int currentX = fallingPos.x + fx;
		    int currentY = fallingPos.y + fy;

		    if (getSquareType(currentX, currentY) != SquareType.EMPTY) return true;
		}
	    }
	}
	return false;
    }
    public void addBoardsListener(BoardListener bl){
	boardListeners.add(bl);
    }

}
