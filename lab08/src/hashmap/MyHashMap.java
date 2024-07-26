package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Lin
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int initialCapacity;
    private double loadFactor;
    private int size = 0;

    /** Constructors */
    public MyHashMap() { 
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) { 
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    @Override
    public void put(K key, V value) {
        int bucket = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[bucket] == null) {
            buckets[bucket] = createBucket();
        }

        for (Node node : buckets[bucket]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        size++;
        buckets[bucket].add(new Node(key, value));

        if (((double) size / (double) buckets.length) > loadFactor) {
            resize(buckets.length * 2);
        }
    }

    private void resize(int newSize) {
        Collection<Node>[] originalBuckets = buckets;
        buckets = (Collection<Node>[]) new Collection[newSize];
        for (Collection<Node> bucket : originalBuckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    int newBucket = Math.floorMod(node.key.hashCode(), newSize);
                    if (buckets[newBucket] == null) {
                        buckets[newBucket] = createBucket();
                    }
                    buckets[newBucket].add(node);
                }
            }
        }
    }

    @Override
    public V get(K key) {
        int bucket = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[bucket] == null) {
            return null;
        }
        for (Node node : buckets[bucket]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int bucket = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[bucket] == null) {
            return false;
        }
        for (Node node : buckets[bucket]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    keySet.add(node.key);
                }
            }
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        int bucket = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[bucket] == null) {
            return null;
        }
        for (Node node : buckets[bucket]) {
            if (node.key.equals(key)) {
                buckets[bucket].remove(node);
                size--;
                return node.value;
            }
        }
        return null;
    }
}
