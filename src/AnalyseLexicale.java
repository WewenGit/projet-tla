import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalyseLexicale {

	private static final int ETAT_INITIAL = 0;
	private String entree;
	private int pos=0;

	/*
	Table de transition de l'analyse lexicale
	 */
	private static Integer TRANSITIONS[][] = {
		//       espace|lettre|  ¤|chiffre|   *|   /|   $
		/*  0*/ {     0,     0,  0,      0,   1,   3,   6},
		/*  1*/ {     0,     0,  0,      2,   0,   0,   0},
		/*  2*/ {   101,   101,101,      2, 101, 101, 101},
		/*  3*/ {     0,     0,  0,      0,   0,   4,   0},
		/*  4*/ {     0,     0,  0,      5,   0,   0,   0},
		/*  5*/ {   102,   102,102,      5, 102, 102, 102},
		/*  6*/ {     0,     0,  0,      7,   0,   0,   0},
		/*  7*/ {   103,   103,103,      7, 103, 103, 103}};
		//état > 100 = états d'acceptation
		//espace inclut également les retours à la ligne
		//¤ = , . ( ) ' + = [ ] { } # ~ - @ ! : ; ? °
		//101 scene + rollback
		//102 choix + rollback
		//103   fin + rollback
	
	private static ArrayList<Character> Symbols = new ArrayList<Character>(Arrays.asList(
		',', '.', '(', ')', '\'', '+', '=',
		'[', ']', '{', '}', '#', '~', '-',
		'@', '!', ':', ';', '?', '°'
	));

	public int indiceSymbole(Character c) throws IllegalCharacterException{
		if (c==null) return 0;
		if (Symbols.contains(c)) return 2;
		else {
			switch (c) {
				case '*': return 4;
				case '/': return 5;
				case '$': return 6;
			
				default:
					if (Character.isWhitespace(c)) return 0;
					if (Character.isLetter(c)) return 1;
					if (Character.isDigit(c)) return 3;
					throw new IllegalCharacterException();
			}
		}
	}


	public Character lireCaractere(){
		try {
			Character ret = this.entree.charAt(pos);
			this.pos++;
			return ret;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void retourArriere(){
		this.pos--;
	}

	public List<Token> analyse(String entree) throws IllegalCharacterException {
		this.entree=entree;
		this.pos=0;

		Character c;
		int i;
		ArrayList<Token> al = new ArrayList<>();
		int prochainEtat;
		String value="";

		int etat = ETAT_INITIAL;
		do {
			c=this.lireCaractere();
			i=this.indiceSymbole(c);
			prochainEtat=TRANSITIONS[etat][i];

			if (prochainEtat >= 100) {
				if (prochainEtat == 101) {
					al.add(new Token(TypeDeToken.scene, value));
				} else if (prochainEtat == 102) {
					al.add(new Token(TypeDeToken.choice, value));
				} else if (prochainEtat == 103) {
					al.add(new Token(TypeDeToken.end, value));
				}
				etat = 0;
				value = "";
			} else {
				etat = prochainEtat;
				if (etat>0) value += c;
			}

		} while (c!=null);

		return al;
	}

}
