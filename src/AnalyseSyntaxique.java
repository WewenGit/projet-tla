import java.util.List;

public class AnalyseSyntaxique {

	private List<Token> tokens;
    private int pos;

    public TypeDeToken getTypeDeToken(){
        return tokens.get(pos).getTypeDeToken();
    }

    public Token lireToken(){
        return tokens.get(pos++);
    }

    public boolean finAtteinte(){
        return pos>=tokens.size();
    }

    public int S(){
        if (this.getTypeDeToken()==TypeDeToken.intVal || this.getTypeDeToken()==TypeDeToken.leftPar) {
            int i = A();
            return S_prime(i);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public int S_prime(int i){
        if (finAtteinte()) {
            return i;
        }
        if (getTypeDeToken()==TypeDeToken.add) {
            lireToken();
            return i + S();
        }
        if (getTypeDeToken()==TypeDeToken.rightPar) {
            return i;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public int A(){
        if (this.getTypeDeToken()==TypeDeToken.intVal) {
            int i = Integer.valueOf(tokens.get(pos).getValeur());
            lireToken();
            return A_prime(i);
        }
        if (this.getTypeDeToken()==TypeDeToken.leftPar) {
            lireToken();
            int i = S();
            lireToken();
            return A_prime(i);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public int A_prime(int i){
        if (finAtteinte()) {
            return i;
        }
        if (this.getTypeDeToken()==TypeDeToken.multiply) {
            lireToken();
            return i * A();
        }
        if (this.getTypeDeToken()==TypeDeToken.add) {
            return i;
        }
        if (this.getTypeDeToken()==TypeDeToken.rightPar) {
            return i;
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public Integer analyse(List<Token> tokens){
        this.pos=0;
        this.tokens=tokens;
        return S();
    }

}