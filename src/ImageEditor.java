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
        image.invert();
    }

    public void grayscale() {
        image.grayscale();
    }

    public void emboss() {
        image.emboss();
    }

    public void blur(int range) {
        image.blur(range);
    }

    public void output(File output) throws FileNotFoundException{
        image.output(output);
    }

}
