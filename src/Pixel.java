/**
 * Created by Lingfu on 5/2/2014.
 */
public class Pixel {
    private int red = -1;
    private int green = -1;
    private int blue = -1;

    public Pixel(int red, int green, int blue) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
    }

    private void setRed(int value) {
        red = value;
    }

    private void setGreen(int value) {
        green = value;
    }

    private void setBlue(int value) {
        blue = value;
    }

    private int getRed() {
        return this.red;
    }

    private int getGreen() {
        return this.green;
    }

    private int getBlue() {
        return this.blue;
    }

    public void invert(int maxValue) {
        setRed(maxValue - getRed());
        setGreen(maxValue - getGreen());
        setBlue(maxValue - getBlue());
    }

    public String toString() {
        String result = getRed() + "\n";
        result += getGreen() + "\n";
        result += getBlue() + "\n";

        return result;
    }
}
