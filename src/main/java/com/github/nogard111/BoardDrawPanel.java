package com.github.nogard111;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardDrawPanel extends JPanel {

  IGameEngine gameEngine;
  private int height = 30;
  private int width = 30;

  public BoardDrawPanel(IGameEngine engine) {
    gameEngine = engine;

    setBorder(BorderFactory.createLineBorder(Color.black));

    // I don't think that a better way is for UI to decide
    // (execute business rules if you can/cannot place symbol somewhere or replace symbol)
    // rules of the game are business logic - it can change and UI should not care
    addMouseListener(new MouseAdapter() {

      public void mousePressed(MouseEvent e) {
        boolean repaint = gameEngine.clicked((float) (e.getX()) / getWidth(), (float) (e.getY()) / getHeight());
        if (repaint) {
          repaint();
        }
      }
    });

  }

  public Dimension getPreferredSize() {
    return new Dimension(600, 600);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    //g.drawString("This is my custom Panel!", 10, 20);
    var fields = gameEngine.getFields();

    width = g.getClipBounds().width / fields[0].length;
    height = g.getClipBounds().height / fields.length;

    for (var row : fields) {
      for (var col : row) {
        paintField(g, col);
      }
    }
  }

  private void paintField(Graphics g, Field field) {
    switch (field.type) {
      case NONE:
        break;
      case O:
        g.setColor(Color.GREEN);
        g.drawOval(field.xPos * width, field.yPos * height, width, height);
        break;
      case X:
        g.setColor(Color.RED);
        g.drawLine(field.xPos * width,
                field.yPos * height,
                (field.xPos + 1) * width,
                (field.yPos + 1) * height);
        g.drawLine(field.xPos * width,
                (field.yPos + 1) * height,
                (field.xPos + 1) * width,
                (field.yPos) * height);
        break;

    }
    g.setColor(Color.BLACK);
    g.drawRect(field.xPos * width, field.yPos * height, width, height);
  }
}
