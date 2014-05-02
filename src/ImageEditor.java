import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Lingfu on 5/2/2014.
 */
public class ImageEditor {
    private int width = 0;
    private int height = 0;
    private int maxColorValue = 255;
    private Pixle image[][];
    final String MAGIC_NUMBER = "P3";

    public ImageEditor (File inputFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(inputFile);
        String firstString = scanner.next();
    }
}
