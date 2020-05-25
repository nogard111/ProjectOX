package com.github.nogard111;

import java.awt.*;

public class Field {
    public static int width = 20;
    public static int height = 20;
    public final int xPos;
    public final int yPos;
    public FieldType type = FieldType.NONE;

    public Field(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public void paintField(Graphics g) {
        switch (type) {
            case NONE:
                break;
            case O:
                g.setColor(Color.GREEN);
                g.fillRect(xPos*width, yPos*height, width, height);
                break;
            case X:
                g.setColor(Color.RED);
                g.fillRect(xPos*width, yPos*height, width, height);
                break;

        }
    }
}
