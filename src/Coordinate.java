/**
 * Created by Lingfu on 5/3/14.
 */
public class Coordinate {
    private int x = -1;
    private int y = -1;

    public Coordinate(int x, int y) {
        setX(x);
        setY(y);
    }

    private void setX(int x) {
        this.x = x;
    }

    private void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isOutOfBound() {
        if (x == 0 || y == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Coordinate))
            return false;

        Coordinate other = (Coordinate) obj;
        if (this.getX() == other.getX() && this.getY() == other.getY()) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Integer.toString(x).hashCode() + Integer.toString(y).hashCode();
    }
}
