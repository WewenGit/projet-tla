import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AnalyseSyntaxique {

	private List<Token> tokens;
    private int pos;
    private List<Scene> scenes;
    private Map<String, Boolean> conditions;
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
        this.conditions = new HashMap<>();
        S();
        this.game = new Game(scenes,conditions);
        return game;
    }
    



///TRAITEMENT

    public void S(){
        if (this.getTypeDeToken()==TypeDeToken.keyWord) {
            if (getValeur().equals("Condition")) {
                nextToken();
                C();
            }
            else if (getValeur().equals("Scene")) {
                nextToken();
                SC();
            }
            else if(getValeur().equals("Personnage")){
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
                if (sBool.equals("false")) {
                    b=false;
                }
                else if (sBool.equals("true")) {
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
        ArrayList<Choice> choices = new ArrayList<Choice>();
        HashMap<String, Boolean> conditionsToChange = new HashMap<String, Boolean>();
        if (getTypeDeToken()==TypeDeToken.keyWord) {
            if (getValeur().equals("Final") || getValeur().equals("final")) {
                isFinal = true;
                nextToken();
            }
            else{
                throw new IllegalArgumentException("Can't put a keyword that isn't Final after Scene");
            }
        }
        if (getTypeDeToken().equals(TypeDeToken.number)) {
            sceneNumber = getNumber();
            nextToken();
        }
        else{
            throw new IllegalArgumentException("Missing scene number");
        }
        if (!getTypeDeToken().equals(TypeDeToken.sectionStart)) {
            throw new IllegalArgumentException("Missing section start");
        }
        while(getTypeDeToken().equals(TypeDeToken.text) || (getTypeDeToken().equals(TypeDeToken.keyWord) && getValeur().equals("Condition")))
        {
			if(getTypeDeToken().equals(TypeDeToken.text))
			{
				sceneText += getValeur()+"\n";
				nextToken();
			}
			else CTC(conditionsToChange);
        }
        if (!isFinal) {
            choices = CH();
        }
        Scene sc = new Scene(sceneNumber, sceneText, choices, conditionsToChange, isFinal);
        scenes.add(sc);
    }
    
    
    
    
    

    public ArrayList<Choice> CH(){ //creates choices for a scene
        int choiceNumber=0;
        String choiceText="";
        ArrayList<String> conditions = new ArrayList<String>();
        ArrayList<Choice> choices = new ArrayList<Choice>();
        while (getTypeDeToken()==TypeDeToken.choice || (getTypeDeToken().equals(TypeDeToken.keyWord) && getValeur().equals("if"))) {
            if (getTypeDeToken().equals(TypeDeToken.choice)) {
                nextToken();
                if (!getTypeDeToken().equals(TypeDeToken.number)) {
                    throw new IllegalArgumentException("number expected after choice token");
                }
                choiceNumber=getNumber();
                nextToken();
                if (!getTypeDeToken().equals(TypeDeToken.text)) {
                    throw new IllegalArgumentException("text expected after choice number token");
                }
                choiceText=getValeur();
                nextToken();
                choices.add(new Choice(choiceText, choiceNumber));
            }
            if (getTypeDeToken().equals(TypeDeToken.keyWord)) {
                nextToken();
                if (!getTypeDeToken().equals(TypeDeToken.conditionStart)) {
                    throw new IllegalArgumentException("need a ( to start a condition");
                }
                nextToken();
                conditions=CO();
                if (!getTypeDeToken().equals(TypeDeToken.conditionEnd)) {
                    throw new IllegalArgumentException("need a ) to end the condition");
                }
                nextToken();
                if (!getTypeDeToken().equals(TypeDeToken.sectionStart)) {
                    throw new IllegalArgumentException("need a { to start the choice section");
                }
                nextToken();
                if (!getTypeDeToken().equals(TypeDeToken.choice)) {
                    throw new IllegalArgumentException("choice token expected before choice number");
                }
                nextToken();
                if (!getTypeDeToken().equals(TypeDeToken.number)) {
                    throw new IllegalArgumentException("number expected after choice token");
                }
                choiceNumber=getNumber();
                nextToken();
                if (!getTypeDeToken().equals(TypeDeToken.text)) {
                    throw new IllegalArgumentException("text expected after choice number token");
                }
                choiceText=getValeur();
                nextToken();
                if (!getTypeDeToken().equals(TypeDeToken.sectionEnd)) {
                    throw new IllegalArgumentException("need a } to end the choice section");
                }
                nextToken();
                choices.add(new Choice(choiceText,choiceNumber,conditions));
            }
        }
        return choices;
    }

    
    
    
    
    
    
    public ArrayList<String> CO(){
    	ArrayList<String> conditions = new ArrayList<String>();
        while(getTypeDeToken().equals(TypeDeToken.keyWord)) {
        	conditions.add(tokens.get(pos).getValeur());
        	nextToken();
        }
        return conditions;
    }
    
    
    
    
    //update conditions to change in a scene
    public void CTC(HashMap<String, Boolean> conditionsToChange)
    {
    	nextToken();
    	if(!getTypeDeToken().equals(TypeDeToken.text))
    		throw new IllegalArgumentException("Text was expected after keyWord 'Condition'.");
    	String conditionName = getValeur();
    	if(!conditions.containsKey(conditionName))
    		conditions.put(conditionName, false);
    	nextToken();
    	if(!(getTypeDeToken().equals(TypeDeToken.keyWord) && Arrays.asList("false", "true").contains(getValeur())))
    		throw new IllegalArgumentException("Condition value must be true or false");
    	boolean conditionValue = getValeur().equals("true");
    	conditionsToChange.put(conditionName, conditionValue);
    }
}









































