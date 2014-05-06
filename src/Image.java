import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Lingfu on 5/2/14.
 */
public class Image {

    private static final int MIN_VALUE = 0;
    private static final String MAGIC_NUMBER = "P3";

    private Map<Coordinate, Pixel> pixels = new HashMap<Coordinate, Pixel>();
    private int height = -1;
    private int width = -1;
    private int maxValue = -1;

    public Image() {

    }

    public Image(Map<Coordinate, Pixel> pixels, int width, int height, int maxValue) {
        setPixels(pixels);
        setWidth(width);
        setHeight(height);
        setMaxValue(maxValue);
    }

    public Image(File input) throws FileNotFoundException {
        parseFromFile(input);
    }

    private void parseFromFile(File input) throws FileNotFoundException {
        Scanner scanner = new Scanner(input);

        String firstToken;
        if (!scanner.hasNext()) {
            System.out.println("Error: Empty file.");
        }

        firstToken = scanner.next();
        if (!firstToken.equals(MAGIC_NUMBER)) {
            System.out.println("Error: Wrong format. File not start with P3");
        }

        parseHeader(scanner);
        createImage(scanner);

    }

    //initialize pixels
    //eg. Pixels => {{"10,100", {128,128,128}}, {"11,100", {255,255,0}}....}
    private void createImage(Scanner scanner) {
        if (!scanner.hasNext()) {
            System.out.println("Error: Missing pixels.");
        } else {
            String current = "";
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Coordinate coordinate = new Coordinate(j, i);

                    int red = Integer.parseInt(scanner.next());
                    current = skipComment(scanner);
                    int green = Integer.parseInt(current);
                    current = skipComment(scanner);
                    int blue = Integer.parseInt(current);

                    Pixel pixel = new Pixel(red, green, blue);

                    pixels.put(coordinate, pixel);
                }
            }
        }

    }

    //Set height, width, and max color value
    private void parseHeader(Scanner scanner) {
        String current = skipComment(scanner);
        if (current != null){
            width = Integer.parseInt(current);
        }

        current = skipComment(scanner);
        if (current != null) {
            height = Integer.parseInt(current);
        }

        current = skipComment(scanner);
        if (current != null) {
            maxValue = Integer.parseInt(current);
        }

    }

    private String skipComment(Scanner scanner) {
        String current = "";
        if (scanner.hasNext()) {
            current = scanner.next();
        } else {
            System.out.println("Error: File ends unexpectedly.");
            return null;
        }

        final String POND = "#";
        if (current.equals(POND)) {
            scanner.nextLine();
            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                System.out.println("Error: File ends unexpectedly.");
                return null;
            }
        } else {
            return current;
        }
    }

    public void invert() {
        for (Map.Entry<Coordinate, Pixel> pixelEntry: pixels.entrySet()) {
            Coordinate currentKey = pixelEntry.getKey();
            Pixel currentValue = pixelEntry.getValue();

            currentValue.invert(maxValue);

            pixels.put(currentKey, currentValue);
        }
    }

    public void grayscale() {
        for (Map.Entry<Coordinate, Pixel>pixelEntry: pixels.entrySet()) {
            Coordinate currentKey = pixelEntry.getKey();
            Pixel currentValue = pixelEntry.getValue();

            currentValue.grayscale();

            pixels.put(currentKey, currentValue);
        }
    }

    public void emboss() {
//        iterate through each pair
//        get key and value
//        if the pixel at the most upper left corner
//                call pixel.emboss()
//        else construct the upper left pixel's coordinate
//        get value upperLeftPixel according to the coordinate key
//                call pixel.emboss(maxValue, upperLeftPixel)
//        update value for current pair

        Map<Coordinate, Pixel> newPixels = new HashMap<Coordinate, Pixel>();

        for (Map.Entry<Coordinate, Pixel>pixelEntry: pixels.entrySet()) {
            Coordinate currentKey = pixelEntry.getKey();
            Pixel currentValue = pixelEntry.getValue();

            if (currentKey.isOutOfBound()) {
                Pixel newPixel = new Pixel();
                newPixel.emboss();
                newPixels.put(currentKey, newPixel);
            } else {
                int left = currentKey.getX()-1;
                int upper = currentKey.getY()-1;

                Coordinate upperLeftCoordinate = new Coordinate(left, upper);
                Pixel upperLeftPixel = pixels.get(upperLeftCoordinate);

//                if (upperLeftPixel == null) {
//                    System.out.println("Error: null pointer for upper left pixel.");
//                }

                Pixel newPixel = currentValue.emboss(maxValue, MIN_VALUE, upperLeftPixel);

                newPixels.put(currentKey, newPixel);
            }
        }

        this.setPixels(newPixels);
    }

    private Image clone(Image image) {
        Image newImage = new Image();

        newImage.setHeight(image.getHeight());
        newImage.setWidth(image.getWidth());
        newImage.setMaxValue(image.getMaxValue());

        Map<Coordinate, Pixel> newPixels = new HashMap<Coordinate, Pixel>();
        for (Map.Entry<Coordinate, Pixel> pixelEntry: image.getPixels().entrySet()) {
            Coordinate currentKey = pixelEntry.getKey();
            Pixel currentValue = pixelEntry.getValue();

            newPixels.put(currentKey, currentValue);

        }

        newImage.setPixels(newPixels);

        return newImage;
    }

    public void blur(int range) {

        if(range > width) {
            range = width;
        }

        Map<Coordinate, Pixel> newPixels = new HashMap<Coordinate, Pixel>();
        for (Map.Entry<Coordinate, Pixel>pixelEntry: pixels.entrySet()) {
            Coordinate currentKey = pixelEntry.getKey();
            Pixel currentValue = pixelEntry.getValue();

            List<Pixel> selectedPixels = new ArrayList<Pixel>();
            int currentX = currentKey.getX();
            int currentY = currentKey.getY();

            for (int i=0; i<range; i++) {
                int x = currentX + i;
                if (x < width) {
                    Coordinate nextCoor = new Coordinate(x, currentY);
                    Pixel nextPixel = pixels.get(nextCoor);

                    selectedPixels.add(nextPixel);
                }
            }

            Pixel newPixel = currentValue.blur(selectedPixels);

            for (int i=0; i<selectedPixels.size(); i++) {
                int x = currentX + i;
                Coordinate nextCoordinate = new Coordinate(x, currentY);
                newPixels.put(nextCoordinate, newPixel);
            }

//            //For pass off error
//            Pixel bug = new Pixel(0, 63, 31);
//            if (currentX == 1 && currentY == 1) {
//                System.out.println("The size of selected pixels: " + selectedPixels.size());
//                for (int j=0; j< width; j++) {
//                    Coordinate next = new Coordinate(j, currentY);
//                    Pixel original = pixels.get(next);
//
//                    System.out.println("Pixel at (x, y) : (" + next.getX() + ", " + next.getY()
//                            + ") has original value {" + original.getRed() + ", "
//                            + original.getGreen() + ", " + original.getBlue() + ")");
//                    System.out.println();
//                }
//                for (int j=0; j< width; j++) {
//                    Coordinate next = new Coordinate(j, currentY);
//                    Pixel modified = newPixels.get(next);
//
//                    System.out.println("Pixel at (x, y) : (" + next.getX() + ", " + next.getY()
//                            + ") has modified value {" + modified.getRed() + ", "
//                            + modified.getGreen() + ", " + modified.getBlue() + ")");
//                    System.out.println();
//                }
//            }
        }

        this.setPixels(newPixels);

    }

//    public void testEmboss() {
//        Map<Coordinate, Pixel> testPixels = new HashMap<Coordinate, Pixel>();
//
//        Coordinate upperLeftCoordinate = new Coordinate(0, 6);
//        Pixel upperLeftPixel = new Pixel(121, 69, 22);
//
//        Coordinate coordinate = new Coordinate(1, 7);
//        Pixel pixel = new Pixel(122, 68, 22);
//
//        testPixels.put(upperLeftCoordinate, upperLeftPixel);
//        testPixels.put(coordinate, pixel);
//
//        Image testImage = new Image(testPixels, 10, 10, 255);
//        testImage.emboss();
//
//        System.out.println(testImage);
//    }

    public void testBlur() {
        Coordinate coordinate0 = new Coordinate(0, 1);
        Coordinate coordinate1 = new Coordinate(1, 1);
        Coordinate coordinate2 = new Coordinate(2, 1);
        Coordinate coordinate3 = new Coordinate(3, 1);

        Pixel pixel0 = new Pixel(0, 0, 0);
        Pixel pixel1 = new Pixel(0, 255, 127);
        Pixel pixel2 = new Pixel(0, 0, 0);
        Pixel pixel3 = new Pixel(0, 0, 0);

        Map<Coordinate, Pixel> testPixels = new HashMap<Coordinate, Pixel>();
        testPixels.put(coordinate0, pixel0);
        testPixels.put(coordinate1, pixel1);
        testPixels.put(coordinate2, pixel2);
        testPixels.put(coordinate3, pixel3);

        Image testImage = new Image(testPixels, 4, 4, 255);
        testImage.blur(10);

        System.out.println(testImage);
    }

    @Override
    public String toString() {
        String result = "Width: " + getWidth() + "\n";
        result += "Height: " + getHeight() + "\n";
        for (Map.Entry<Coordinate, Pixel>pixelEntry: getPixels().entrySet()) {
            Coordinate currentKey = pixelEntry.getKey();
            Pixel currentValue = pixelEntry.getValue();

            result += "Pixel at x:" + currentKey.getX() + " y: " + currentKey.getY() + "\n";
            result += "Pixel value is (" + currentValue.getRed() + ", " + currentValue.getGreen()
                    + ", " + currentValue.getBlue() + ").\n";

        }

        return result;
    }

    private Map<Coordinate, Pixel> getPixels() {
        return pixels;
    }

    private int getHeight() {
        return height;
    }

    private int getWidth() {
        return width;
    }

    private int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setPixels(Map<Coordinate, Pixel> pixels) {
        this.pixels = pixels;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void output(File output) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(output);

        writer.print( MAGIC_NUMBER + "\n");
        writer.print( width + " " + height + "\n");
        writer.print( maxValue + "\n");

        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                Coordinate coordinate = new Coordinate(j, i);
                Pixel pixel = pixels.get(coordinate);

                writer.print(pixel);
            }
        }

        writer.close();
    }
}
