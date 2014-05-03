import java.io.File;

/**
 * Created by Lingfu on 5/2/14.
 */
public interface Editor {

//  Take the destination and save the result image
    public void output(File output);

//  For every pixel of the image, inverse the color value
//    eg. the minimum value is 0 and maximum value is 255,
//        then color value 0 becomes 255 and 127 becomes 128.
    public void invert();

//  For every pixel of the image, average the value of red, green, and blue
//    eg. Red: 25 Green: 230 Blue: 122, (25 + 230 + 122)/3 = 125
//        The grayscale values: Red: 125 Green: 125 Blue: 125.
    public void grayscale();

//  For every pixel of the image, set the color to same calculated value.
//      first, calculate the difference between current pixel color with the upper left pixel
//          eg. redDiff = p.redValue - image[r-1, c-1].redValue
//      second, find the absolute largest difference, then create v by adding 128 to the max difference,
//          if v is out of bound of the min and max color value,
//          then set the value to min value if v < min,
//          or set the value to max value if v > max.
//      last, assign v to every pixel red, green, blue value.
//    Special case: for the most upper left pixel, set v to 128.
    public void emboss();

//  For every pixel of the image, average the pixel color value from x,y to x,y+n-1
//      Special case: if the pixel is out of bound,
//          then just average the pixels within the width of the image.
    public void blur(int range);

}
