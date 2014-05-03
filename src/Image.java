import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Lingfu on 5/2/14.
 */
public class Image {
    private Map pixels = new HashMap();
    private int height = -1;
    private int width = -1;
    private int maxValue = -1;
    private final String MAGIC_NUMBER = "P3";

    public Image parseFromFile(File input) throws FileNotFoundException {
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

        return this;
    }

    private void createImage(Scanner scanner) {
        if (!scanner.hasNext()) {
            System.out.println("Error: Missing pixels.");
        } else {
            String current = "";
            for (int i=0; i<height; i++) {
                for (int j=0; j<width; j++) {
                    String coordinate = Integer.toString(j) + Integer.toString(i);

                    int red = Integer.parseInt(scanner.next());
                    current = skipComment(scanner);
                    int green = Integer.parseInt(current);
                    current = skipComment(scanner);
                    int blue = Integer.parseInt(current);

                    Pixel pixel = new Pixel(red, green, blue, maxValue);

                    pixels.put(coordinate, pixel);
                }
            }
        }

    }

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
}
