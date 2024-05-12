package com.ixume.chernilo.text;

import com.ixume.chernilo.MapColorImage;

import javax.annotation.Nullable;
import java.util.Map;

public class Font {
    private final int height;
    private final Map<Character, MapColorImage> sprites;

    public Font(Map<Character, MapColorImage> sprites, int height) {
        this.sprites = sprites;
        this.height = height;
    }

    @Nullable
    public MapColorImage getSprite(Character c) {
        return sprites.get(c);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth(String text) {
        int width = 0;
        for (char c : text.toCharArray()) {
            width += sprites.get(c).getWidth() + Text.CHAR_SPACING;
        }

        return width;
    }
}
