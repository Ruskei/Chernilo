package com.ixume.chernilo.background;

import com.ixume.chernilo.render.ActiveSectionUtils;
import com.ixume.chernilo.render.RenderUtils;
import com.ixume.karta.render.background.BackgroundRenderer;
import com.ixume.karta.screen.MapScreen;

public abstract class PixelBackgroundRenderer implements BackgroundRenderer {
    private final MapScreen mapScreen;

    public PixelBackgroundRenderer(MapScreen mapScreen) {
        this.mapScreen = mapScreen;
    }

    @Override
    public void drawBackground(int section) {
        int[] sectionPos = ActiveSectionUtils.posFromSection(mapScreen.getWidth(), section);
        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 128; x++) {
                RenderUtils.drawPixelRaw(mapScreen, section, x, y, colorAt(sectionPos[0] + x, sectionPos[1] + y));
            }
        }
    }

    public abstract byte colorAt(int x, int y);
}
