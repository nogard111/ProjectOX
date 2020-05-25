package com.github.nogard111;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameDialog extends JDialog implements GameNotifications {
    private JPanel contentPane;
    private JButton buttonCancel;
    private MyDrawPanel myDrawPanel1;
    private JLabel score;
    private JLabel currentMessage;

    public GameDialog() {
        setContentPane(contentPane);
        setModal(true);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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

    private void createUIComponents() {
        GameEngine game = new GameEngine(this);
        myDrawPanel1 = new MyDrawPanel(game);

       //todo:  myDrawPanel1.addActionListener(); Finish()
    }

    public void Finish()
    {
        onOK();
    }

    @Override
    public void displayMessage(String message) {
        currentMessage.setText(message);
    }

    @Override
    public void displayScore(String message) {
        score.setText(message);
    }

    @Override
    public void showWinnerMessage(String winnerMessage) {
        final JDialog modelDialog = createWinnerDialog(this,winnerMessage);
                modelDialog.setVisible(true);
    }

    private JDialog createWinnerDialog(GameDialog gameDialog, String winnerMessage) {
        final JDialog modelDialog = new JDialog(gameDialog, winnerMessage,
                Dialog.ModalityType.DOCUMENT_MODAL);
        modelDialog.setBounds(132, 132, 200, 200);

        Container dialogContainer = modelDialog.getContentPane();

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelDialog.setVisible(false);
            }
        });
        dialogContainer.add(okButton);
        return modelDialog;
    }
}
