package com.ixume.chernilo.background;

import com.ixume.chernilo.MapColorImage;
import com.ixume.chernilo.render.ActiveSectionUtils;
import com.ixume.chernilo.render.RenderUtils;
import com.ixume.karta.render.background.BackgroundRenderer;
import com.ixume.karta.screen.MapScreen;

public class ImageBackground implements BackgroundRenderer {
    private final MapColorImage backgroundImage;
    private final MapScreen mapScreen;
    private final int upscale;

    public ImageBackground(MapColorImage backgroundImage, MapScreen mapScreen, int upscale) {
        this.backgroundImage = backgroundImage;
        this.mapScreen = mapScreen;
        this.upscale = upscale;
    }

    @Override
    public void drawBackground(int section) {
        int[] sectionPos = ActiveSectionUtils.posFromSection(mapScreen.getWidth(), section);

        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 128; x++) {
                RenderUtils.drawPixelRaw(mapScreen, section, x, y, backgroundImage.getPixel((int) Math.floor((double) (sectionPos[0] + x) / upscale), (int) Math.floor((double) ((mapScreen.getHeight() - 1) * 128 - sectionPos[1] + y) / upscale)));
            }
        }
    }
}
