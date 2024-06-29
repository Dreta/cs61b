import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

import deque.LinkedListDeque61B;

public class LinkedListDeque61BTest {
    @Test
    public void testForEach() {
        LinkedListDeque61B<Integer> ad = new LinkedListDeque61B<>();
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
        LinkedListDeque61B<Integer> ad = new LinkedListDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        ad.addLast(5);
        assertThat(ad.toString()).isEqualTo("[1, 2, 3, 4, 5]");
    }

    @Test
    public void testEquals() {
        LinkedListDeque61B<Integer> ad1 = new LinkedListDeque61B<>();
        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);
        ad1.addLast(4);
        ad1.addLast(5);
        LinkedListDeque61B<Integer> ad2 = new LinkedListDeque61B<>();
        ad2.addLast(1);
        ad2.addLast(2);
        ad2.addLast(3);
        ad2.addLast(4);
        ad2.addLast(5);
        assertThat(ad1).isEqualTo(ad2);
    }
}
