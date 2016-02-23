public class Symbol {
	
	private String name;
	
	public static final Symbol blank = new Symbol("blank");	
	
	public Symbol(String name) {
		this.name = name;
	}
	
	public String name(){
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
