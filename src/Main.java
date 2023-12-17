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
	

	public static void main(String[] args) {
		JFrame window = new Main();
	}

	/*
	effectue l'analyse lexicale de la chaine entree,
	affiche la liste des tokens reconnus
	 */
	private static void testAnalyseLexicale(String entree) {
		System.out.println("test de l'analyse lexicale sur l'entrée " + entree);
		AnalyseLexicale anlex = new AnalyseLexicale();
		try {
			List<Token> lt = anlex.analyse(entree);
			for (Token token : lt) {
				System.out.println(token.toString());
			}
			testAnalyseSyntaxique(lt);
		} catch (IllegalCharacterException e) {
			e.printStackTrace();
		}
	}

	private static void testAnalyseSyntaxique(List<Token> lt) {
		AnalyseSyntaxique ansyn = new AnalyseSyntaxique();
		System.out.println(ansyn.analyse(lt));
	}

}
