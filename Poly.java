public class Poly
{
    private SquareType[][] poly;

    public Poly(final SquareType[][] poly) {
        this.poly = poly;
    }

    public int getHeight(){
        return poly.length;
    }

    public SquareType[][] getPoly() {
        return poly;
    }

    public int getWidth(){
        return poly[0].length;
    }
}