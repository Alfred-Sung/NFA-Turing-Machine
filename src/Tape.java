public class Tape implements Cloneable {
    private StringBuilder sb;
    private int index;

    public Tape(String value) {
        sb = new StringBuilder(value);
        setIndex(0);
    }

    public char charAt(int i) {
        return sb.charAt(i);
    }

    public void setCharAt(int i, char c) {
        sb.setCharAt(i, c);
    }

    public int getIndex() {
        return index;
    }

    //TODO for loop append/insert
    public void setIndex(int index) {
        if (sb.length() - 1 < index) {
            sb.append('B');
            this.index = index;
        } else if (index == 0 && sb.charAt(0) != 'B') {
            sb.insert(0, 'B');
            this.index = 1;
        } else if (index < 0) {
            sb.insert(0, 'B');
            this.index = 0;
        } else {
            this.index = index;
        }
    }

    @Override
    public Tape clone() {
        Tape other = new Tape(sb.toString());
        other.index = index;
        return other;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public StringBuilder getStringBuilder() {
        return sb;
    }
}
