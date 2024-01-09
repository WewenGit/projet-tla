import java.util.ArrayList;
import java.util.List;

public class AnalyseLexicale {

	private static final int ETAT_INITIAL = 0;
	private String entree;
	private int pos = 0;

	/*
	Table de transition de l'analyse lexicale
	 */
	private static Integer TRANSITIONS[][] = {
			//	   espace|lettre|chiffre|  "|  {|  }|  -|  (|  )|autre
			/*0*/ {     0,     1,      2,  3,104,105,106,107,108,    0},
			/*1*/ {   101,     1,      1,101,101,101,101,101,101,  101},
			/*2*/ {   102,   102,      2,102,102,102,102,102,102,  102},
			/*3*/ {     3,     3,      3,103,  3,  3,  3,  3,  3,    3}
			};

			// espace inclut egalement les retours a la ligne
			// autre = tout autre caractere
			// etat > 100 = etats d'acceptation
			// 101 MotClef + rollback
			// 102 Nombre + rollback
			// 103 Texte
			// 104 DebutSection
			// 105 FinSection
			// 106 Choix
			// 107 DebutCondition
			// 108 FinCondition
			// NB: penser à supprimer le 1ere caractere des textes qui sera inévitablement '"'
			// NB: quand on est dans l'état 0, il ne faut pas enregistrer les caractères et
			// a plus forte raison il faut vider le buffer s'il n'est pas deja vide.
			// Dis autrement, tout ce qui est en etat 0 doit etre simplement ignore.

	public int indiceSymbole(Character c) {
		if (c == null) return 0;
		else {
			switch (c) {
				case '"': return 3;
				case '{': return 4;
				case '}': return 5;
				case '-': return 6;
				case '(': return 7;
				case ')': return 8;
			
				default:
					if (Character.isWhitespace(c)) return 0;
					else if (Character.isLetter(c)) return 1;
					else if (Character.isDigit(c)) return 2;
					else return 9;
			}
		}
	}

	public Character lireCaractere() {
		try {
			Character ret = this.entree.charAt(pos);
			this.pos++;
			return ret;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
 
	public List<Token> analyse(String entree) {
		this.entree = entree;
		this.pos = 0;

		Character c;
		int i;
		ArrayList<Token> al = new ArrayList<>();
		int prochainEtat;
		String value = "";

		int etat = ETAT_INITIAL;
		do {
			c = this.lireCaractere();
			i = this.indiceSymbole(c);
			prochainEtat = TRANSITIONS[etat][i];

			if (prochainEtat >= 100) {
				if (prochainEtat == 102) al.add(new Token(TypeDeToken.number, "", Integer.parseInt(value)));
				else if (prochainEtat == 103) al.add(new Token(TypeDeToken.text, value.substring(1), 0));
				else al.add(new Token(TypeDeToken.values()[prochainEtat - 101], value, 0));
				
				if (prochainEtat >= 101 && prochainEtat <= 102) pos -= 1; // retour arrière (rollback)
				
				etat = 0;
				value = "";
			}
			else {
				if (prochainEtat > 0) value += c;
				etat = prochainEtat;
			}

		} while (c != null);

		return al;
	}

}
