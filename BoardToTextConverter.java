public class BoardToTextConverter
{
    public String convertToText(Board board){
	StringBuilder sb = new StringBuilder();
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		switch (board.getVisibleSquareAt(x, y)) {
		    case EMPTY -> sb.append('-');
		    case OUTSIDE -> sb.append('X');
		    case I -> sb.append('#');
		    case J -> sb.append('O');
		    case L -> sb.append('@');
		    case O -> sb.append('%');
		    case S -> sb.append('€');
		    case T -> sb.append('=');
		    case Z -> sb.append('&');
		}
	    }
	    //After every row must go to new line
	    sb.append("\n");
	}
	return sb.toString();
    }
}
