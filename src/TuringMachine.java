import Exception.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class TuringMachine {
    private int line = 7;
    private int initialState = 0;
    private HashSet<Integer> finalStates = new HashSet<>();

    private State[] stateGraph;
    private HashSet<Character> tapeAlphabetSet;

    public TuringMachine(String textFile) throws FileNotFoundException, ParseException {
        File file = new File(textFile);
        Scanner scanner = new Scanner(file);

        //Line 1 should be number of states
        int states = scanner.nextInt();
        scanner.nextLine();
        //Error checking
        if (states < 1)
            throw new ParseException(textFile, 1, "States must be larger than 0");
        // Initialize state array
        stateGraph = new State[states];
        for (int i = 0; i < states; i++)
            stateGraph[i] = new State(i);

        int numberOfTapes = scanner.nextInt();
        scanner.nextLine();
        if (numberOfTapes < 1)
            throw new ParseException(textFile, 1, "Tape length must be larger than 0");

        //Next lines should be a series of characters specifying valid alphabet characters
        scanner.nextLine();
        tapeAlphabetSet = stringToCharacterSet(scanner.nextLine());

        //Line 4 should be initial state between 1 - states
        initialState = scanner.nextInt();
        scanner.nextLine();
        //Error checking
        if (initialState - 1 < 0 || initialState - 1 > states)
            throw new ParseException(textFile, 4, initialState + " out of bounds");
        initialState--;

        //Line 5 should be final state between 1 - states
        String[] finalStateSplitString = scanner.nextLine().split(" ");
        for (String finalStateString : finalStateSplitString) {
            int finalState = Integer.parseInt(finalStateString.trim());

            //Error checking
            if (finalState - 1 < 0 || finalState - 1 > states)
                throw new ParseException(textFile, 5, finalState + " out of bounds");

            finalStates.add(finalState - 1);
        }

        char[] read = new char[numberOfTapes];
        char[] write = new char[numberOfTapes];
        char[] dir = new char[numberOfTapes];

        //Rest of the file are transitions for state transitions in the form of:
        //CurrentState -> NextState : ReadLetter WrittenLetter Direction | ReadLetter WrittenLetter Direction | ReadLetter WrittenLetter Direction...
        while (scanner.hasNext()) {
            String[] transitionSplitString = scanner.nextLine().split(":");

            String[] statesSplitString = transitionSplitString[0].split("->");
            //Read in CurrentState
            int fromState = Integer.parseInt(statesSplitString[0].trim());
            //Error checking
            if (fromState - 1 < 0 || fromState - 1 > states)
                throw new ParseException(textFile, line, fromState + " out of bounds");
            State from = stateGraph[fromState - 1];

            //Read in NextState
            int toState = Integer.parseInt(statesSplitString[1].trim());
            //Error checking
            if (toState - 1 < 0 || toState - 1 > states)
                throw new ParseException(textFile, line, toState + " out of bounds");
            State to = stateGraph[toState - 1];


            String[] transitionString = transitionSplitString[1].split("\\|");
            if (transitionString.length != numberOfTapes)
                throw new ParseException(textFile, line, "Number of instructions must match tape size");

            for (int i = 0; i < transitionString.length; i++) {
                String[] characters = transitionString[i].trim().split(" ");

                //Read in ReadLetter
                read[i] = characters[0].charAt(0);
                //Error checking
                if (!tapeAlphabetSet.contains(read[i]))
                    throw new ParseException(textFile, line, "Invalid character '" + read + "' in transition");

                //Read in WrittenLetter
                write[i] = characters[1].charAt(0);
                //Error checking
                if (!tapeAlphabetSet.contains(write[i]))
                    throw new ParseException(textFile, line, "Invalid character '" + write + "' in transition");

                dir[i] = characters[2].charAt(0);
            }

            Transition transition = new Transition(
                    Arrays.copyOf(read, numberOfTapes),
                    Arrays.copyOf(write, numberOfTapes),
                    Arrays.copyOf(dir, numberOfTapes),
                    to
            );
            from.AddInstruction(transition);

            line++;
        }

        scanner.close();
    }

    public State getState(int index) {
        return stateGraph[index];
    }

    public State getInitialState() {
        return stateGraph[initialState];
    }

    public HashSet<Integer> getFinalStates() { return finalStates; }

    static HashSet<Character> stringToCharacterSet(String str) {
        HashSet<Character> set = new HashSet<>();
        //Add each character of str and add it to the set
        str.chars().forEach(e -> set.add((char) e));
        return set;
    }
}
