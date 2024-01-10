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
    private ArrayList<Personnage> personnages;
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
        this.personnages = new ArrayList<Personnage>();
        S();
        this.game = new Game(scenes,conditions, personnages);
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
                nextToken();
                P();
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
            nextToken();
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
        HashMap<String, Integer> personnagesToMove = new HashMap<String, Integer>();
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
        nextToken();
        while(getTypeDeToken().equals(TypeDeToken.text) ||
        	(getTypeDeToken().equals(TypeDeToken.keyWord) && (getValeur().equals("Condition") || getValeur().equals("Personnage"))))
        {
			if(getTypeDeToken().equals(TypeDeToken.text))
			{
                sceneText+=getValeur()+"\n";
				nextToken();
			}
			else if(getValeur().equals("Condition"))
				CTC(conditionsToChange);
			else PTM(personnagesToMove);
        }
        if (!isFinal) {
            choices = CH();
        }
        Scene sc = new Scene(sceneNumber, sceneText, choices, conditionsToChange, personnages, personnagesToMove, isFinal);
        scenes.add(sc);
        if (!getTypeDeToken().equals(TypeDeToken.sectionEnd)) {
            throw new IllegalArgumentException("Missing section end");
        }
        nextToken();
        S();
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
    	if(!(getTypeDeToken().equals(TypeDeToken.text)))
    		throw new IllegalArgumentException("Text was expected after keyWord 'Condition'.");
    	String conditionName = getValeur();
    	if(!conditions.containsKey(conditionName))
    		conditions.put(conditionName, false);
    	nextToken();
    	if(!(getTypeDeToken().equals(TypeDeToken.keyWord) && Arrays.asList("false", "true").contains(getValeur())))
    		throw new IllegalArgumentException("Condition value must be true or false");
    	boolean conditionValue = getValeur().equals("true");
    	conditionsToChange.put(conditionName, conditionValue);
        nextToken();
    }
    
    
    
    
    //create a new character ("personnage")
    public void P()
    {
    	if(!getTypeDeToken().equals(TypeDeToken.text))
    		throw new IllegalArgumentException("Text expected for the name of the character.");
    	String name = getValeur();
    	nextToken();
    	
    	if(!getTypeDeToken().equals(TypeDeToken.number))
    		throw new IllegalArgumentException("Number expected for the start scene of the character: '"+name+"'.");
    	int sceneId = getNumber();
    	nextToken();
    	
    	if(!getTypeDeToken().equals(TypeDeToken.sectionStart))
    		throw new IllegalArgumentException("{ expected.");
    	nextToken();
    	
    	if(!getTypeDeToken().equals(TypeDeToken.text))
    		throw new IllegalArgumentException("Text expected for character dialogue.");
    	String text = getValeur();
    	nextToken();
    	
    	personnages.add(new Personnage(name, sceneId, text, CH()));
    	
    	if(!getTypeDeToken().equals(TypeDeToken.sectionEnd))
    		throw new IllegalArgumentException("} expected.");
    	nextToken();
    	S();
    }

    
    
    
    //update personnages to move
    private void PTM(HashMap<String, Integer> personnagesToMove) {
    	nextToken();
    	if(!(getTypeDeToken().equals(TypeDeToken.text)))
    		throw new IllegalArgumentException("Text was expected after keyWord 'Personnage'.");
    	String persoName = getValeur();
    	nextToken();
    	if(!(getTypeDeToken().equals(TypeDeToken.keyWord) && getValeur().equals("go")))
    		throw new IllegalArgumentException("keyWord 'go' was expected after personange name.");
    	nextToken();
    	if(!getTypeDeToken().equals(TypeDeToken.number))
    		throw new IllegalArgumentException("Number expected as personnage new scene.");
    	Integer newSceneId = getNumber();
    	personnagesToMove.put(persoName, newSceneId);
        nextToken();
	}
}









































