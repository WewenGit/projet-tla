import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalyseSyntaxique {

	private List<Token> tokens;
    private int pos;
    private List<Scene> scenes;
    private List<Choice> choices;
    private Map<String, Boolean> conditions = new HashMap<>();
    private Game game;


    public TypeDeToken getTypeDeToken(){
        if (pos==this.tokens.size()) {
            return null;
        }
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
        if (this.getTypeDeToken()==TypeDeToken.keyWord) {
            if (getValeur()=="Condition") {
                nextToken();
                C();
            }
            else if (getValeur()=="Scene") {
                nextToken();
                SC();
            }
            else if(getValeur()=="Personnage"){
                //TODO
            }
            
        }
        else if (!finAtteinte()) {
            throw new IllegalArgumentException("Missing keyword");
        }
    }

    public void C(){ //creates a condition
        if (getTypeDeToken()==TypeDeToken.text) {
            String sName = getValeur();
            boolean b = true;
            nextToken();
            if (getTypeDeToken()==TypeDeToken.keyWord) {
                String sBool = getValeur();
                if (sBool == "false") {
                    b=false;
                }
                else if (sBool == "true") {
                    b=true;
                }
                else{
                    throw new IllegalArgumentException("condition must be true or false");
                }
            }
            else{
                throw new IllegalArgumentException("Missing keyword after condition name");
            }
            conditions.put(sName, b);
        }
        else{
            throw new IllegalArgumentException("condition needs a name");
        }
        S();
    }

    public void SC(){ //creates a scene
        boolean isFinal=false;
        int sceneNumber=0;
        String sceneText="";
        if (getTypeDeToken()==TypeDeToken.keyWord) {
            if (getValeur()=="Final") {
                isFinal = true;
            }
            else{
                throw new IllegalArgumentException("Can't put a keyword that isn't Final after Scene");
            }
        }
        if (getTypeDeToken()==TypeDeToken.number) {
            sceneNumber = getNumber();
            nextToken();
        }
        else{
            throw new IllegalArgumentException("Missing number");
        }
        if (!(getTypeDeToken()==TypeDeToken.sectionStart)) {
            throw new IllegalArgumentException("Missing section start");
        }
        if (getTypeDeToken()==TypeDeToken.text) {
            sceneText = getValeur();
            nextToken();
        }
        else{
            throw new IllegalArgumentException("Missing scene text");
        }
        Scene sc = new Scene(sceneNumber, sceneText, isFinal);
        scenes.add(sc);
        if (!isFinal) {
            CH(sc);
        }
    }

    public void CH(Scene sc){ //creates choices for a scene
        int choiceNumber=0;
        String choiceText="";
        while (getTypeDeToken()==TypeDeToken.choice || (getTypeDeToken()==TypeDeToken.keyWord && getValeur()=="if")) {
            if (getTypeDeToken()==TypeDeToken.choice) {
                nextToken();
                if (!(getTypeDeToken()==TypeDeToken.number)) {
                    throw new IllegalArgumentException("number expected after choice token");
                }
                choiceNumber=getNumber();
                nextToken();
                if (!(getTypeDeToken()==TypeDeToken.text)) {
                    throw new IllegalArgumentException("text expected after choice number token");
                }
                choiceText=getValeur();
                choices.add(new Choice(sc.getId(), choiceText, choiceNumber));
                nextToken();
            }
            if (getTypeDeToken()==TypeDeToken.keyWord) {
                nextToken();
                if (!(getTypeDeToken()==TypeDeToken.conditionStart)) {
                    throw new IllegalArgumentException("need a ( to start a condition");
                }
                searchCondition();
                if (!(getTypeDeToken()==TypeDeToken.conditionEnd)) {
                    throw new IllegalArgumentException("need a ) to end the condition");
                }
            }
        }
    }

    public void searchCondition(){

    }

}
