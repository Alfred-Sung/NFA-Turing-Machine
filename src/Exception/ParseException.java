package Exception;

/**
 * Custom exception thrown when parsing information from the file
 */
public class ParseException extends Exception {
    /***
     * Constructs a new ParseException
     * @param textFile File name being parsed
     * @param line Which line the error occurred
     * @param message Error message to display
     */
    public ParseException(String textFile, int line, String message) {
        super(
                textFile + ": Line " + line + "\n" +
                        message
        );
    }
}
