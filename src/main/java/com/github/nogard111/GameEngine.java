package com.github.nogard111;

import java.util.HashMap;
import java.util.Map;

public class GameEngine implements IGameEngine {
    private int gamesToPlay = 3;
    private int sizeY;
    private int sizeX;
    private int lenToWin;
    private Field[][] fields;
    private FieldType currentPlayer;
    private FieldType startPlayer;
    private Map<FieldType, Player> players = new HashMap<>();
    private GameNotifications notificationsPresenter;

    /**
     * @param notificationPresenter
     * @param config
     */
    public GameEngine(final GameNotifications notificationPresenter, GameConfig config) {
        notificationsPresenter = notificationPresenter;

        String playerOName = config.playerO;
        String playerXName = config.playerX;
        sizeX = config.columnSize;
        sizeY = config.rowSize;
        lenToWin = config.lenToWin;
        startPlayer = config.startingPlayer;
        currentPlayer = config.startingPlayer;

        players.put(FieldType.O, new Player(playerOName));
        players.put(FieldType.X, new Player(playerXName));

        fields = BoardHelper.GenerateFields(sizeY, sizeX);

    }

    /**
     * @param x Ratio of the board from 0 to 1
     * @param y Ratio of the board from 0 to 1
     * @return Does click cause change
     */
    @Override
    public boolean clicked(float x, float y) {
        Field selected = getField(x, y);
        if (selected.type == FieldType.NONE) {
            selected.type = currentPlayer;
            var winners = GetWinners();
            if (winners != null) {
                finishStage(winners);
            } else {
                switchPlayer();
                displayWhoShouldMove();
            }
            return true;
        }
        return false;
    }

    void displayWhoShouldMove() {
        notificationsPresenter.displayMessage("Make your move " + players.get(currentPlayer).name);
    }


    private void finishStage(FieldType[] winners) {
        String winnerMessage;
        if (winners.length == 2) {
            for (var winner : winners) {
                ++players.get(winner).score;
            }
            winnerMessage = "It's Tie";
        } else {
            var winner = players.get(winners[0]);
            winner.score += 3;
            winnerMessage = winner.name + " wins!";
        }

        showScore();

        gamesToPlay--;
        if (gamesToPlay == 0) {
            notificationsPresenter.showFinalWinnerAndClose("FINAL WINNER IS : " + GetWinner());
        } else {
            clearCurrentStage();
            notificationsPresenter.showWinnerMessage(winnerMessage);
        }

        startPlayer = getOppositePlayer(startPlayer);
        currentPlayer = startPlayer;
        displayWhoShouldMove();
    }

    private void showScore() {
        var score = players.get(FieldType.O).getScore() + " " + players.get(FieldType.X).getScore();
        notificationsPresenter.displayScore(score);
    }

    private void clearCurrentStage() {
        for (int y = 0; y < fields.length; y++) {
            var row = fields[y];
            for (int x = 0; x < row.length; x++) {
                row[x].type = FieldType.NONE;
            }
        }
    }

    private String GetWinner() {
        var player1 = players.get(FieldType.O);
        var player2 = players.get(FieldType.X);

        if (player1.score == player2.score) {
            return "NONE !";
        }
        return player1.score > player2.score ? player1.name : player2.name;
    }


    private FieldType[] GetWinners() {
        var collection = BoardHelper.getStandardRules(lenToWin).values();
        WinRule winRules[] = collection.toArray(new WinRule[collection.size()]);

        //check winner
        for (FieldType playerType : players.keySet()) {
            if (BoardHelper.IsPlayerAWinner(winRules, playerType, fields)) {
                return new FieldType[]{playerType};
            }
        }

        //all filled -> tie
        boolean emptyFieldExists = false;
        for (int y = 0; y < fields.length; y++) {
            var row = fields[y];
            for (int x = 0; x < row.length; x++) {
                if (row[x].type == FieldType.NONE) {
                    emptyFieldExists = true;
                    break;
                }
            }
            if (emptyFieldExists) break;
        }
        return emptyFieldExists ? null : new FieldType[]{FieldType.X, FieldType.O};
    }

    private Field getField(float x, float y) {
        return fields[(int) (y * sizeY)][(int) (x * sizeX)];
    }

    private void switchPlayer() {
        currentPlayer = getOppositePlayer(currentPlayer);
    }

    private static FieldType getOppositePlayer(FieldType currentPlayer) {
        return currentPlayer == FieldType.O ? FieldType.X : FieldType.O;
    }

    @Override
    public Field[][] getFields() {
        return fields;
    }

    @Override
    public void onStart() {
        showScore();
        displayWhoShouldMove();
    }
}
