public class Token {

	private TypeDeToken typeDeToken;
	private String valeur;
	private int id;

	public Token(TypeDeToken typeDeToken, String value, int id) {
		this.typeDeToken=typeDeToken;
		this.valeur=value;
		this.id=id;
	}

	public Token(TypeDeToken typeDeToken) {
		this.typeDeToken=typeDeToken;
		this.valeur=null;
	}

	public TypeDeToken getTypeDeToken() {
		return this.typeDeToken;
	}

	public String getValeur() {
		return this.valeur;
	}

	public int getId() {
		return this.id;
	}

	public String toString() {
		return "["+this.typeDeToken+"]" + " " + this.valeur;
	}

}
