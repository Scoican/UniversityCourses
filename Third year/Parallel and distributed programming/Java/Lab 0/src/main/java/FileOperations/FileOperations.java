package FileOperations;

public interface FileOperations {
    /**
     * Method that generates random numbers and writes them in a given file
     *
     * @param fileName - File in which the numbers will be writen
     * @param size     - How many numbers to be writen
     * @param min      - Minimum size of number length
     * @param max      - Maximum size of number length
     */
    void fileGenerator(String fileName, Integer size, Integer min, Integer max);

    /**
     * Method that compares two given files and checks if the data inside it is the same
     *
     * @param fileName1 - Given file name
     * @param fileName2 - Given file name
     * @return true    - If the containing data in both files are the same
     * false   - Otherwise
     */
    boolean fileComparator(String fileName1, String fileName2);
}
