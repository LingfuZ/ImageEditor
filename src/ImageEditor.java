import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Lingfu on 5/2/14.
 */
public class ImageEditor implements Editor {
    private Image image = new Image();

    public ImageEditor(File input) throws FileNotFoundException {
        image = new Image(input);
    }

    public void invert() {
        image = image.invert();
    }

    public void grayscale() {

    }

    public void emboss() {

    }

    public void blur(int range) {

    }

    public void output(File output) throws FileNotFoundException{
        image.output(output);
    }

}
