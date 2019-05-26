public class Token {

	private Symbol sym;
	private String id;
	private int num;
	
	public Token(Symbol s, String i, int n) {
		sym = s;
		id = i;
		num = n;
	}
	
	public Symbol getSym() {
		return sym;
	}

	public void setSym(Symbol sym) {
		this.sym = sym;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String toString() {
		String s = "Symbol: " + sym + ", id: " + id + ", num: "+ num;
		return s;
	}
	
	
}
