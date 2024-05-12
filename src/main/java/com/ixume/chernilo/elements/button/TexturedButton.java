package com.ixume.chernilo.elements.button;

import com.ixume.chernilo.MapColorImage;
import com.ixume.chernilo.render.RenderUtils;
import com.ixume.karta.gui.events.Tickable;
import com.ixume.karta.screen.MapScreen;

public class TexturedButton extends ButtonElement implements Tickable {
    MapColorImage unselected;
    MapColorImage hover;
    MapColorImage clicked;
    private final int scale;
    private int timeSinceClick;

    public TexturedButton(MapScreen mapScreen, int zIndex, int x, int y, int width, int height, MapColorImage unselected, MapColorImage hover, MapColorImage clicked, int scale) {
        super(mapScreen, zIndex, x, y, width, height, scale);
        this.unselected = unselected;
        this.hover = hover;
        this.clicked = clicked;
        this.scale = scale;
        timeSinceClick = 0;
    }

    @Override
    public void drawElement() {
        if (timeSinceClick > 0) {
            RenderUtils.drawImage(mapScreen, x + width / 2 - clicked.getWidth() * scale / 2, y + height / 2 - clicked.getHeight() * scale / 2, clicked, scale);
        } else if (isHovering) {
            RenderUtils.drawImage(mapScreen, x + width / 2 - hover.getWidth() * scale / 2, y + height / 2 - hover.getHeight() * scale / 2, hover, scale);
        } else {
            RenderUtils.drawImage(mapScreen, x + width / 2 - unselected.getWidth() * scale / 2, y + height / 2 - unselected.getHeight() * scale / 2, unselected, scale);
        }
    }

    @Override
    public void onMouseClick(int x, int y) {
        if (inBounds(x, y)) {
            timeSinceClick = 4;
            hasChanged = true;
            listener.onClick(x, y);
        }
    }

    @Override
    public void onMouseMove(int x, int y) {
        if (inBounds(x, y)) {
            if (!isHovering) hasChanged = true;
            isHovering = true;
        } else {
            if (isHovering) hasChanged = true;
            isHovering = false;
        }
    }

    @Override
    public void tick() {
        if (timeSinceClick == 1) hasChanged = true;
        if (timeSinceClick != 0) timeSinceClick--;
    }
}
