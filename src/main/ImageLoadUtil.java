package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoadUtil {

    private ImageLoadUtil() {}

    public static Image loadImage(String path) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(ImageLoadUtil.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return image;

    }

}