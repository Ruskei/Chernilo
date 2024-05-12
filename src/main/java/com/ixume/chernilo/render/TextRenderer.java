package com.ixume.chernilo.render;

import com.ixume.chernilo.MapColorImage;
import com.ixume.chernilo.colormapping.ExperimentalMapColorMatcher;
import com.ixume.chernilo.text.Font;
import com.ixume.chernilo.text.Text;
import com.ixume.karta.screen.MapScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class TextRenderer {
    private static void drawString(MapScreen mapScreen, String text, Font font, int x, int y, int scale) {
        int xOffset = 0;
        int yOffset = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                yOffset += font.getHeight() * scale + Text.LINE_SPACING;
            }
            
            MapColorImage sprite = font.getSprite(c);
            RenderUtils.drawImage(mapScreen, x + xOffset, y - yOffset, sprite, scale);
            xOffset += (sprite.getWidth() + Text.CHAR_SPACING) * scale;
        }
    }

    private static void drawString(MapScreen mapScreen, String text, Font font, int x, int y, int scale, byte color) {
        int xOffset = 0;
        int yOffset = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                yOffset += font.getHeight() * scale + Text.LINE_SPACING;
            }

            MapColorImage sprite = font.getSprite(c);
            RenderUtils.drawFlatImage(mapScreen, x + xOffset, y - yOffset, sprite, scale, color);
            xOffset += (sprite.getWidth() + Text.CHAR_SPACING) * scale;
        }
    }

    public static void drawText(MapScreen mapScreen, Text text, int x, int y) {
        for (int i = 0; i < text.getText().size(); i++) {
            int offset = text.getScale() * i * (text.getFont().getHeight() + Text.LINE_SPACING);
            if (text.hasShadow()) drawString(mapScreen, text.getText().get(i), text.getFont(), x + text.getScale(), y - text.getScale() - offset, text.getScale(), text.getShadow());
            if (text.hasColor()) drawString(mapScreen, text.getText().get(i), text.getFont(), x, y - offset, text.getScale(), text.getColor());
            else drawString(mapScreen, text.getText().get(i), text.getFont(), x, y - offset, text.getScale());
        }
    }
}
