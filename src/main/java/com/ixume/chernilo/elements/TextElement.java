package com.ixume.chernilo.elements;

import com.ixume.chernilo.render.ActiveSectionUtils;
import com.ixume.chernilo.text.Font;
import com.ixume.chernilo.render.TextRenderer;
import com.ixume.chernilo.text.Text;
import com.ixume.karta.gui.MapElement;
import com.ixume.karta.screen.MapScreen;

public class TextElement extends MapElement {
    private Text text;

    public TextElement(MapScreen mapScreen, int zIndex, int x, int y, Text text) {
        super(mapScreen, zIndex, x, y);
        this.text = text;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public boolean[] updateActiveSections() {
        activeSections = ActiveSectionUtils.sectionsFromText(mapScreen, text, x, y);
        return activeSections;
    }

    @Override
    public void drawElement() {
        TextRenderer.drawText(mapScreen, text, x, y);
    }
}
