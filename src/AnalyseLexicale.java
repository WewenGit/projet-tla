import java.util.ArrayList;
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
		/*  0*/ {     0,     1,  1,      1,   2,   4,   7},
		/*  1*/ {   101,     1,101,      1, 101, 101, 101},
		/*  2*/ {   101,   101,101,      3, 101, 101, 101},
		/*  3*/ {   102,   102,102,      3, 102, 102, 102},
		/*  4*/ {   101,   101,101,    101, 101,   5, 101},
		/*  5*/ {   101,   101,101,      6, 101, 101, 101},
		/*  6*/ {   103,   103,103,      6, 103, 103, 103},
		/*  7*/ {   101,   101,101,      8, 101, 101, 101},
		/*  8*/ {   104,   104,104,      8, 104, 104, 104}};
		//état > 100 = états d'acceptation
		//espace inclut également les retours à la ligne
		//¤ = , . ( ) ' + = [ ] { } # ~ - @ ! : ; ? °
		//101   mot + rollback
		//102 scene + rollback
		//103 choix + rollback
		//104   fin + rollback

	public int indiceSymbole(Character c) throws IllegalCharacterException{
		if (c==null) return 0;

		switch (c) {
			case '+': return 1;
			case '*': return 2;
			case '(': return 3;
			case ')': return 4;
			case ',': return 5;
			case '=': return 6;
			case '!': return 7;
		
			default:
				if(Character.isWhitespace(c)) return 0;
				if (Character.isLetter(c)) return 9;
				if (Character.isDigit(c)) return 8;
				throw new IllegalCharacterException();
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

			switch (prochainEtat) {
				case 3:
					value+=c.toString();
					etat=prochainEtat;
					break;
				case 4:
					value+=c.toString();
					etat=prochainEtat;
					break;
				case 101:
					al.add(new Token(TypeDeToken.add, "+"));
					etat=ETAT_INITIAL;
					break;
				case 102:
					al.add(new Token(TypeDeToken.multiply, "*"));
					etat=ETAT_INITIAL;
					break;
				case 103:
					al.add(new Token(TypeDeToken.leftPar, "("));
					etat=ETAT_INITIAL;
					break;
				case 104:
					al.add(new Token(TypeDeToken.rightPar, ")"));
					etat=ETAT_INITIAL;
					break;
				case 105:
					al.add(new Token(TypeDeToken.comma, ","));
					etat=ETAT_INITIAL;
					break;
				case 106:
					al.add(new Token(TypeDeToken.assign, "="));
					this.retourArriere();
					etat=ETAT_INITIAL;
					break;
				case 107:
					al.add(new Token(TypeDeToken.equal, "=="));
					etat=ETAT_INITIAL;
					break;
				case 108:
					al.add(new Token(TypeDeToken.logicalNeg, "!"));
					this.retourArriere();
					etat=ETAT_INITIAL;
					break;
				case 109:
					al.add(new Token(TypeDeToken.notEqual, "!="));
					etat=ETAT_INITIAL;
					break;
				case 110:
					al.add(new Token(TypeDeToken.intVal,value));
					value="";
					this.retourArriere();
					etat=ETAT_INITIAL;
					break;
				case 111:
					al.add(new Token(TypeDeToken.ident,value));
					value="";
					this.retourArriere();
					etat=ETAT_INITIAL;
					break;
			
				default:
					etat=prochainEtat;
					break;
			}

		} while (c!=null);

		return al;
	}

}
