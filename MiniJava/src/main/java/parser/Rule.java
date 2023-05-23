package parser;

import scanner.token.Token;

import java.util.ArrayList;

/**
 * Created by mohammad hosein on 6/25/2015.
 */

public class Rule {
    public Rule(String stringRule) {
        int index = stringRule.indexOf("#");
        if (index != -1) {
            try {
                semanticAction = Integer.parseInt(stringRule.substring(index + 1));
            } catch (NumberFormatException ex) {
                semanticAction = 0;
            }
            stringRule = stringRule.substring(0, index);
        } else {
            semanticAction = 0;
        }
        String[] splited = stringRule.split("->");
//        try {
        LHS = NonTerminal.valueOf(splited[0]);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        RHS = new ArrayList<GrammarSymbol>();
        if (splited.length > 1) {
            String[] RHSs = splited[1].split(" ");
            for (String s : RHSs) {
                try {
                    RHS.add(new NonTerminalGrammarSymbol(NonTerminal.valueOf(s)));
                } catch (Exception e) {
//                    try{
                    RHS.add(new TerminalGrammarSymbol(new Token(Token.getTyepFormString(s), s)));
//                    }catch (IllegalArgumentException d){
//                        d.printStackTrace();
//                        Log.print(s);
//                    }
                }
            }
        }
    }

    public NonTerminal LHS;
    public ArrayList<GrammarSymbol> RHS;
    public int semanticAction;
}

interface GrammarSymbol {
//    public boolean isTerminal;
//    public NonTerminal nonTerminal;
//    public Token terminal;

//    public GrammarSymbol(NonTerminal nonTerminal) {
//        this.nonTerminal = nonTerminal;
//        isTerminal = false;
//    }
//
//    public GrammarSymbol(Token terminal) {
//        this.terminal = terminal;
//        isTerminal = true;
//    }

    Boolean isTerminal();
}

class TerminalGrammarSymbol implements GrammarSymbol {
    private final Token terminal;

    TerminalGrammarSymbol(Token terminal) {
        this.terminal = terminal;
    }

    @Override
    public Boolean isTerminal() {
        return true;
    }

    public Token getTerminal() {
        return terminal;
    }
}

class NonTerminalGrammarSymbol implements GrammarSymbol {
    private final NonTerminal nonTerminal;

    NonTerminalGrammarSymbol(NonTerminal nonTerminal) {
        this.nonTerminal = nonTerminal;
    }

    @Override
    public Boolean isTerminal() {
        return false;
    }

    public NonTerminal getNonTerminal() {
        return nonTerminal;
    }
}
