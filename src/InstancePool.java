import Exception.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class InstancePool {
    private boolean halted;
    private int numberOfTapes;
    private List<TuringInstance> instances = new LinkedList<>();
    private HashSet<Character> inputAlphabetSet;

    private List<Transition> possibleTransitions = new LinkedList<>();

    public InstancePool(String textFile, TuringMachine machine) throws FileNotFoundException, ParseException {
        File file = new File(textFile);
        Scanner scanner = new Scanner(file);

        scanner.nextLine();

        numberOfTapes = scanner.nextInt();
        scanner.nextLine();
        if (numberOfTapes < 1)
            throw new ParseException(textFile, 1, "Tape length must be larger than 0");

        inputAlphabetSet = stringToCharacterSet(scanner.nextLine());

        scanner.close();

        Tape[] firstInstanceTapes = new Tape[numberOfTapes];
        StringBuilder in = new StringBuilder();
        for (int i = 0; i < numberOfTapes; i++) {
            boolean validInput = false;

            do {
                //Prompt input
                System.out.print("Tape " + (i + 1) + ": ");
                scanner = new Scanner(System.in);

                in.delete(0, in.length());
                in.append(scanner.nextLine());

                validInput = containsAllChars(inputAlphabetSet, stringToCharacterSet(in.toString()));
                if (!validInput)
                    System.err.println("Invalid character in input");
            } while (!validInput);

            //Construct tape object
            firstInstanceTapes[i] = new Tape(in.toString());
        }

        instances.add(new TuringInstance(firstInstanceTapes, machine.getInitialState()));
    }

    /**
     * Brings all TuringInstances to their next states
     * If transition is non-deterministic (i.e. multiple valid transitions out of a state) then InstancePool will create new TuringInstances to run those transitions
     */
    public void step() {
        halted = true;
        /**
         * Phase 1: Iterate through instances, call Find(), generate more instances if needed, assign currentTransition
         * if instance has no possible transitions then it is halted
         */
        List<TuringInstance> newInstances = new LinkedList<>();
        for (TuringInstance instance : instances) {
            possibleTransitions.clear();

            possibleTransitions = instance.Find(possibleTransitions);
            if (possibleTransitions.isEmpty()) {
                instance.setHalted(true);
            } else {
                instance.setCurrentTransition(possibleTransitions.get(0));

                for (int i = 1; i < possibleTransitions.size(); i++) {
                    TuringInstance newInstance = instance.clone();
                    newInstance.setCurrentTransition(possibleTransitions.get(i));
                    newInstances.add(newInstance);
                }
            }

            halted &= instance.isHalted();
        }
        if (halted) return;
        instances.addAll(newInstances);

        /**
         * Phase 2: Iterate through instances, call Run()
         * && halted bool with each instance's halted
         */
        for (TuringInstance instance : instances)
            instance.Run();
    }

    public boolean isHalted() {
        return halted;
    }

    public List<TuringInstance> getInstances() {
        return instances;
    }

    public int getNumberOfTapes() {
        return numberOfTapes;
    }

    static HashSet<Character> stringToCharacterSet(String str) {
        HashSet<Character> set = new HashSet<>();
        //Add each character of str and add it to the set
        str.chars().forEach(e -> set.add((char) e));
        return set;
    }

    static boolean containsAllChars(HashSet container, HashSet containee) {
        return container.containsAll(containee);
    }
}
