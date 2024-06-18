import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node<T> sentinel;
    private int size;

    private static class Node<T> {
        public T item;
        public Node<T> prev;
        public Node<T> next;

        public Node(T i, Node<T> p, Node<T> n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new Node<>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T x) {
        sentinel.next = new Node<>(x, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }

    public void addLast(T x) {
        sentinel.prev.next = new Node<>(x, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
        size++;
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node<T> p = sentinel.next;
        while (p != sentinel) {
            list.add(p.item);
            p = p.next;
        }
        return list;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        Node<T> originalFirst = sentinel.next;
        if (originalFirst == sentinel) {
            return null;
        }
        sentinel.next = originalFirst.next;
        originalFirst.prev = null;
        originalFirst.next = null;
        sentinel.next.prev = sentinel;
        size--;
        return originalFirst.item;
    }

    public T removeLast() {
        Node<T> originalLast = sentinel.prev;
        if (originalLast == sentinel) {
            return null;
        }
        sentinel.prev = originalLast.prev;
        originalLast.prev = null;
        originalLast.next = null;
        sentinel.prev.next = sentinel;
        size--;
        return originalLast.item;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node<T> p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursive(index, sentinel.next).item;
    }

    public Node<T> getRecursive(int index, Node<T> p) {
        if (index == 0) {
            return p;
        }
        return getRecursive(index - 1, p.next);
    }
}
