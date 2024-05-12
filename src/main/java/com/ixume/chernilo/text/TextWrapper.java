package com.ixume.chernilo.text;

import java.util.ArrayList;
import java.util.List;

public class TextWrapper {
    public static Text wrapText(Text text, int width) {
        width -= (text.hasShadow() ? 1 : 0) * text.getScale();
        List<String> lines = new ArrayList<>();
        String singletonText = text.getText().get(0);
        int startIndex = 0;
        int endIndex = 0;
        for(int i = 0; i < singletonText.length(); ++i) {
            char ch = singletonText.charAt(i);
            if (ch == ' ') {
                endIndex = i;
                continue;
            }

            if (ch == '\n') {
                endIndex = i;
                String lineString = singletonText.substring(startIndex, endIndex);
                lines.add(lineString);
                endIndex += 1;
                startIndex = endIndex;
                continue;
            }

            int currentWidth = text.getFont().getWidth(singletonText.substring(startIndex, i + 1)) * text.getScale();
            if (currentWidth > width) {
                if (startIndex == endIndex) {//long word
                    endIndex = i;
                }

                String lineString = singletonText.substring(startIndex, endIndex);
                lines.add(lineString);
                startIndex = ++endIndex;
            }
        }

        lines.add(singletonText.substring(startIndex));

        return new Text(lines, text.getFont(), text.getColor(), text.getShadow(), text.getScale());
    }
}
