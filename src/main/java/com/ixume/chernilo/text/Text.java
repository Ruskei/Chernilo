package com.ixume.chernilo.text;

import com.ixume.chernilo.colormapping.ExperimentalMapColorMatcher;
import org.joml.Vector2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Text {
    public static final int CHAR_SPACING = 1;
    public static final int LINE_SPACING = 1;
    private List<String> text;
    private Font font;
    private byte color;
    private byte shadow;
    private int scale;

    public Text(List<String> text, Font font, byte color, byte shadow, int scale) {
        this.text = text;
        this.font = font;
        this.color = color;
        this.shadow = shadow;
        this.scale = scale;
    }

    public static Text of(String text, Font font, int scale) {
        return new Text(Collections.singletonList(text), font, (byte) 34, ExperimentalMapColorMatcher.matchColor(new Color(0, 0, 0), false), scale);
    }

    public static Text empty(Font font, int scale, boolean shadow) {
        return new Text(new ArrayList<>(), font, (byte) 34, shadow ? ExperimentalMapColorMatcher.matchColor(new Color(0, 0, 0), false) : 0, scale);
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public byte getColor() {
        return color;
    }

    public boolean hasColor() { return color != 0; }

    public void setColor(byte color) {
        this.color = color;
    }

    public byte getShadow() {
        return shadow;
    }

    public void setShadow(byte shadow) {
        this.shadow = shadow;
    }

    public boolean hasShadow() { return shadow != 0; }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public Vector2i dimensionsFromLines() {
        return new Vector2i(text.stream().mapToInt(s -> font.getWidth(s)).reduce(0, (subtotal, current) -> Math.max(current, subtotal)), (font.getHeight() + LINE_SPACING) * text.size());
    }
}
