
public class State {
	
	private String name;
	
	public static final State q0 = new State("q0"); // initial state
	public static final State qa = new State("qa"); // accepting state
	public static final State qr = new State("qr"); // rejecting state
	
	public State(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
