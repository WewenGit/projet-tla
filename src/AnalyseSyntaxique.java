import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;

public class AnalyseSyntaxique {

    
	private List<Token> tokens;
    private int pos;

    private class Scene{
        private String text;
        private boolean isEnd;
    
        public Scene(String text, boolean isEnd) {
            this.text = text;
            this.isEnd = isEnd;
        }
    
        public String getText() {
            return text;
        }
    
        public boolean isEnd() {
            return isEnd;
        }
    }

    private class Choice{
        private String text;
        private int nextScene;
    
        public Choice(String text, int nextScene) {
            this.text = text;
            this.nextScene = nextScene;
        }
    
        public String getText() {
            return text;
        }
    
        public int getNextScene() {
            return nextScene;
        }
    }


    private List<Scene> scenes;
    private List<Choice> choices;
    private int numberOfChoices;


    public TypeDeToken getTypeDeToken(){
        return tokens.get(pos).getTypeDeToken();
    }

    public Token getToken(){
        return tokens.get(pos);
    }

    public void nextToken(){
        this.pos++;
    }

    public boolean finAtteinte(){
        return pos>=tokens.size();
    }

    public void analyse(List<Token> tokens, JFrame window){
        this.pos=0;
        this.numberOfChoices=0;
        this.tokens=tokens;
        this.scenes = new ArrayList<>();
        this.choices = new ArrayList<>();
        S();
    }



///TRAITEMENT



    public void S(){
        if (this.getTypeDeToken()==TypeDeToken.scene) {
            Scene s = new Scene(getToken().getValeur(), false);
            scenes.set(getToken().getNumber(),s);
            nextToken();
            C();
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public void C(){
        if (this.getTypeDeToken()==TypeDeToken.choice) {
            Choice c = new Choice(getToken().getValeur(), getToken().getNumber());
            choices.set(numberOfChoices++,c);
            nextToken();
            if (!finAtteinte()){
                if (this.getTypeDeToken()==TypeDeToken.scene) {
                    S();
                }
                if (this.getTypeDeToken()==TypeDeToken.choice) {
                    C();
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
        Scene s = new Scene(getToken().getValeur(), true);
        scenes.set(getToken().getNumber(), s);
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