import java.util.ArrayList;

public class Scene {
    private String text;
    private boolean isEnd;
    private int id;
    private ArrayList<Choice> choices = new ArrayList<>();
    
    public Scene(int id, String text, ArrayList<Choice> choices, boolean isEnd) {
        this.id=id;
        this.text = text;
        this.choices = choices;
        this.isEnd = isEnd;
    }
    
    public ArrayList<Choice> getChoices()
    {
    	return choices;
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
