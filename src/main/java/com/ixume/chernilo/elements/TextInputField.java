package com.ixume.chernilo.elements;

import com.ixume.chernilo.listeners.TextInputFieldListener;
import com.ixume.chernilo.render.ActiveSectionUtils;
import com.ixume.chernilo.render.RenderUtils;
import com.ixume.chernilo.render.TextRenderer;
import com.ixume.chernilo.text.Text;
import com.ixume.chernilo.text.TextWrapper;
import com.ixume.karta.gui.MapElement;
import com.ixume.karta.gui.events.Clickable;
import com.ixume.karta.gui.events.Tickable;
import com.ixume.karta.gui.events.Typable;
import com.ixume.karta.screen.MapScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class TextInputField extends MapElement implements Typable, Clickable, Tickable {
    private final int width;
    private final int height;
    private final Text defaultText;
    private Text inputTextLines;
    private TextInputFieldListener textInputListener;
    private boolean isValid;
    private boolean isSelected;
    private int underscoreBlink;
    private final int scale;

    public TextInputField(MapScreen mapScreen, int zIndex, int x, int y, int width, int height, Text defaultText, int scale) {
        super(mapScreen, zIndex, x, y);
        this.width = width;
        this.height = height;
        this.defaultText = defaultText;
        inputTextLines = new Text(defaultText.getText(), defaultText.getFont(), defaultText.getColor(), defaultText.getShadow(), defaultText.getScale());
        isValid = true;
        isSelected = false;
        underscoreBlink = 15;
        this.scale = scale;
    }

    public void setTextInputListener(TextInputFieldListener textInputListener) {
        this.textInputListener = textInputListener;
    }

    @Override
    public void tick() {
        underscoreBlink--;
        if (underscoreBlink == 0 || underscoreBlink == 6) {
            if (inputTextLines.getText().isEmpty() && isSelected) {
                hasChanged = true;
            }

            if (underscoreBlink == 0) {
                underscoreBlink = 15;
            }
        }
    }

    @Override
    public boolean[] updateActiveSections() {
        activeSections = ActiveSectionUtils.sectionsFromRectangle(mapScreen, x, y, x + width, y + height);
        return activeSections;
    }

    @Override
    public void drawElement() {
        inputTextLines.setText(inputTextLines.getText().stream().limit(height).collect(Collectors.toList()));
        RenderUtils.fillRectangle(mapScreen, x + scale, y + scale, x + width - scale, y + height, (byte) 119);
        RenderUtils.fillRectangle(mapScreen, x, y, x + scale, y + height, (byte) 34);
        RenderUtils.fillRectangle(mapScreen, x + scale + 1, y, x + width, y + scale, (byte) 34);
        RenderUtils.fillRectangle(mapScreen, x + scale + 1, y + height - scale, x + width, y + height, (byte) 34);
        RenderUtils.fillRectangle(mapScreen, x + width - scale, y + scale + 1, x + width, y + height - scale, (byte) 34);
        System.out.println(inputTextLines.getShadow());
        TextRenderer.drawText(mapScreen, inputTextLines.getText().isEmpty() ? defaultText : inputTextLines, x + 8, y + height - 8 - inputTextLines.getFont().getHeight() * scale);
    }

    @Override
    public void onTextInput(String input) {
        System.out.println("input: \"" + input + "\"");
        if (isSelected) {
            isValid = textInputListener.onTextInput(input);
            hasChanged = true;
            if (input.isEmpty()) {
                System.out.println("empty");
                inputTextLines.setText(new ArrayList<>());
            } else {
                inputTextLines.setText(Collections.singletonList(input));
                inputTextLines = TextWrapper.wrapText(inputTextLines, width - scale - 8);
            }
        }
    }

    @Override
    public void clear() {
        hasChanged = true;
        inputTextLines.getText().clear();
    }

    @Override
    public void onMouseClick(int x, int y) {
        if (inBounds(x, y)) {
            hasChanged = true;
            isSelected = !isSelected;
        }
    }

    public boolean inBounds(int x, int y) {
        return (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height);
    }
}
