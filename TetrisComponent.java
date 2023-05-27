import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class TetrisComponent extends JComponent implements BoardListener
{
    private Board board;
    private int squareSize;
    private final static EnumMap<SquareType, Color> SQUARECOLORS = createColorMap();

    public TetrisComponent(final Board board) {
	this.board = board;
	this.squareSize = calculateSquareSize(board);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	for (int x = 0; x < board.getWidth(); x++){
	    for (int y = 0; y < board.getHeight(); y++){
		SquareType currentSquare = board.getVisibleSquareAt(x, y);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x * squareSize, y * squareSize, squareSize, squareSize);
		g2d.setColor(SQUARECOLORS.get(currentSquare));
		g2d.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
	    }
	}
    }

    private int calculateSquareSize(Board board){
	final Dimension screenSize = getPreferredSize();
	int maxW = screenSize.width / board.getWidth();
	int maxH = screenSize.height / board.getHeight();
	return Math.min(maxW, maxH);
    }

    private static EnumMap<SquareType, Color> createColorMap(){
	EnumMap<SquareType, Color> squareColors = new EnumMap<>(SquareType.class);
	squareColors.put(SquareType.EMPTY, Color.GRAY);
	squareColors.put(SquareType.S, Color.RED);
	squareColors.put(SquareType.T, Color.BLUE);
	squareColors.put(SquareType.J, Color.YELLOW);
	squareColors.put(SquareType.I, Color.GREEN);
	squareColors.put(SquareType.O, Color.MAGENTA);
	squareColors.put(SquareType.L, Color.CYAN);
	squareColors.put(SquareType.Z, Color.PINK);
	return squareColors;
    }

    public void boardChanged(){
	repaint();
    }

    public Dimension getPreferredSize(){
	final int height = 500;
	final int width = 400;
	return new Dimension(width, height);
    }
}
