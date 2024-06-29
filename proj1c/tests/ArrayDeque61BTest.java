import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

import deque.ArrayDeque61B;

public class ArrayDeque61BTest {
    @Test
    public void testForEach() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        ad.addLast(5);
        List<Integer> expected = List.of(1, 2, 3, 4, 5);
        List<Integer> actual = new ArrayList<>();
        for (int x : ad) {
            actual.add(x);
        }
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testToString() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        ad.addLast(5);
        assertThat(ad.toString()).isEqualTo("[1, 2, 3, 4, 5]");
    }

    @Test
    public void testEquals() {
        ArrayDeque61B<Integer> ad1 = new ArrayDeque61B<>();
        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);
        ad1.addLast(4);
        ad1.addLast(5);
        ArrayDeque61B<Integer> ad2 = new ArrayDeque61B<>();
        ad2.addLast(1);
        ad2.addLast(2);
        ad2.addLast(3);
        ad2.addLast(4);
        ad2.addLast(5);
        assertThat(ad1).isEqualTo(ad2);
    }
}
