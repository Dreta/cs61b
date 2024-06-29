package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] array;
    private int front = 0;
    private int size = 0;

    public ArrayDeque61B() {
        array = (T[]) new Object[8];
    }

    private void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        array = newArray;
        front = 0;
    }

    @Override
    public void addFirst(T x) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        front = Math.floorMod(front - 1, array.length);
        array[front] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[Math.floorMod(front + size, array.length)] = x;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(get(i));
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        T x = array[front];
        front = Math.floorMod(front + 1, array.length);
        size--;
        if (size < array.length / 2) {
            resize(array.length / 2);
        }
        return x;
    }

    @Override
    public T removeLast() {
        T x = array[Math.floorMod(front + size - 1, array.length)];
        size--;
        if (size < array.length / 2) {
            resize(array.length / 2);
        }
        return x;
    }

    @Override
    public T get(int index) {
        return array[Math.floorMod(front + index, array.length)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("Unimplemented method 'getRecursive'");
    }

    private class ArrayDeque61BIterator implements Iterator<T> {
        private int index = -1;

        @Override
        public boolean hasNext() {
            return index + 1 < size;
        }

        @Override
        public T next() {
            index++;
            return get(index);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayDeque61B<?> other) {
            if (other.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!get(i).equals(other.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return toList().toString();
    }
}
