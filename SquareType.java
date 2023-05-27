import java.util.Random;

public enum SquareType
{
    EMPTY, OUTSIDE, I, O, T, S, Z, J, L;

    public static void main(String[] args) {
	Random rnd = new Random();
	for (int i = 0; i < 25; i++){
	    System.out.println(rnd.nextInt(100));
	}

	SquareType[] types = SquareType.values();
	System.out.println(types.length);
	for(int i = 0; i < 25; i++){
	    System.out.println(types[rnd.nextInt(types.length)]);
	}
    }
}


