package com.ixume.chernilo.text;

import javax.annotation.Nullable;
import java.util.HashMap;

public class FontsManager {
    private static HashMap<String, Font> fonts = new HashMap<>();

    public static void registerFont(String name, Font font) {
        fonts.put(name, font);
    }

    @Nullable
    public static Font getFont(String name) {
        return fonts.get(name);
    }
}
