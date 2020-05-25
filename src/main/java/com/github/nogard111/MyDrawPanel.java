package com.github.nogard111;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MyDrawPanel extends JPanel {

    GameNotifications notificationsPresenter;

    public MyDrawPanel(GameNotifications notificationPresenter) {
        notificationsPresenter = notificationPresenter;
        initialize();

        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                boolean repaint = clicked((float) (e.getX()) / getWidth(), (float) (e.getY()) / getHeight());
                if (repaint) {
                    repaint();
                }
            }
        });

    }


    private int sizeY = 3;
    private int sizeX = 3;
    private int lenToWin = 3;
    private Field[][] fields;
    private FieldType currentPlayer;
    private Map<FieldType, Player> players = new HashMap<>();
    ;

    private void initialize() {
        //todo: ask for names
        String playerOName = "Adam";
        String playerXName = "Ewa";
        sizeX = 3;
        sizeY = 4;
        lenToWin = 3;

        players.put(FieldType.O, new Player(playerOName));
        players.put(FieldType.X, new Player(playerXName));

        fields = new Field[sizeY][sizeX];
        for (int y = 0; y < fields.length; y++) {
            var row = fields[y];
            for (int x = 0; x < row.length; x++) {
                row[x] = new Field(x, y);
            }
        }

        currentPlayer = FieldType.O;
    }

    public boolean clicked(float x, float y) {
        Field selected = getField(x, y);
        if (selected.type == FieldType.NONE) {
            selected.type = currentPlayer;
            var winners = checkWinners();
            if (winners != null) {
                scoreUpdate(winners);
            } else {
                switchPlayer();
                notificationsPresenter.displayMessage("Make your move " + players.get(currentPlayer).name);
            }
            return true;
        }
        return false;
    }


    private void scoreUpdate(FieldType[] winners) {
        String winnerMessage;
        if(winners.length == 2) {
            for (var winner : winners) {
                ++players.get(winner).score;
            }
            winnerMessage = "It's Tie";
        }else {
            var winner = players.get(winners[0]);
            winner.score += 3;
            winnerMessage = winner.name+ " wins!";
        }

        var score = players.get(FieldType.O).getScore() + " " + players.get(FieldType.X).getScore();
        notificationsPresenter.displayScore(score);
        notificationsPresenter.showWinnerMessage(winnerMessage);
    }


    private FieldType[] checkWinners() {
        //Arrays.sort();
        class WinRule{
            public int limitRows;
            public int limitColumn;
            public int noOfChecks;
            public Function<Point, Point> getNextPointToCheck;


            public WinRule(int limitRows, int limitColumn,int noOfChecks, Function<Point, Point> getNextPointToCheck) {
                this.limitRows = limitRows;
                this.limitColumn = limitColumn;
                this.noOfChecks = noOfChecks;
                this.getNextPointToCheck = getNextPointToCheck;
            }
        }
        //public Point NextCheck(Point previous){return }
        WinRule winRules[] = new WinRule[]{
         new WinRule(lenToWin,0,lenToWin,(Point k) -> new Point(k.x+1,k.y)),
         new WinRule(0,lenToWin,lenToWin,(Point k) -> new Point(k.x,k.y+1)),
         new WinRule(lenToWin,lenToWin,lenToWin,(Point k) -> new Point(k.x+1,k.y+1)),
         new WinRule(lenToWin,lenToWin,lenToWin,(Point k) -> new Point(k.x+1,k.y))
        };
        //todo:hgcg
           /*
        //check winner
        for (FieldType playerType:players.keySet()) {
            boolean win = true;
            for (int y = 0; y < fields.length; y++) {
                var row = fields[y];
                for (int x = 0; x < row.length; x++) {

                    if(row[x].type != playerType ) {
                        win = false;
                        break;
                    }
                }
                if(!win)break;
            }
            if(win) {
                return new FieldType[]{playerType};
            }
        }
*/

        //all filled -> tie
        boolean emptyFieldExists = false;
        for (int y = 0; y < fields.length; y++) {
            var row = fields[y];
            for (int x = 0; x < row.length; x++) {
                if(row[x].type == FieldType.NONE) {
                    emptyFieldExists = true;
                    break;
                }
            }
            if(emptyFieldExists)break;
        }
        return emptyFieldExists ?null: new FieldType[]{ FieldType.X,FieldType.O};
    }

    private Field getField(float x, float y) {
        return fields[(int) (y * sizeY)][(int) (x * sizeX)];
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer == FieldType.O ? FieldType.X : FieldType.O;
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("This is my custom Panel!", 10, 20);

        Field.width = g.getClipBounds().width / sizeX;
        Field.height = g.getClipBounds().height / sizeY;

        for (var row : fields) {
            for (var col : row) {
                col.paintField(g);
            }
        }
    }
}
