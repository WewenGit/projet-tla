import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Choice{

        private String text;
        private int nextScene;
        private ArrayList<String> conditionTokens=null;

        public Choice(String text, int nextScene) {
            this.text = text;
            this.nextScene = nextScene;
        }

        public Choice(String text, int nextScene, ArrayList<String> conditionTokens) {
            this.text = text;
            this.nextScene = nextScene;
            this.conditionTokens = conditionTokens;
        }
        
        public boolean conditionsAreTrue(Map<String,Boolean> conditions)
        {
        	boolean condition = true;
        	boolean not = false, or = false, and = false;
        	if(conditionTokens == null)
        		return true;
        	for(String keyWord : conditionTokens)
        	{
        		
        		if(Arrays.asList("and","or","not").contains(keyWord)) {
            		if(keyWord.equals("not"))
            			not = true;
            		else if(keyWord.equals("or"))
            			or = true;
            		else if(keyWord.equals("and"))
            			and = true;
            	}
        		else
        		{
        			boolean curCondition = conditions.containsKey(keyWord)? conditions.get(keyWord) : false;
        			if(or) condition |= not?!curCondition: curCondition;
        			else if(and) condition &= not?!curCondition: curCondition;
        			else condition = curCondition;
        			not = or = and = false;
        		}
        	}
        	return condition;
        }
    
        public String getText() {
            return text;
        }
    
        public int getNextScene() {
            return nextScene;
        }
        
        @Override
        public String toString() {
        	return "Destination: "+ nextScene + " Text: " + text;
        }
    }