import java.util.ArrayList;

public class Driver {
	public static void test1() {

		Simulation sim;
		ArrayList<Symbol> input;
		TuringMachine m;
		
		Alphabet inputAlphabet = new Alphabet();
		Symbol sharp = inputAlphabet.get("#");
		inputAlphabet.get("a");
		inputAlphabet.get("b");
		inputAlphabet.get("c");
		
		m = new TuringMachine();
		m.setInputAlphabet(inputAlphabet);
		
		/*
		 * Check that the input contains sharp # symbol.
		 */
		
		for (Symbol sym : inputAlphabet.getAll()) {
			if (sym.equals(sharp))
				m.add(new Transition(State.q0, sym, State.qa, sym, Transition.Direction.RIGHT));
			else
				m.add(new Transition(State.q0, sym, State.q0, sym, Transition.Direction.RIGHT));
		}
		
		/* expected: accept */
		input = new ArrayList<Symbol>();
		input.add(inputAlphabet.get("a"));
		input.add(inputAlphabet.get("b"));
		input.add(inputAlphabet.get("#"));
		input.add(inputAlphabet.get("a"));
		input.add(inputAlphabet.get("b"));		
		
		sim = new Simulation(m, input);
		sim.verbose = true;
		System.out.println(m);
		sim.run();
		
		assert (sim.isAccepting());
		
		/* expected: reject */
		input = new ArrayList<Symbol>();
		input.add(inputAlphabet.get("a"));
		input.add(inputAlphabet.get("b"));
		input.add(inputAlphabet.get("b"));
		input.add(inputAlphabet.get("a"));
		input.add(inputAlphabet.get("b"));		
		
		sim = new Simulation(m, input);
		sim.verbose = true;
		System.out.println(m);
		sim.run();
		
		assert (sim.isRejecting());
		
	}

	private static void test2() {
		
		Simulation sim;
		TuringMachine m;
		
		/*
		 * Check that the input is empty.
		 */
		Transition t = new Transition(State.q0, Symbol.blank, State.qa, Symbol.blank, Transition.Direction.RIGHT);
		
		/* expected: accept */
		m = new TuringMachine();
		m.add(t);
		sim = new Simulation(m, "");
		System.out.println(m);
		
		sim.verbose = true;
		sim.run();	
		assert (sim.isAccepting());

		/* expected: accept */
		Alphabet inputAlphabet = new Alphabet();
		inputAlphabet.get("a");
		inputAlphabet.get("b");
		inputAlphabet.get("c");
		m = new TuringMachine();
		m.add(t);
		m.setInputAlphabet(inputAlphabet);
		sim = new Simulation(m, "");
		System.out.println(m);
		
		sim.verbose = true;
		sim.run();	
		assert (sim.isAccepting());
	
		/* expected: reject */
		m = new TuringMachine();
		m.add(t);
		sim = new Simulation(m, "abcabc");
		System.out.println(m);
		sim.verbose = true;
		sim.run();
			
		assert (sim.isRejecting());		
	}

	
	private static void test3() {
		Simulation sim;
		TuringMachine m;
				
		Alphabet inputAlphabet = new Alphabet();
		Symbol a = inputAlphabet.get("a");
		Symbol b = inputAlphabet.get("b");
		Symbol c = inputAlphabet.get("c");
		m = new TuringMachine();
		m.setInputAlphabet(inputAlphabet);
		
		/*
		 * Check that the input is not empty.
		 */
		m.add(new Transition(State.q0, Symbol.blank, State.qr, Symbol.blank, Transition.Direction.RIGHT));
		m.add(new Transition(State.q0, a, State.qa, a, Transition.Direction.RIGHT));
		m.add(new Transition(State.q0, b, State.qa, b, Transition.Direction.RIGHT));
		m.add(new Transition(State.q0, c, State.qa, c, Transition.Direction.RIGHT));
		
		/* expected: reject */
		sim = new Simulation(m, new ArrayList<Symbol>());
		System.out.println(m);
		
		sim.verbose = true;
		sim.simulate();	
		
		assert (sim.isRejecting());

		/* expected: accept */
		ArrayList<Symbol> input = new ArrayList<Symbol>();
		input.add(a);
		input.add(b);
		sim = new Simulation(m, input);
		System.out.println(m);
		
		sim.verbose = true;
		sim.simulate();	
		
		assert (sim.isAccepting());

	}

	
	public static void main(String[] args) {
		test1();
		//test2();
		//test3();
	}
}
