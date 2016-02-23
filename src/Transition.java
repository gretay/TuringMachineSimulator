
public class Transition {
	public enum Direction { RIGHT, LEFT	}; 
	
	private State curState;
	private Symbol curSymbol;
	private State nextState;
	private Symbol nextSymbol;
	private Direction move;
	
	public Transition(State curState, Symbol curSymbol, State nextState, Symbol nextSymbol, Direction move) {
		this.curState = curState;
		this.curSymbol = curSymbol;
		this.nextState = nextState;
		this.nextSymbol = nextSymbol;
		this.move = move;
	}

	@Override
	public String toString() {
		return curState + ", " + curSymbol + " -> " + nextState +", " + nextSymbol + ", " + move;
	}

	public State getCurState() {
		return curState;
	}

	public Symbol getCurSymbol() {
		return curSymbol;
	}

	public State getNextState() {
		return nextState;
	}

	public Symbol getNextSymbol() {
		return nextSymbol;
	}

	public Direction getMove() {
		return move;
	}
}
