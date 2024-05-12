package com.ixume.chernilo.elements;

import com.ixume.karta.gui.MapElement;
import com.ixume.karta.gui.events.Hoverable;
import com.ixume.karta.screen.MapScreen;

public abstract class Tooltip extends MapElement implements Hoverable {
    protected int width;
    protected int height;

    public Tooltip(MapScreen mapScreen, int zIndex, int x, int y, int width, int height) {
        super(mapScreen, zIndex, x, y, false);
        this.width = width;
        this.height = height;
        System.out.println("width: " + width);
        System.out.println("height: " + height);
    }

    @Override
    public void onMouseMove(int x, int y) {
        boolean isInBounds = inBounds(x, y);
        if (isVisible != isInBounds || isInBounds) hasChanged = true;
        isVisible = isInBounds;
        if (isInBounds) {
            this.x = Math.max(0, x);
            this.x = Math.min(mapScreen.getWidth() * 128 - width - 1, x);
            this.y = Math.max(0, y);
            this.y = Math.min(mapScreen.getHeight() * 128 - height - 1, y);
        }
    }

    protected abstract boolean inBounds(int x, int y);
}
