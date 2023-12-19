public class Choice{

        private int relatedTo;
        private String text;
        private int nextScene;
    
        public Choice(int relatedTo, String text, int nextScene) {
            this.relatedTo=relatedTo;
            this.text = text;
            this.nextScene = nextScene;
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
    }