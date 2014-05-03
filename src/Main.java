import javafx.scene.effect.Effect;

import java.io.File;

/**
 * Created by Lingfu on 5/2/2014.
 */
public class Main {
    public static void main(String[] args) {
        final String USAGE_STATMENT = "USAGE: java ImageEditor in-file out-file " +
                "(grayscale|invert|emboss|motionblur motion-blur-length)";

        try {
            final String INPUT_FILE = args[0];
            final String OUTPUT_FILE = args[1];
            final String EFFECT = args[2];

            File input = new File(INPUT_FILE);
            File output = new File(OUTPUT_FILE);

            int blurRange = -1;
            final String MONTIONBLUR = "motionblur";
            if (EFFECT.equalsIgnoreCase(MONTIONBLUR)) {
                blurRange = Integer.parseInt(args[3]);
                if (blurRange < 0) {
                    System.out.println(USAGE_STATMENT);
                    return;
                }
            }

            ImageEditor imageEditor = new ImageEditor(input);

            final String INVERT = "invert";
            if (EFFECT.equalsIgnoreCase(INVERT)) {
                imageEditor.invert();
            }

            imageEditor.output(output);

        } catch (Exception e) {
            System.out.println(USAGE_STATMENT);
        }
    }
}
