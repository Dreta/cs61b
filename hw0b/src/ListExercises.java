import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        return L.stream().mapToInt(Integer::intValue).sum();
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> result = new ArrayList<>();
        for (int i : L) {
            if (i % 2 == 0) {
                result.add(i);
            }
        }
        return result;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> result = new ArrayList<>();
        for (int i : L1) {
            if (L2.contains(i)) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Returns the number of occurrences of the given character in a list of
     * strings.
     */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int result = 0;
        for (String s : words) {
            if (s.indexOf(c) != -1) {
                result++;
            }
        }
        return result;
    }
}
