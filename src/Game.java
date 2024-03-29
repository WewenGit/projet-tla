import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Scene> scenes;
    private Map<String,Boolean> conditions;
    private ArrayList<Personnage> personnages;

    public Game(List<Scene> scenes, Map<String,Boolean> conditions, ArrayList<Personnage> personnages){
        this.scenes = scenes;
        this.conditions=conditions;
        this.personnages = personnages;
    }

    public List<Scene> getScenes(){
        return this.scenes;
    }
    public Map<String,Boolean> getConditions(){
        return this.conditions;
    }
    public ArrayList<Personnage> getPersonnages() {
		return personnages;
	}

	public String toString(){
        String s="";
        for (Scene scene : scenes) {
            s+="Id : "+scene.getId()+", text : "+scene.getText()+", is the end : "+scene.isEnd();
            s+="\n";
            s+="\n";
        }
        for (Map.Entry<String, Boolean> entry : conditions.entrySet()) {
            String cle = entry.getKey();
            Boolean valeur = entry.getValue();
            s+="Condition : " + cle + ", Valeur : " + valeur;
            s+="\n";
        }
        return s;
    }

    public void setCondition(String condition, Boolean newBool){
        conditions.put(condition, newBool);
    }
}
