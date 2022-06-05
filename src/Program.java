import Exception.*;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.InputMismatchException;

public class Program {
    final static int MAX_ITERATIONS = 10000;

    public static void main(String[] args) {
        String textFile = "input.txt";

        //Read File
        try {
            Instant before = Instant.now();
            TuringMachine machine = new TuringMachine(textFile);
            Instant after = Instant.now();
            System.out.println("Generated turing machine state graph in " + Duration.between(before, after).toMillis() / 10000.0 + "s");

            before = Instant.now();
            InstancePool pool = new InstancePool(textFile, machine);
            if (!pool.isHalted()) PrettyPrint.print(pool);

            int iteration = 0;
            do {
                if (iteration >= MAX_ITERATIONS)
                    throw new TuringException("Max iterations hit");

                pool.step();
                if (!pool.isHalted()) PrettyPrint.print(pool);

                iteration++;
            } while (!pool.isHalted());
            PrettyPrint.printResults(machine, pool);

            after = Instant.now();
            System.out.println("Ran turing machine in " + Duration.between(before, after).toMillis() / 10000.0 + "s");

            PrettyPrint.printStatistics(pool);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
