import java.util.ArrayList;
import java.util.HashMap;

public class Scene {
    private String text;
    private boolean isEnd;
    private int id;
    private ArrayList<Choice> choices = new ArrayList<>();
    private HashMap<String,Boolean> conditionsToChange = new HashMap<String,Boolean>();
    
    public Scene(int id, String text, ArrayList<Choice> choices, HashMap<String,Boolean> conditionsToChange, boolean isEnd) {
        this.id=id;
        this.text = text;
        this.choices = choices;
        this.conditionsToChange = conditionsToChange;
        this.isEnd = isEnd;
    }
    
    public ArrayList<Choice> getChoices() {
    	return choices;
    }
    
    public HashMap<String, Boolean> getConditionsToChange() {
		return conditionsToChange;
	}

	public String getText() {
        return text;
    }
    
    public boolean isEnd() {
        return isEnd;
    }

    public int getId() {
        return id;
    }
}
