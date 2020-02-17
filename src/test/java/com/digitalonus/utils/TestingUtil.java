package com.digitalonus.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The type Testing util.
 */
public final class TestingUtil {

    /**
     * The constant IMAGE_COVI_19_PATH.
     */
    public static final String IMAGE_COVI_19_PATH = "image/covi-19.png";
    /**
     * The constant IMAGE_COVI_19_BUF.
     */
    public static final BufferedImage IMAGE_COVI_19_BUF = loadImage(IMAGE_COVI_19_PATH);

    /**
     * Load image buffered image.
     *
     * @param path the path
     * @return the buffered image
     */
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ClassLoader.getSystemResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
