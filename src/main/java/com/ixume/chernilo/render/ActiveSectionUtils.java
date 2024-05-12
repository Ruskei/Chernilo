package com.ixume.chernilo.render;

import com.ixume.chernilo.MapColorImage;
import com.ixume.chernilo.text.Text;
import com.ixume.karta.screen.MapScreen;
import org.bukkit.map.MinecraftFont;
import org.joml.Vector2i;

public class ActiveSectionUtils {
    public static boolean[] emptyActiveSections(MapScreen mapScreen) {
        return new boolean[mapScreen.getWidth() * mapScreen.getHeight()];
    }

    public static boolean[] sectionsFromRectangle(MapScreen mapScreen, int x1, int y1, int x2, int y2) {
        boolean[] activeSections = emptyActiveSections(mapScreen);

        for (int i = (int) Math.floor(y1 / 128f); i <= (int) Math.floor(y2 / 128f); i++) {
            for (int j = (int) Math.floor(x1 / 128f); j <= (int) Math.floor(x2 / 128f); j++) {
                activeSections[j + i * mapScreen.getWidth()] = true;
            }
        }

        return activeSections;
    }

    public static boolean[] sectionsFromText(MapScreen mapScreen, Text text, int x, int y) {
        Vector2i dimensions = text.dimensionsFromLines().mul(text.getScale());
        return sectionsFromRectangle(mapScreen, x, y, x + dimensions.x, y + dimensions.y);
    }

    public static boolean[] sectionsFromImage(MapScreen mapScreen, int x, int y, MapColorImage image, int upscale) {
        return sectionsFromRectangle(mapScreen, x, y, (x + image.getWidth() * upscale), (y + image.getHeight() * upscale));
    }

    public static int sectionFromPos(int width, int x, int y) {
        return (int) (Math.floor(x / 128f) + Math.floor(y / 128f) * width);
    }

    public static int[] posFromSection(int width, int section) {
        return new int[] {section % width * 128, (int) (Math.floor(section / width) * 128)};
    }
}
