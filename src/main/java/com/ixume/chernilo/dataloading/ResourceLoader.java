package com.ixume.chernilo.dataloading;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ixume.chernilo.Chernilo;
import com.ixume.chernilo.MapColorImage;
import com.ixume.chernilo.colormapping.ExperimentalMapColorMatcher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResourceLoader {
    public static void init() {
        File dataFolder = Chernilo.getInstance().getDataFolder();
        dataFolder.mkdirs();

        loadColorMappings();
        loadResources();
    }

    private static void loadResources() {
        System.out.println("loadResources");
        File dataFolder = Chernilo.getInstance().getDataFolder();
        for (File image : dataFolder.listFiles((dir, name) -> name.endsWith(".png"))) {
            try {
                BufferedImage bufferedImage = ImageIO.read(image);
                System.out.println("added: " + image.getName().substring(0, image.getName().length() - 4));
                SpriteManager.addSprite(image.getName().substring(0, image.getName().length() - 4), new MapColorImage(bufferedImage));
            } catch (final IOException ignored) {}
        }

        if (SpriteManager.getSprite("scroll_background") != null) {
            System.out.println("scroll background is not null");
        } else {
            System.out.println("scroll background is null");
        }
    }

    private static void loadColorMappings() {
        JsonObject obj;
        try (
                InputStreamReader reader = new InputStreamReader(Chernilo.getInstance().getResource("color_mappings.json"))) {
            obj = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (
                IOException ex) {
            throw new RuntimeException(ex);
        }

        final JsonObject version = obj.get("1.20").getAsJsonObject();
        for (
                String key : version.keySet()) {
            final JsonObject colorObj = version.get(key).getAsJsonObject();
            final JsonArray colorArr = colorObj.get("colors").getAsJsonArray();
            int id = Integer.parseInt(key);
            for (int variant = 0; variant < colorArr.size(); variant++) {
                int rgb = colorArr.get(variant).getAsInt();
                final Color color = new Color(rgb, false);
                ExperimentalMapColorMatcher.colorIDMap.put(color, id * 4 + variant);
            }
        }

        ExperimentalMapColorMatcher.init();
    }
}
