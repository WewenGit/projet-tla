public class Token {

	private TypeDeToken typeDeToken;
	private String valeur;
	private int number;

	public Token(TypeDeToken typeDeToken, String value, int number) {
		this.typeDeToken=typeDeToken;
		this.valeur=value;
		this.number=number;
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

	public int getNumber() {
		return this.number;
	}

	public String toString(){
		return "Type de token : "+this.typeDeToken+",  number : "+this.number;
	}

}
