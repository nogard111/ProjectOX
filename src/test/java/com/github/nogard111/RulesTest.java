package com.github.nogard111;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
}
