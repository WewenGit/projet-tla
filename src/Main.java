import java.awt.event.*;
import java.util.List;

import javax.swing.JFrame;

public class Main extends JFrame {

	public Main() {
		super("Project TLA");

		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
			   System.exit(0);
			}
		 };
		 
		addWindowListener(l);
		setSize(500,500);
		setVisible(true);
	 }

	public static void main(String[] args) throws IllegalCharacterException {
		AnalyseLexicale al = new AnalyseLexicale();
		List<Token> lt = al.analyse("*0 Carotte# //1 Choix# $1 Fin#");
		for (Token token : lt) {
			System.out.println(token);
		}
		//TODO recup le string correspondant au texte
		run(lt);
	}

	private static void run(List<Token> lt) {
		AnalyseSyntaxique ansyn = new AnalyseSyntaxique();
		new Main();
		Game game = ansyn.analyse(lt);
		//TODO gérer le jeu et l'interface graphique
	}

}