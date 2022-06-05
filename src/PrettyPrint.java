import java.util.HashSet;
import java.util.List;

public class PrettyPrint {
    private final static int width = 11;
    private final static int halfWidth = width / 2;

    private final static String successText = "Success!";
    private final static String failText = "";

    private static String separator;
    private static String pointer;

    static {
        separator = "-".repeat(width);

        StringBuilder sb = new StringBuilder(" ".repeat(halfWidth));
        sb.append('^');
        sb.append(" ".repeat(width - sb.length()));
        pointer = sb.toString();
    }

    public static void print(InstancePool pool) {
        List<TuringInstance> cachedInstances = pool.getInstances();
        int numberOfTapes = pool.getNumberOfTapes();

        for (TuringInstance instance : cachedInstances)
            System.out.print(separator + ' ');
        System.out.println();

        for (int i = 0; i < numberOfTapes; i++) {
            for (TuringInstance instance : cachedInstances) {
                int index = instance.getTapeIndexAt(i);
                String tapeString = instance.getTapeStringAt(i);

                String leftSpace = " ".repeat(Math.max(0, halfWidth - instance.getTapeIndexAt(i)));

                int leftTrimIndex = Math.max(0, index - halfWidth);
                int rightTrimIndex = Math.min(tapeString.length(), halfWidth + index + 1);
                String truncatedString = tapeString.substring(leftTrimIndex, rightTrimIndex);

                String rightSpace = " ".repeat(Math.max(0, width - (leftSpace.length() + truncatedString.length())));

                System.out.print(leftSpace);
                System.out.print(truncatedString);
                System.out.print(rightSpace + ' ');
            }
            System.out.println();
        }

        for (TuringInstance instance : cachedInstances)
            System.out.print(pointer + ' ');
        System.out.println();

        for (TuringInstance instance : cachedInstances) {
            System.out.print(centeredText("[" + (instance.getCurrentState().getIndex() + 1) + "]"));
            System.out.print(' ');
        }
        System.out.println();
    }

    public static void printResults(TuringMachine machine, InstancePool pool) {
        HashSet<Integer> finalStates = machine.getFinalStates();
        List<TuringInstance> cachedInstances = pool.getInstances();

        for (TuringInstance instance : cachedInstances) {
            if (finalStates.contains(instance.getCurrentState().getIndex()))
                System.out.print(centeredText(successText));
            else
                System.out.print(centeredText(failText));

            System.out.print(' ');
        }

        System.out.println();
    }

    public static void printStatistics(InstancePool pool) {
        List<TuringInstance> cachedInstances = pool.getInstances();
        long successfulInstances = cachedInstances.stream().filter(TuringInstance::isHalted).count();
        long failedInstances = cachedInstances.stream().filter(instance -> !instance.isHalted()).count();

        System.out.println(cachedInstances.size() + " turing instance(s) created");
        System.out.println(successfulInstances + " halted, " + failedInstances + " failed");
    }

    private static StringBuilder centeredTextSB = new StringBuilder();
    public static String centeredText(String text) {
        centeredTextSB.delete(0, centeredTextSB.length());

        String leftSpace = " ".repeat(Math.max(0, halfWidth) - (text.length() / 2));
        String rightSpace = " ".repeat(Math.max(0, width - (leftSpace.length() + text.length())));

        centeredTextSB.append(leftSpace);
        centeredTextSB.append(text);
        centeredTextSB.append(rightSpace);

        return centeredTextSB.toString();
    }
}
