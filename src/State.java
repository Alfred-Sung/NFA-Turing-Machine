import Exception.TuringException;

import java.util.HashSet;
import java.util.List;

/**
 * Class that holds a list of transitions
 */

public class State {
    private int index;
    private HashSet<Transition> transitions = new HashSet<>();

    /***
     * Constructs a new State
     * @param index Index of where the state is stored in an array
     */
    public State(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * @param newTransition New instruction object to add
     * @return Returns true if there are no same transitions; return false otherwise
     */
    public boolean AddInstruction(Transition newTransition) {
        //HashSet will use the Transition equals() and hashcode() methods
        if (transitions.contains(newTransition)) return false;
        transitions.add(newTransition);
        return true;
    }

    /**
     * Check which or whether an instruction can process an incoming Tape object
     *
     * @return The Transition object that can process the tape based on the read character
     * @throws TuringException Throws an exception indicating that no transitions can process the Tape
     */
    public List<Transition> Find(Tape[] in, List<Transition> possibleTransitions) {
        possibleTransitions.clear();

        for (Transition transition : transitions)
            if (transition.Check(in)) possibleTransitions.add(transition);

        return possibleTransitions;
    }
}
