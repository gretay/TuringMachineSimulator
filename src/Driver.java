import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSeparatorUI;

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

	
	public static void main(String[] args) {
		// test1();
		test2();
	}

}
