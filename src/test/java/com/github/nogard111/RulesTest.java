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
        var rule = BoardHelper.getStandardRules(3).get(BoardHelper.WinRuleType.VERTICAL);
        Field[][] fields = BoardHelper.GenerateFields(3,3);

        fields[0][0].type = FieldType.O;
        fields[1][0].type = FieldType.O;
        fields[2][0].type = FieldType.O;

        assertTrue(BoardHelper.IsPlayerAWinner(new WinRule[]{rule}, FieldType.O, fields));
    }

    /**
     * Vertical Test
     */
    @Test
    public void verticalRuleFalse()
    {
        var rule = BoardHelper.getStandardRules(3).get(BoardHelper.WinRuleType.VERTICAL);

        Field[][] fields = BoardHelper.GenerateFields(3,3);


        fields[0][0].type = FieldType.O;
        fields[0][1].type = FieldType.O;
        fields[0][2].type = FieldType.O;


        assertFalse(BoardHelper.IsPlayerAWinner(new WinRule[]{rule}, FieldType.O, fields));
    }

    /**
     * Horizontal Test
     */
    @Test
    public void horizontalRule()
    {
        var rule = BoardHelper.getStandardRules(3).get(BoardHelper.WinRuleType.HORIZONTAL);

        Field[][] fields = BoardHelper.GenerateFields(3,3);

        fields[0][0].type = FieldType.O;
        fields[0][1].type = FieldType.O;
        fields[0][2].type = FieldType.O;

        assertTrue(BoardHelper.IsPlayerAWinner(new WinRule[]{rule}, FieldType.O, fields));
    }


    /**
     * Horizontal Test
     */
    @Test
    public void horizontalRuleFalse()
    {
        var rule = BoardHelper.getStandardRules(3).get(BoardHelper.WinRuleType.HORIZONTAL);

        Field[][] fields = BoardHelper.GenerateFields(3,3);

        fields[0][0].type = FieldType.O;
        fields[1][0].type = FieldType.O;
        fields[2][0].type = FieldType.O;

        assertFalse(BoardHelper.IsPlayerAWinner(new WinRule[]{rule}, FieldType.O, fields));
    }
}
