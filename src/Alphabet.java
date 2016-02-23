import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * guarantees no duplicates (effectively implements hash consing)
 * @author gretay
 *
 */
public class Alphabet {
	private HashMap<String, Symbol> symbols = new HashMap<String, Symbol>();

	public Symbol get(String name) {
		if (name.equals(Symbol.blank.name()))
			throw new RuntimeException("Symbol name " + name + " is reserved.");
		
		Symbol sym = symbols.get(name);
		if (sym == null) {
			sym = new Symbol(name);
			symbols.put(name, sym);
		}
		return sym;
	}

	/**
	 * @return shallow copy 
	 */
	public Set<Symbol> getAll() {
		Set<Symbol> tmp = new HashSet<Symbol>();
		tmp.addAll(symbols.values());
		return tmp;		
	}
}