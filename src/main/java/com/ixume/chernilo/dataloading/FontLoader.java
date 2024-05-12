package com.ixume.chernilo.dataloading;

import com.ixume.chernilo.Chernilo;
import com.ixume.chernilo.text.Font;
import com.ixume.chernilo.text.FontsManager;
import com.ixume.chernilo.MapColorImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FontLoader {
    private static Color MARK_COLOR = new Color(1, 2, 3, 255);
    public static void init() {
        File dataFolder = Chernilo.getInstance().getDataFolder();
        dataFolder.mkdirs();
        loadFonts();
    }

    private static void loadFonts() {
        File fontsFolder = new File(Chernilo.getInstance().getDataFolder().getAbsolutePath() + "/fonts");

        for (File image : fontsFolder.listFiles((dir, name) -> name.endsWith(".png"))) {
            try {
                BufferedImage bufferedImage = ImageIO.read(image);
                FontsManager.registerFont(image.getName().substring(0, image.getName().length() - 4), parseFontAtlas(bufferedImage));
            } catch (final IOException ignored) {}
        }
    }

    private static Font parseFontAtlas(BufferedImage image) {
        HashMap<Character, MapColorImage> fontSprites = new HashMap<>();
        //find font height
        //should start at the first pixel anyway, so look for the next line after that
        int height = getFontHeight(image);

        int pointer = 0;
        int max = image.getHeight() * image.getWidth() - height * image.getWidth() + image.getWidth();
        System.out.println("max: " + max);
        int currentWidth = 0;
        while (pointer <= max) {
            int x = pointer % image.getWidth();
            int y = (int) Math.floor(pointer / image.getWidth());
            if (x == 0) currentWidth = 0;
            int rgb = image.getRGB(x, y);
            Color pixelColor = new Color((rgb >> 16) & 0xFF, (rgb >>  8) & 0xFF, (rgb) & 0xFF, (rgb >> 24) & 0xFF);
            if (currentWidth > 0) currentWidth++;

            if (pixelColor.equals(MARK_COLOR)) {
                if (currentWidth == 0) {
                    //starting  char
                    currentWidth++;
                } else {
                    //ending char
                    fontSprites.put((char) (fontSprites.size() + 32), new MapColorImage(image.getSubimage(x - currentWidth + 2, y, currentWidth - 2, height)));
                    currentWidth = 1;
                }
            }

            pointer++;
        }

        return new Font(fontSprites, height);
    }

    private static int getFontHeight(BufferedImage image) {
        for (int y = 1; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                Color pixelColor = new Color((rgb >> 16) & 0xFF, (rgb >>  8) & 0xFF, (rgb) & 0xFF, (rgb >> 24) & 0xFF);
                if (pixelColor.equals(MARK_COLOR)) {
                    return y;
                }
            }
        }

        return 0;
    }
}
