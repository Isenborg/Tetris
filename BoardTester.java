public class BoardTester
{
    public static void main(String[] args) {
	Poly poly = TetrominoMaker.getPoly(5);

	Board board = new Board(12, 15, poly);
	BoardToTextConverter converter = new BoardToTextConverter();
	String textBoard = converter.convertToText(board);
	System.out.println(textBoard);
	TetrisViewer viewer = new TetrisViewer(board);
	viewer.startGame();
    }
}
