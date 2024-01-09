import java.util.List;
import java.util.Map;

public class Game {
    private List<Scene> scenes;
    private List<Choice> choices;
    private Map<String,Boolean> conditions;

    public Game(List<Scene> scenes, List<Choice> choices, Map<String,Boolean> conditions){
        this.scenes = scenes;
        this.choices = choices;
        this.conditions=conditions;
    }

    public List<Scene> getScenes(){
        return this.scenes;
    }
    public List<Choice> getChoices(){
        return this.choices;
    }
    public Map<String,Boolean> getConditions(){
        return this.conditions;
    }

    public String toString(){
        String s="";
        for (Scene scene : scenes) {
            s+="Id : "+scene.getId()+", text : "+scene.getText()+", is the end : "+scene.isEnd();
            s+="\n";
            s+="\n";
        }
        for (Choice choice : choices) {
            s+="Is related to scene : "+choice.getRelation();
            s+=", text : "+choice.getText();
            s+=", next scene : "+choice.getNextScene();
            s+="\n";
            s+="\n";
        }
        return s;
    }
}
