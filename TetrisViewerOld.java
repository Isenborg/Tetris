import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TetrisViewerOld
{
    private Board board;

    public TetrisViewerOld(final Board board) {
	this.board = board;
    }

    public void startGame(){
	final Action doOneStep = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		board.randomizeBoard();
		show();
	    }
	};

	final Timer clockTimer = new Timer(1000, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
	show();
	clockTimer.stop();

    }

    public void show(){
	JFrame frame = new JFrame("Tetris");
	BoardToTextConverter converter = new BoardToTextConverter();
	JTextArea textarea = new JTextArea(this.board.getWidth(), this.board.getHeight());
	textarea.setText(converter.convertToText(this.board));
	textarea.setFont(new Font("Monospaced", Font.PLAIN, 20));
	frame.setLayout(new BorderLayout());
	frame.add(textarea, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);
    }
}
