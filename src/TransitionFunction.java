import java.util.HashMap;
import java.util.Map;

public class TransitionFunction {
	private Map<State, Map<Symbol,Transition>> map = new HashMap<State, Map<Symbol,Transition>>();

	/**
	 * Fails if another transition with the same current state and symbol exists.
	 * @param t
	 */
	public void add(Transition t){
		Map<Symbol,Transition> cur = map.get(t.getCurState());
		if (cur == null) {
			cur = new HashMap<Symbol,Transition>();
		}
		if (cur.containsKey(t.getCurSymbol())) {
			throw new RuntimeException("Transition already exists." + System.lineSeparator() + t);
		}
		cur.put(t.getCurSymbol(),t);
		map.put(t.getCurState(), cur);
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (Map<Symbol,Transition> cur : map.values()) {
			for (Transition t : cur.values()) {
				buf.append(t.toString());
				buf.append(System.lineSeparator());
			}	
		} 
		return buf.toString();
	}

	public Transition get(State state, Symbol symbol) {
		if (!map.containsKey(state)) return null;
		return map.get(state).get(symbol);
	}
	
}
