import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
import org.omg.IOP.ExceptionDetailMessage;

import java.util.concurrent.ExecutionException;

/**
 * Created by Owner on 05-Feb-17.
 * Statements → Statement ; Statements | end
 * Statement ⇒ id = Expr | print id
 * Expr → Term | Term + Expr | Term – Expr
 * Term ⇒ Factor | Factor * Term
 * Factor → int | id | ( Expr )
 */
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
            Token test = lex.nextToken();
            System.out.println("Lex: " + test.lexeme + " , Token: " + test.tCode);
        }
    }

    public void parse() {
        System.out.println("parse()");
        this.currToken = lexer.nextToken();
        statements();
    }

    private void statements() {
        //System.out.println("Statements");
        if(currToken.tCode == Token.TokenCode.END) {
            System.out.println("Statements -> end");
            return;
        } else {
            System.out.println("Statements -> Statement");
            statement();
            if(currToken.tCode == Token.TokenCode.SEMICOL) {
                System.out.println("Statements -> Statement ;");
                currToken = lexer.nextToken();
                System.out.println("Statements -> Statement ; Statements");
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
            System.out.println("Statement -> ID");
            currToken = lexer.nextToken();
            if(currToken.tCode == Token.TokenCode.ASSIGN) {
                System.out.println("Statement -> ID = ");
                currToken = lexer.nextToken();
                System.out.println("Statement -> ID = Expr");
                expr();
                return;
            }
        } else if (currToken.tCode == Token.TokenCode.PRINT) {
            System.out.println("Statement -> print");
            currToken = lexer.nextToken();
            if (currToken.tCode == Token.TokenCode.ID);
            System.out.println("Statement -> print ID");
            currToken = lexer.nextToken();
            return;
        }
        error("statement");
    }

    private void expr() {
        //System.out.println("expr");
        System.out.println("expr -> term");
        term();
        if (currToken.tCode == Token.TokenCode.ADD || currToken.tCode == Token.TokenCode.SUB) {
            System.out.println("expr -> term +/-");
            currToken = lexer.nextToken();
            System.out.println("expr -> term +/- expr");
            expr();
            return;
        } /*else {
            error("expr");
        }*/
    }

    private void term() {
        //System.out.println("term");
        System.out.println("term -> factor");
        factor();
        if(currToken.tCode == Token.TokenCode.MULT) {
            System.out.println("term -> factor *");
            currToken = lexer.nextToken();
            System.out.println("term -> factor * term");
            term();
        }
        return;

    }

    private void factor() {
        //System.out.println("factor");
        if(currToken.tCode == Token.TokenCode.INT || currToken.tCode == Token.TokenCode.ID) {
            System.out.println("factor -> INT/ID");
            currToken = lexer.nextToken();
            return;
        } else if ( currToken.tCode == Token.TokenCode.LPAREN) {
            System.out.println("factor -> (");
            currToken = lexer.nextToken();
            System.out.println("factor -> ( expr");
            expr();
            if(currToken.tCode == Token.TokenCode.RPAREN) {
                System.out.println("factor -> ( expr )");
                currToken = lexer.nextToken();
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

