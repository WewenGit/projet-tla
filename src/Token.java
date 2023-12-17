public class Token {

	private TypeDeToken typeDeToken;
	private String valeur;

	public Token(TypeDeToken typeDeToken, String value) {
		this.typeDeToken=typeDeToken;
		this.valeur=value;
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

	public String toString() {
		return "["+this.typeDeToken+"]" + " " + this.valeur;
	}

}
