package com.github.nogard111.OXGameUI;

import com.github.nogard111.OXGame.GameNotifications;
import com.github.nogard111.OXGame.IGameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameDialog extends JDialog implements GameNotifications {
  private JPanel contentPane;
  private JButton buttonCancel;
  private JLabel score;
  private JLabel currentMessage;

  private final IGameEngine game;

  public GameDialog(final IGameEngine gameEngine) {
    this.game = gameEngine;
    setupUI();
    setContentPane(contentPane);
    setModal(true);

    var gameDialog = this;
    gameDialog.addWindowListener(new WindowListener() {

      @Override
      public void windowOpened(final WindowEvent e) {
        game.onStart(gameDialog);
      }

      @Override
      public void windowClosing(final WindowEvent e) {

      }

      @Override
      public void windowClosed(final WindowEvent e) {

      }

      @Override
      public void windowIconified(final WindowEvent e) {

      }

      @Override
      public void windowDeiconified(final WindowEvent e) {

      }

      @Override
      public void windowActivated(final WindowEvent e) {

      }

      @Override
      public void windowDeactivated(final WindowEvent e) {

      }
    });
    buttonCancel.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        onCancel();
      }
    });

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(final WindowEvent e) {
        onCancel();
      }
    });

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        onCancel();
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  }


  private void onOK() {
    // add your code here
    dispose();
  }

  private void onCancel() {
    // add your code here if necessary
    dispose();
  }

  public void finish() {
    onOK();
  }

  @Override
  public void displayMessage(final String message) {
    currentMessage.setText(message);
  }

  @Override
  public void displayScore(final String message) {
    score.setText(message);
  }

  @Override
  public void showWinnerMessage(final String winnerMessage) {
    JOptionPane.showMessageDialog(this, winnerMessage);
  }

  @Override
  public void showFinalWinnerAndClose(final String winnerMessage) {
    JOptionPane.showMessageDialog(this, winnerMessage);
    finish();
  }

  /**
   * Method generated by Me because IntelliJ IDEA GUI Designer Have issues !.
   *
   * @noinspection ALL
   */
  private void setupUI() {
    BoardDrawPanel boardDrawPanel = new BoardDrawPanel(game);
    contentPane = new JPanel(new BorderLayout());

    buttonCancel = new JButton();
    buttonCancel.setText("I dont care ! Quit");
    currentMessage = new JLabel();
    currentMessage.setText("Label");
    score = new JLabel();
    score.setText("Label");

    var messagePanel = new JPanel(new BorderLayout());
    messagePanel.add(currentMessage, BorderLayout.EAST);
    messagePanel.add(score, BorderLayout.WEST);

    var bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.add(buttonCancel, BorderLayout.EAST);
    bottomPanel.add(messagePanel, BorderLayout.CENTER);

    contentPane.add(bottomPanel, BorderLayout.SOUTH);
    contentPane.add(boardDrawPanel, BorderLayout.CENTER);
  }
}