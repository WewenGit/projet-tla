import java.util.HashMap;
import java.util.Map;

public class Choice{

        private int relatedTo;
        private String text;
        private int nextScene;
        private Map<String,Boolean> conditionNames=new HashMap<>();

        public Choice(int relatedTo, String text, int nextScene) {
            this.relatedTo=relatedTo;
            this.text = text;
            this.nextScene = nextScene;
        }

        public Choice(int relatedTo, String text, int nextScene, Map<String,Boolean> conditionNames) {
            this.relatedTo=relatedTo;
            this.text = text;
            this.nextScene = nextScene;
            this.conditionNames = conditionNames;
        }
    
        public String getText() {
            return text;
        }
    
        public int getNextScene() {
            return nextScene;
        }

        public int getRelation() {
            return relatedTo;
        }

        public Map<String,Boolean> getConditionNames() {
            return conditionNames;
        }
    }