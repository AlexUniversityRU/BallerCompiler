/**
 * Created by Owner on 05-Feb-17.
 * Statements → Statement ; Statements | end
 * Statement ⇒ id = Expr | print id
 * Expr → Term | Term + Expr | Term – Expr
 * Term ⇒ Factor | Factor * Term
 * Factor → int | id | ( Expr )
 */
public class Parser {


    public Parser(Lexer lexer) {
        this.checkLexer(lexer);
    }


    private void checkLexer(Lexer lex) {

        for (int i = 0; i < 10; i++) {
            Token test = lex.nextToken();
            System.out.println("Lex: " + test.lexeme + " , Token: " + test.tCode);
        }

    }
}

