import java.util.List;

/**
 * Created by Lingfu on 5/2/2014.
 */
public class Pixel {
    private int red = -1;
    private int green = -1;
    private int blue = -1;

    public Pixel() {

    }

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

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public void invert(int maxValue) {
        setRed(maxValue - getRed());
        setGreen(maxValue - getGreen());
        setBlue(maxValue - getBlue());
    }

    public void grayscale() {
        int sum = getRed() + getGreen() + getBlue();
        int average = sum/3;
        setRed(average);
        setGreen(average);
        setBlue(average);
    }

    final int DEFAULT_EMBOSS_VALUE = 128;
    public void emboss() {
        setRed(DEFAULT_EMBOSS_VALUE);
        setGreen(DEFAULT_EMBOSS_VALUE);
        setBlue(DEFAULT_EMBOSS_VALUE);
    }

    public Pixel emboss(int maxValue, int minValue, Pixel upperLeft) {
        int redDifference = getRed() - upperLeft.getRed();
        int greenDifference = getGreen() - upperLeft.getGreen();
        int blueDifference = getBlue() - upperLeft.getBlue();

        int maxDifference = findMaxDifference(redDifference, greenDifference, blueDifference);

        int colorValue = DEFAULT_EMBOSS_VALUE + maxDifference;

        colorValue = setBound(colorValue, maxValue, minValue);

        return new Pixel(colorValue, colorValue, colorValue);

    }

    private int setBound(int v, int maxValue, int minValue) {
        if (v < minValue) {
            return minValue;
        }
        if (v > maxValue) {
            return maxValue;
        }
        return v;
    }

    private int findMaxDifference(int redDiff, int greenDiff, int blueDiff) {
        int redAbs = Math.abs(redDiff);
        int greenAbs = Math.abs(greenDiff);
        int blueAbs = Math.abs(blueDiff);

        if (redAbs == greenAbs && greenAbs == blueAbs) {
            return redDiff;
        }

        if (redAbs >= greenAbs && redAbs >= blueAbs) {
            return redDiff;
        }

        if (greenAbs >= blueAbs && greenAbs >= redAbs) {
            if (redAbs == greenAbs) {
                return redDiff;
            }
            return greenDiff;
        }

        if (blueAbs >= greenAbs && blueAbs >= redAbs) {
            if (blueAbs == redAbs) {
                return redDiff;
            }
            if (blueAbs == greenAbs) {
                return greenDiff;
            }
            return blueDiff;
        }
        return 0;
    }

    public Pixel blur (List<Pixel> pixels){
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for(Pixel pixel: pixels) {
            redSum += pixel.getRed();
            greenSum += pixel.getGreen();
            blueSum += pixel.getBlue();
        }

        int redAverage = redSum/pixels.size();
        int greenAverage = greenSum/pixels.size();
        int blueAverage = blueSum/pixels.size();

        return new Pixel(redAverage, greenAverage, blueAverage);
    }

    @Override
    public String toString() {
        String result = getRed() + "\n";
        result += getGreen() + "\n";
        result += getBlue() + "\n";

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Pixel)){
            return false;
        }

        Pixel other = (Pixel) obj;
        if (getRed() == other.getRed()
                && getGreen() == other.getGreen()
                && getBlue() == other.getBlue()) {
            return true;
        }

        return false;

    }
}
