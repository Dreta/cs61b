import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void testNaturalOrder() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        mad.addFirst(1);
        mad.addFirst(2);
        mad.addFirst(3);
        assertThat(mad.max()).isEqualTo(3);
    }

    private static class ReverseIntegerComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return b - a;
        }
    }

    @Test
    public void testReverseOrder() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(new ReverseIntegerComparator());
        mad.addFirst(1);
        mad.addFirst(2);
        mad.addFirst(3);
        assertThat(mad.max()).isEqualTo(1);
    }
}
