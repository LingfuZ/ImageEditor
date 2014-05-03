import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Lingfu on 5/2/14.
 */
public class ImageEditor implements Editor {
    private Image image = new Image();

    public ImageEditor(File input) throws FileNotFoundException {
        image = image.parseFromFile(input);
    }

    public void invert() {

    }

    public void grayscale() {

    }

    public void emboss() {

    }

    public void blur(int range) {

    }

    public void output(File output) {

    }

}
