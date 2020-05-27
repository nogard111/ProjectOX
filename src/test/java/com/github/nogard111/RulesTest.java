package com.github.nogard111;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RulesTest {

    /**
     * Vertical Test
     */
    @Test
    public void verticalRule()
    {
        var rule = GameRules.getStandardRules(3).get(GameRules.WinRuleType.VERTICAL);
        var board = new Board(3,3);

        board.trySetFieldSymbol(FieldType.O, 0, 0);
        board.trySetFieldSymbol(FieldType.O, 0, 1);
        board.trySetFieldSymbol(FieldType.O, 0, 2);

        assertTrue(board.IsPlayerAWinner(new WinRule[]{rule}, FieldType.O));
    }

    /**
     * Vertical Test
     */
    @Test
    public void verticalRuleFalse()
    {
        var rule = GameRules.getStandardRules(3).get(GameRules.WinRuleType.VERTICAL);

        var board = new Board(3,3);

        board.trySetFieldSymbol(FieldType.O, 0, 0);
        board.trySetFieldSymbol(FieldType.O, 1, 0);
        board.trySetFieldSymbol(FieldType.O, 2, 0);


        assertFalse(board.IsPlayerAWinner(new WinRule[]{rule}, FieldType.O));
    }

    /**
     * Horizontal Test
     */
    @Test
    public void horizontalRule()
    {
        var rule = GameRules.getStandardRules(3).get(GameRules.WinRuleType.HORIZONTAL);


        var board = new Board(3,3);

        board.trySetFieldSymbol(FieldType.O, 0, 0);
        board.trySetFieldSymbol(FieldType.O, 1, 0);
        board.trySetFieldSymbol(FieldType.O, 2, 0);


        assertTrue(board.IsPlayerAWinner(new WinRule[]{rule}, FieldType.O));
    }


    /**
     * Horizontal Test
     */
    @Test
    public void horizontalRuleFalse()
    {
        var rule = GameRules.getStandardRules(3).get(GameRules.WinRuleType.HORIZONTAL);

        var board = new Board(3,3);

        board.trySetFieldSymbol(FieldType.O, 0, 0);
        board.trySetFieldSymbol(FieldType.O, 0, 1);
        board.trySetFieldSymbol(FieldType.O, 0, 2);

        assertFalse(board.IsPlayerAWinner(new WinRule[]{rule}, FieldType.O));
    }
}
