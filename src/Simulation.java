import java.util.ArrayList;
import java.util.Scanner;

public class Simulation {

	private TuringMachine m;
	private Configuration currentConfiguration;
	public boolean verbose = false;

	class Configuration {
		ArrayList<Symbol> tape;
		int head;
		State state;

		/**
		 * create empty tape, initialize head and state
		 * 
		 * @param tape
		 */
		Configuration(ArrayList<Symbol> input) {
			this.tape = input;
			if ((tape == null) || (tape.size() == 0))
				tape.add(Symbol.blank);
			this.head = 0;
			this.state = m.getInitialState();
		}

		 void step() {
			Transition t = m.getTransition(state, tape.get(head));
			state = t.getNextState();
			tape.set(head, t.getNextSymbol());
			switch (t.getMove()) {
				case RIGHT:
					head = this.head + 1;
					if (tape.size() == head)
						tape.add(Symbol.blank);
					break;
				case LEFT:
					if (head > 0) head--;
					break;
				default:
					throw new RuntimeException("Unrecognized move direction in transition" + System.lineSeparator() + t);
			}	
		}
		 
		public String toString() {
			StringBuffer buf = new StringBuffer();
			buf.append(state);
			buf.append("\t");
			for (int i = 0; i < tape.size(); i++) {
				Symbol sym = tape.get(i);
				if (i == head)
					buf.append("[");
				else
					buf.append("(");
				buf.append(sym);
				if (i == head)
					buf.append("]");
				else
					buf.append(")");
				if (sym.equals(Symbol.blank))
					break;
			}
			return buf.toString();
		}
		
	}

	
	public Simulation(TuringMachine m, String w) {
		Alphabet inputAlphabet = new Alphabet();
		ArrayList<Symbol> input = new ArrayList<Symbol>();
		for(int i = 0, n = w.length() ; i < n ; i++) { 
			String c = String.valueOf(w.charAt(i));
			input.add(inputAlphabet.get(c)); 
		}
		m.setInputAlphabet(inputAlphabet);
		init(m,input);
	}
	
	public Simulation(TuringMachine m, ArrayList<Symbol> input) {
		this.init(m,input);
	}
	
	private void init(TuringMachine m, ArrayList<Symbol> input) {
		if (!m.checkInput(input))
			throw new RuntimeException("Input contains unknown symbols." + System.lineSeparator() +
					"Input: " + input + System.lineSeparator() +
					"Turing machine: " + System.lineSeparator() + m);
		this.m = m;
		this.currentConfiguration = new Configuration(input);
	}
	
	public void run() {
		while (true) {
			if (verbose)
				System.out.println(currentConfiguration);
			if (isHalting()) break;
			currentConfiguration.step();
		}
		if (verbose)
			System.out.println("Halt.");
	}

	public void simulate() {

		Scanner reader = new Scanner(System.in); 
		while (true) {
			System.out.println(currentConfiguration);		
			if (isHalting()) break;
			
			System.out.println("(type: [n]ext transition, [r]un to completion, [q]uit)?");
			switch (reader.nextLine().charAt(0)) {
			case 'n':
				currentConfiguration.step();
				break;
			case 'r': {
				run();
				return;
			}
			case 'q':
				System.out.println("Quit.");
				return;
			default:
				System.out.println("Unknown command.");
			}
		}
		System.out.println("Halt.");
	}	
	
	boolean isAccepting(){
		return currentConfiguration.state.equals(m.getAcceptingState());
	}
	boolean isRejecting(){
		return currentConfiguration.state.equals(m.getRejectingState());
	}
	boolean isHalting(){
		return this.isAccepting() || this.isRejecting();
	}
	
	
}
