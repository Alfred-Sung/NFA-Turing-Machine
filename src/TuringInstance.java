import java.util.Arrays;
import java.util.List;

public class TuringInstance implements Cloneable {
    private boolean halted = false;
    private Tape[] tape;
    private State currentState;
    private Transition currentTransition;

    public TuringInstance(Tape[] tape, State currentState) {
        this.tape = tape;
        this.currentState = currentState;
    }

    public List<Transition> Find(List<Transition> possibleTransitions) {
        return currentState.Find(tape, possibleTransitions);
    }

    public void Run() {
        if (halted) return;

        currentTransition.Run(tape);
        currentState = currentTransition.getToState();
    }

    public boolean isHalted() {
        return halted;
    }

    public void setHalted(boolean halted) {
        this.halted = halted;
    }

    public String getTapeStringAt(int index) {
        return tape[index].toString();
    }

    public StringBuilder getTapeStringBuilderAt(int index) {
        return tape[index].getStringBuilder();
    }

    public int getTapeIndexAt(int index) {
        return tape[index].getIndex();
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentTransition(Transition currentTransition) {
        this.currentTransition = currentTransition;
    }

    @Override
    public TuringInstance clone() {
        return new TuringInstance(Arrays.stream(tape).map(Tape::clone).toArray(Tape[]::new), currentState);
    }
}
