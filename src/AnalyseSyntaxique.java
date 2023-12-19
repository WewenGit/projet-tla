import java.util.List;
import java.util.ArrayList;

public class AnalyseSyntaxique {

	private List<Token> tokens;
    private int pos;
    private List<Scene> scenes;
    private List<Choice> choices;
    private Game game;


    public TypeDeToken getTypeDeToken(){
        return tokens.get(pos).getTypeDeToken();
    }

    public String getValeur(){
        return tokens.get(pos).getValeur();
    }

    public int getNumber(){
        return tokens.get(pos).getNumber();
    }

    public void nextToken(){
        this.pos++;
    }

    public boolean finAtteinte(){
        return pos>=tokens.size();
    }

    public Game analyse(List<Token> tokens){
        this.pos=0;
        this.tokens=tokens;
        this.scenes = new ArrayList<>();
        this.choices = new ArrayList<>();
        S();
        this.game = new Game(scenes,choices);
        return game;
    }



///TRAITEMENT



    public void S(){
        if (this.getTypeDeToken()==TypeDeToken.scene) {
            int number = getNumber();
            Scene s = new Scene(number, getValeur(), false);
            scenes.add(s);
            nextToken();
            C(number);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public void C(int relatedTo){
        if (this.getTypeDeToken()==TypeDeToken.choice) {
            Choice c = new Choice(relatedTo, getValeur(), getNumber());
            choices.add(c);
            nextToken();
            if (!finAtteinte()){
                if (this.getTypeDeToken()==TypeDeToken.scene) {
                    S();
                }
                if (this.getTypeDeToken()==TypeDeToken.choice) {
                    C(relatedTo);
                }
                if (this.getTypeDeToken()==TypeDeToken.end) {
                    E();
                }
            }
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public void E(){
        Scene s = new Scene(getNumber(), getValeur(), true);
        scenes.add(s);
        nextToken();
        if (!finAtteinte()){
            if (this.getTypeDeToken()==TypeDeToken.scene) {
                S();
            }
            if(this.getTypeDeToken()==TypeDeToken.end) {
                E();
            }
            if (this.getTypeDeToken()==TypeDeToken.choice){
                throw new IllegalArgumentException();
            }
        }
    }

}