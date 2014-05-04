import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Lingfu on 5/2/14.
 */
public class Image {
    private Map<Coordinate, Pixel> pixels = new HashMap<Coordinate, Pixel>();
    private int height = -1;
    private int width = -1;
    private int maxValue = -1;
    private static final int MIN_VALUE = 0;
    private static final String MAGIC_NUMBER = "P3";

    public Image() {

    }

    public Image(File input) throws FileNotFoundException {
        parseFromFile(input);
    }

    private void parseFromFile(File input) throws FileNotFoundException {
        Scanner scanner = new Scanner(input);

        String firstToken = "";
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
            return;
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

        for (Map.Entry<Coordinate, Pixel>pixelEntry: pixels.entrySet()) {
            Coordinate currentKey = pixelEntry.getKey();
            Pixel currentValue = pixelEntry.getValue();

            if (currentKey.isOutOfBound()) {
                currentValue.emboss();
                pixels.put(currentKey, currentValue);
            } else {
                int left = currentKey.getX()-1;
                int upper = currentKey.getY()-1;

                Coordinate upperLeftCoordinate = new Coordinate(left, upper);
                Pixel upperLeftPixel = pixels.get(upperLeftCoordinate);

//                if (upperLeftPixel == null) {
//                    System.out.println("Error: null pointer for upper left pixel.");
//                }

                currentValue.emboss(maxValue, MIN_VALUE, upperLeftPixel);

                pixels.put(currentKey, currentValue);
            }
        }
    }

    public void blur(int range) {

        if(range > width) {
            range = width;
        }

        for (Map.Entry<Coordinate, Pixel>pixelEntry: pixels.entrySet()) {
            Coordinate currentKey = pixelEntry.getKey();
            Pixel currentValue = pixelEntry.getValue();

            List<Pixel> selectedPixels = new ArrayList<Pixel>();
            int currentX = currentKey.getX();
            int currentY = currentKey.getY();
            for (int i=1; i<range-1; i++) {
                int x = currentX + i;
                if (x <= width) {
                    Coordinate nextCoor = new Coordinate(x, currentY);
                    Pixel nextPixel = pixels.get(nextCoor);

                    selectedPixels.add(nextPixel);
                }
            }

            currentValue.blur(selectedPixels);
            pixels.put(currentKey, currentValue);
        }
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
