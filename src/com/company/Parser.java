public class Parser {

    private Lexer lexer;
    private Token currToken;

    public Parser(Lexer lexer) {
        //this.checkLexer(lexer);
        this.lexer = lexer;
        this.parse();
    }


    private void checkLexer(Lexer lex) {

        for (int i = 0; i < 18; i++) {
            Token test = lex.getNextToken();
            System.out.println("Lex: " + test.lexeme + " , Token: " + test.tCode);
        }
    }

    public void parse() {
        //System.out.println("parse()");
        this.currToken = lexer.getNextToken();
        statements();
    }

    private void statements() {
        //System.out.println("Statements");
        if(currToken.tCode == Token.TokenCode.END) {
            //System.out.println("Statements -> end");
            return;
        } else {
            //System.out.println("Statements -> Statement");
            statement();
            if(currToken.tCode == Token.TokenCode.SEMICOL) {
                //System.out.println("Statements -> Statement ;");
                currToken = lexer.getNextToken();
                //System.out.println("Statements -> Statement ; Statements");
                statements();
                return;
            } else {
                error("statements");
            }
        }
    }

    private void statement() {
        //System.out.println("Statement");
        if(currToken.tCode == Token.TokenCode.ID) {
            //System.out.println("Statement -> ID");
            System.out.println("PUSH " + currToken.lexeme);
            currToken = lexer.getNextToken();
            // kajdlshfaæskldf System.out.println("PUSH ID");
            if(currToken.tCode == Token.TokenCode.ASSIGN) {
                //System.out.println("Statement -> ID = ");
                currToken = lexer.getNextToken();
                //System.out.println("Statement -> ID = Expr");
                expr();
                System.out.println("ASSIGN");
                return;
            }
        } else if (currToken.tCode == Token.TokenCode.PRINT) {
            //System.out.println("Statement -> print");
            currToken = lexer.getNextToken();
            System.out.println("PUSH " + currToken.lexeme);
            if (currToken.tCode == Token.TokenCode.ID) {
                /// - dslkjf aælkdjf System.out.println("PUSH ID");
                //System.out.println("Statement -> print ID");
                currToken = lexer.getNextToken();
                System.out.println("PRINT");
                return;
            }
            error("statement");
        }
        error("statement");
    }

    private void expr() {
        //System.out.println("expr");
        //System.out.println("expr -> term");
        term();
        if (currToken.tCode == Token.TokenCode.ADD) {
            //System.out.println("expr -> term +");
            currToken = lexer.getNextToken();
            //System.out.println("expr -> term + expr");
            expr();
            System.out.println("ADD");
            return;
        } else if (currToken.tCode == Token.TokenCode.SUB) {
            //System.out.println("expr -> term -");
            currToken = lexer.getNextToken();
            //System.out.println("expr -> term - expr");
            expr();
            System.out.println("SUB");
            return;
        }
    }

    private void term() {
        //System.out.println("term");
        //System.out.println("term -> factor");
        factor();
        if(currToken.tCode == Token.TokenCode.MULT) {
            //System.out.println("term -> factor *");
            currToken = lexer.getNextToken();
            //System.out.println("term -> factor * term");
            term();
            System.out.println("MULT");
        }
        return;

    }

    private void factor() {
        //System.out.println("factor");
        if(currToken.tCode == Token.TokenCode.INT || currToken.tCode == Token.TokenCode.ID) {
            //System.out.println("factor -> INT/ID");
            System.out.println("PUSH " + currToken.lexeme);
            currToken = lexer.getNextToken();
            return;
        } else if ( currToken.tCode == Token.TokenCode.LPAREN) {
            //System.out.println("factor -> (");
            currToken = lexer.getNextToken();
            //System.out.println("factor -> ( expr");
            expr();
            if(currToken.tCode == Token.TokenCode.RPAREN) {
                //System.out.println("factor -> ( expr )");
                currToken = lexer.getNextToken();
                return;
            }

        }
        error("factor");


    }

    private void error(String from ) {
        throw new Error("Error from: " + from);
        //System.out.println("Error from: " + from);
    }
}

