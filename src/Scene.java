import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scene {
    private String text;
    private boolean isEnd;
    private int id;
    private ArrayList<Choice> choices = new ArrayList<>();
    private HashMap<String,Boolean> conditionsToChange;
    private ArrayList<Personnage> personnages;
    private HashMap<String,Integer> personnagesToMove;
    
    public Scene(int id, String text,
    		ArrayList<Choice> choices,
    		HashMap<String,Boolean> conditionsToChange,
    		ArrayList<Personnage> personnages,
    		HashMap<String,Integer> personnagesToMove,
    		boolean isEnd) {
        this.id=id;
        this.text = text;
        this.choices = choices;
        this.conditionsToChange = conditionsToChange;
        this.personnages = personnages;
        this.personnagesToMove = personnagesToMove;
        this.isEnd = isEnd;
    }
    
    public ArrayList<Choice> getChoices() {
    	ArrayList<Choice> allChoices = new ArrayList<Choice>();
    	allChoices.addAll(choices);
    	for(Personnage perso : personnages)
    		if(perso.getCurrentSceneId() == id)
    			allChoices.addAll(perso.getChoices());
    	return allChoices;
    }
    
    public HashMap<String, Boolean> getConditionsToChange() {
		return conditionsToChange;
	}

	public String getText() {
		String allText = "";
		allText += text;

    	for(Personnage perso : personnages)
    		if(perso.getCurrentSceneId() == id)
    			allText += "\nIci se trouve "+ perso.getName()+ " qui vous dit:\n" + perso.getDialogue();
        return allText;
    }
    
    public boolean isEnd() {
        return isEnd;
    }

    public int getId() {
        return id;
    }

	public void movePersonnages() {
		for(Map.Entry<String, Integer> entry : personnagesToMove.entrySet())
			for(Personnage perso : personnages)
				if(perso.getName().equals(entry.getKey()))
					perso.setCurrentSceneId(entry.getValue());
		personnagesToMove.clear();
	}
}
