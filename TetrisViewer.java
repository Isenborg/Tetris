import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisViewer
{
    private Board board;
    private JFrame frame;
    private TetrisComponent comp;
    private static int DELAY = 500;

    public TetrisViewer(final Board board) {

	this.board = board;
	comp = new TetrisComponent(board);
	board.addBoardsListener(comp);
	initialize();

	JComponent pane = frame.getRootPane();

	final InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	in.put(KeyStroke.getKeyStroke("LEFT"), "moveleft");
	in.put(KeyStroke.getKeyStroke("RIGHT"), "moveright");
	in.put(KeyStroke.getKeyStroke("UP"), "rotateright");
	in.put(KeyStroke.getKeyStroke("DOWN"), "rotateleft");
	in.put(KeyStroke.getKeyStroke("SPACE"), "restartgame");

	final ActionMap act = pane.getActionMap();
	act.put("moveleft", new MoveAction(Direction.LEFT));
	act.put("moveright", new MoveAction(Direction.RIGHT));
	act.put("rotateleft", new RotateAction(Direction.LEFT));
	act.put("rotateright", new RotateAction(Direction.RIGHT));
    }

    public void startGame(){
	final Action doOneStep = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		board.tick();

	    }
	};

	Timer clockTimer = new Timer(DELAY, doOneStep);
	show();
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

    private void initialize(){
	frame = new JFrame("Tetris");
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setSize(400, 600);
	frame.setLayout(new BorderLayout());
	frame.setLocationRelativeTo(null);
	frame.add(comp, BorderLayout.CENTER);
	loadBar();
    }

    private void loadBar(){
	JMenuBar bar = new JMenuBar();

	JMenuItem exitOption = new JMenuItem("Exit");
	exitOption.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		tryExit();
	    }
	});
	JMenuItem restartOption = new JMenuItem("Restart");
	restartOption.addActionListener(new ActionListener()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		restartGame();
	    }
	});


	JMenu options = new JMenu("Options");
	options.add(restartOption);
	options.add(exitOption);

	bar.add(options);

	frame.setJMenuBar(bar);
    }

    public void show() {
	frame.setVisible(true);
    }

    private void restartGame(){
	board.resetBoard();
	startGame();
    }


    private void tryExit(){
	Object[] options = {
		"Yes",
		"No"
	};
	int optionChosen = JOptionPane.showOptionDialog(
		frame,
		"Are you sure you want to exit?",
		"Exit",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.WARNING_MESSAGE,
		null,
		options,
		options[0]
	);

	if (optionChosen == 0){
	    System.exit(0);
	}
    }

    private class MoveAction extends AbstractAction{
	private Direction direction;

	private MoveAction(final Direction direction) {
	    this.direction = direction;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    board.move(direction);
	}
    }

    private class RotateAction extends AbstractAction{
	private Direction direction;

	private RotateAction(final Direction direction) {
	    this.direction = direction;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    board.rotate(direction);
	}
    }


}
