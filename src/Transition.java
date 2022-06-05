import java.util.Objects;

public class Transition {
    public enum Direction {
        //The tape pointer movement is dictated by the enum value specified
        //Able to move left or right by one or stay still by adding the getValue() of the enum variable
        Left(-1), Right(1), Stay(0);

        final int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private State to;

    private char[] read;
    private char[] write;
    private Direction[] dir;

    /**
     * Constructs a new Transition object
     *
     * @param read  Character
     * @param write
     * @param dir
     * @param to    Nest ste the TM will transition to
     */
    public Transition(char[] read, char[] write, char[] dir, State to) {
        this.read = read;
        this.write = write;
        this.dir = new Direction[dir.length];

        for (int i = 0; i < dir.length; i++) {
            Direction direction;
            switch (Character.toUpperCase(dir[i])) {
                case 'L':
                    direction = Direction.Left;
                    break;
                case 'R':
                    direction = Direction.Right;
                    break;
                default:
                    direction = Direction.Stay;
            }
            this.dir[i] = direction;
        }

        this.to = to;
    }

    public boolean Check(Tape[] in) {
        for (int i = 0; i < in.length; i++)
            if (in[i].charAt(in[i].getIndex()) != read[i]) return false;

        return true;
    }

    public void Run(Tape[] in) {
        for (int i = 0; i < in.length; i++) {
            in[i].setCharAt(in[i].getIndex(), write[i]);
            in[i].setIndex(in[i].getIndex() + this.dir[i].getValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return read == that.read && write == that.write && to.equals(that.to) && dir == that.dir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, dir, read, write);
    }

    public State getToState() {
        return to;
    }
}


