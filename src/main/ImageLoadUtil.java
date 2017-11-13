package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoadUtil {

    private ImageLoadUtil() {}

    public static Image loadImage(String path) {

        BufferedImage image = null;

        try {
            File imageFile = new File(path);
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

}