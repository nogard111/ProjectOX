package com.github.nogard111;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameDialog extends JDialog implements GameNotifications {
    private JPanel contentPane;
    private JButton buttonCancel;
    private MyDrawPanel myDrawPanel;
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

        var configLoader = new ConfigLoader(System.in);
        var config = configLoader.getConfigFromUser();

        config = new GameConfig((byte)3,(byte)4,(byte)3,"Adam","Ewa");

        GameEngine game = new GameEngine(this, config);
        myDrawPanel = new MyDrawPanel(game);

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

    @Override
    public void showFinalWinnerAndClose(String winnerMessage) {
        final JDialog modelDialog = createWinnerDialog(this,winnerMessage);
        modelDialog.setVisible(true);
        Finish();
    }

    private JDialog createWinnerDialog(GameDialog gameDialog, String winnerMessage) {
        final JDialog modelDialog = new JDialog(gameDialog, winnerMessage,
                Dialog.ModalityType.DOCUMENT_MODAL);
        modelDialog.setBounds(132, 132, 400, 400);

        Container dialogContainer = modelDialog.getContentPane();

        JButton okButton = new JButton(winnerMessage + " (Click me)");
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
