public class Scene {
    private String text;
    private boolean isEnd;
    private int id;
    
    public Scene(int id, String text, boolean isEnd) {
        this.id=id;
        this.text = text;
        this.isEnd = isEnd;
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
