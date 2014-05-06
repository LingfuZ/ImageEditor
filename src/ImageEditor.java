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

//    public void testEmboss() { image.testEmboss(); }
    public void testBlur() {image.testBlur(); }

    public static void main(String[] args) {
        final String USAGE_STATMENT = "USAGE: java ImageEditor in-file out-file " +
                "(grayscale|invert|emboss|motionblur motion-blur-range)";

        try {
            final String INPUT_FILE = args[0];
            final String OUTPUT_FILE = args[1];
            final String EFFECT = args[2];

            File input = new File(INPUT_FILE);
            File output = new File(OUTPUT_FILE);

            ImageEditor imageEditor = new ImageEditor(input);

            int blurRange ;
            final String MONTIONBLUR = "motionblur";
            if (EFFECT.equalsIgnoreCase(MONTIONBLUR)) {
                blurRange = Integer.parseInt(args[3]);
                if (blurRange < 0) {
                    System.out.println(USAGE_STATMENT);
                    return;
                }
                imageEditor.blur(blurRange);
            }

            final String INVERT = "invert";
            if (EFFECT.equalsIgnoreCase(INVERT)) {
                imageEditor.invert();
            }

            final String GRAYSCALE = "grayscale";
            if(EFFECT.equalsIgnoreCase((GRAYSCALE))) {
                imageEditor.grayscale();
            }

            final String EMBOSS = "emboss";
            if(EFFECT.equalsIgnoreCase(EMBOSS)) {
                imageEditor.emboss();
            }

            imageEditor.output(output);

//            imageEditor.testEmboss();
//            imageEditor.testBlur();

        } catch (Exception e) {
            System.out.println(USAGE_STATMENT);
        }
    }
}
