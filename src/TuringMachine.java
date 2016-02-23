import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TuringMachine {
	
	private Set<State> states = new HashSet<State>(); 	// states
	private Set<Symbol> sigma = new HashSet<Symbol>();	// input alphabet
	private Set<Symbol> gamma = new HashSet<Symbol>();	// tape alphabet
	
	private TransitionFunction delta = new TransitionFunction();	// transition function
	
	private State q0;			// initial state
	private State qa;			// accepting state
	private State qr;			// rejecting state

	/**
	 * Default input vocabulary is empty.
	 * Initial, accepting, and rejecting states use the default names.
	 * Tape alphabet and other states are implicit and inferred from the transitions that are added later. 
	 * @param sigma input alphabet
	 */
	public TuringMachine() {
		this.gamma.add(Symbol.blank);
		
		states.add(State.q0); this.q0 = State.q0; 
		states.add(State.qa); this.qa = State.qa;
		states.add(State.qr); this.qr = State.qr;
	}
	
	/**
	 * Copies all elements of sigma to the input alphabet. 
	 * Tape alphabet implicitly contains everything in input alphabet
	 * @param sigma
	 */
	public void setInputAlphabet(Alphabet sigma) {
		this.sigma.addAll(sigma.getAll());
		this.gamma.addAll(this.sigma);
	}
	
	/**
	 * Adds current and next states and symbols to the machine definition, then adds the transition.
	 * @param t
	 */
	public void add(Transition t){
		states.add(t.getCurState());
		states.add(t.getNextState());
		gamma.add(t.getCurSymbol());
		gamma.add(t.getNextSymbol());
		
		delta.add(t);
	}
	
	public boolean checkInput(List<Symbol> input) {
		return sigma.containsAll(input);
	}
	
	public String toString() {
		String sep = System.lineSeparator();
		StringBuffer buf = new StringBuffer();
		buf.append("Input alphabet:");
		buf.append(sep);
		for (Symbol symbol : sigma) {
			buf.append(symbol);
			buf.append(", ");
		}
		buf.append(sep);
		buf.append("Tape alphabet:");
		buf.append(sep);
		for (Symbol symbol : gamma) {
			buf.append(symbol);
			buf.append(", ");
		}
		buf.append(sep);
		
		buf.append("States:");
		buf.append(sep);
		for (State state : states) {
			buf.append(state);
			buf.append(", ");
		}
		buf.append(sep);
		
		buf.append("Initial state:");
		buf.append(q0);
		buf.append(sep);
		buf.append("Accepting state:");
		buf.append(qa);
		buf.append(sep);
		buf.append("Rejecting state:");
		buf.append(qr);
		buf.append(sep);
		
		buf.append("Transition function:");
		buf.append(sep);
		buf.append(delta.toString());
		buf.append(sep);
		
		return buf.toString();
	}
	
	public State getInitialState() {
		return q0;
	}
	public State getAcceptingState() {
		return qa;
	}
	public State getRejectingState() {
		return qr;
	}

	/**
	 * Implicitly, undefined transitions are transition to the rejecting state without modifying the tape symbol or position,
	 * and moving to the RIGHT
	 * @param state
	 * @param symbol
	 * @return
	 */
	public Transition getTransition(State state, Symbol symbol) {
		Transition t = delta.get(state,symbol);
		if (t == null)
			t = new Transition(state, symbol, this.qr, symbol, Transition.Direction.RIGHT);
		return t;
	}
}