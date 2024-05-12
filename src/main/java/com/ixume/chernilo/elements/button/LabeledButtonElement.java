package com.ixume.chernilo.elements.button;

import com.ixume.chernilo.elements.TextElement;
import com.ixume.chernilo.text.Font;
import com.ixume.chernilo.text.Text;
import com.ixume.karta.screen.MapScreen;

public class LabeledButtonElement extends ButtonElement {
    private final TextElement textElement;

    public LabeledButtonElement(MapScreen mapScreen, int zIndex, int x, int y, int width, int height, int scale, Text text) {
        super(mapScreen, zIndex, x, y, width, height, scale);

        int textWidth = text.getFont().getWidth(text.getText().get(0)) * text.getScale();
        int textHeight = text.getFont().getHeight() * text.getScale();
        textElement = new TextElement(mapScreen, zIndex + 1, x + width / 2 - textWidth / 2, y + height / 2 - textHeight / 2, text);
    }

    @Override
    public void drawElement() {
        renderButton();
        textElement.render();
    }
}
