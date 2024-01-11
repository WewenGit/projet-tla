import java.util.ArrayList;

public class Personnage
{
	private String name;
	private int currentSceneId;
	private String dialogue;
	private ArrayList<Choice> choices = new ArrayList<>();
	
	public Personnage(String name, int currentSceneId, String dialogue, ArrayList<Choice> choices) {
		this.name = name;
		this.currentSceneId = currentSceneId;
		this.dialogue = dialogue;
		this.choices = choices;
		System.out.println(this);
	}
	
	public ArrayList<Choice> getChoices()
	{
		return choices;
	}
	
	
	
	public void setCurrentSceneId(int currentSceneId) {
		this.currentSceneId = currentSceneId;
	}

	public String getDialogue() {
		return dialogue;
	}

	public int getCurrentSceneId() {
		return currentSceneId;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		String str = "Name: " + name + "\n"
				+ "Start scene: " + currentSceneId + "\n"
				+ "Dialogue: " + dialogue + "\n"
				+ "Choices:";
		for(Choice choice : choices)
			str += "\n	"+choice.toString();
		return str;
	}
}
