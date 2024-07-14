import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;
        BSTNode parent;

        BSTNode(K key, V value, BSTNode left, BSTNode right, BSTNode parent) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private class BSTIterator implements Iterator<K> {
        private Stack<BSTNode> stack = new Stack<>();

        BSTIterator() {
            BSTNode current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            BSTNode current = stack.pop();
            K key = current.key;
            if (current.right != null) {
                current = current.right;
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }
            return key;
        }
    }

    private BSTNode root = null;
    private int size = 0;

	@Override
	public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
		for (K key : this) {
            keySet.add(key);
        }
        return keySet;
	}

	@Override
	public void clear() {
		root = null;
        size = 0;
	}

	@Override
	public Iterator<K> iterator() {
		return new BSTIterator();
	}

	@Override
	public V get(K key) {
        BSTNode node = getHelper(root, key);
		return node == null ? null : node.value;
	}

    private BSTNode getHelper(BSTNode current, K key) {
        if (current == null) {
            return null;
        }
        if (current.key.equals(key)) {
            return current;
        } else if (key.compareTo(current.key) < 0) {
            return getHelper(current.left, key);
        } else {
            return getHelper(current.right, key);
        }
    }

	@Override
	public boolean containsKey(K key) {
		return getHelper(root, key) != null;
	}

	@Override
	public void put(K key, V value) {
        if (root == null) {
            root = new BSTNode(key, value, null, null, null);
            size++;
            return;
        }
		putHelper(root, null, key, value);
	}

    private BSTNode putHelper(BSTNode current, BSTNode previous, K key, V value) {
        if (current == null) {
            size++;
            return new BSTNode(key, value, null, null, previous);
        }
        if (key.compareTo(current.key) < 0) {
            current.left = putHelper(current.left, current, key, value);
        } else if (key.compareTo(current.key) > 0) {
            current.right = putHelper(current.right, current, key, value);
        } else if (key.equals(current.key)) {
            current.value = value;
        }
        return current;
    }

    private void removeHelper(BSTNode node) {
        // Remove in the case of no children
        if (node.left == null && node.right == null) {
            if (node.parent == null) {
                root = null;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
            }
            size--;
            return;
        }

        // Remove in the case of one child
        if (node.left == null || node.right == null) {
            BSTNode oneChild;
            if (node.left == null) {
                oneChild = node.right;
            } else {
                oneChild = node.left;
            }

            if (node.parent == null) {
                root = oneChild;
                oneChild.parent = null;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = oneChild;
                } else {
                    node.parent.right = oneChild;
                }
    
                oneChild.parent = node.parent;
            }
            node.left = null;
            node.right = null;
            size--;
            return;
        }

        // Remove in the case of two children

        // Pick new root
        BSTNode newRoot = node.left;
        while (newRoot.right != null) {
            newRoot = newRoot.right;
        }
        
        // Remove new root from original place
        removeHelper(newRoot); // No need to size--, because this is already done in this step

        // Replace current node with this new root
        node.key = newRoot.key;
        node.value = newRoot.value;
    }

	@Override
	public V remove(K key) {
		BSTNode node = getHelper(root, key);
        if (node == null) {
            return null;
        }
        V value = node.value;
        removeHelper(node);
        return value;
	}

	@Override
	public int size() {
		return size;
	}
}
