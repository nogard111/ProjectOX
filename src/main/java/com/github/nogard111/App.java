package com.github.nogard111;

import com.github.nogard111.GameDialog;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        GameDialog dialog = new GameDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}


