public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;
        RBTreeNode<T> parent;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }

        private boolean isLeftOfParent() {
            return parent != null && parent.left == this;
        }

        private boolean isRightOfParent() {
            return parent != null && parent.right == this;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // Assuming color swap is needed, similar to rotateLeft
        boolean tmp = node.isBlack;
        node.isBlack = node.left.isBlack;
        node.left.isBlack = tmp;
    
        RBTreeNode<T> originalSub = node.left;
        node.left = originalSub.right;
        if (originalSub.right != null) {
            originalSub.right.parent = node;
        }
    
        originalSub.right = node;
        originalSub.parent = node.parent;
    
        if (node.parent != null) {
            if (node.isLeftOfParent()) {
                node.parent.left = originalSub;
            } else {
                node.parent.right = originalSub;
            }
        }
    
        node.parent = originalSub;
    
        return originalSub;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // Swap the colors
        boolean tmp = node.isBlack;
        node.isBlack = node.right.isBlack;
        node.right.isBlack = tmp;

        RBTreeNode<T> originalSub = node.right;
        node.right = originalSub.left;
        if (originalSub.left != null) {
            originalSub.left.parent = node;
        }

        originalSub.left = node;
        originalSub.parent = node.parent;

        if (node.parent != null) {
            if (node.isLeftOfParent()) {
                node.parent.left = originalSub;
            } else {
                node.parent.right = originalSub;
            }
        }

        node.parent = originalSub;

        return originalSub;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        RBTreeNode<T> newNode = new RBTreeNode<>(false, item);
        if (node == null) {
            return newNode;
        }

        RBTreeNode<T> currentlyAt = node;
        while (true) {
            if (item.compareTo(currentlyAt.item) < 0) {
                if (currentlyAt.left == null) {
                    newNode.parent = currentlyAt;
                    currentlyAt.left = newNode;
                    break;
                }
                currentlyAt = currentlyAt.left;
            } else if (item.compareTo(currentlyAt.item) > 0) {
                if (currentlyAt.right == null) {
                    newNode.parent = currentlyAt;
                    currentlyAt.right = newNode;
                    break;
                }
                currentlyAt = currentlyAt.right;
            }
        }

        // Go upward the tree to fix any violations
        currentlyAt = newNode;
        RBTreeNode<T> parent = null;
        RBTreeNode<T> grandparent = null;
        while (currentlyAt != null && currentlyAt.parent != null) {
            parent = currentlyAt.parent;
            grandparent = currentlyAt.parent.parent;

            if (isRed(parent.left) && isRed(parent.right)) {
                flipColors(parent);
            }

            if ((parent.left == null || parent.left.isBlack) && isRed(parent.right)) {
                currentlyAt = parent;
                parent = rotateLeft(parent);
                grandparent = parent.parent;
            }

            if (grandparent != null && isRed(grandparent.left) && isRed(grandparent.left.left)) {
                parent = rotateRight(grandparent);
                flipColors(parent);
                grandparent = parent.parent;
            }
            currentlyAt = currentlyAt.parent;
        }

        RBTreeNode<T> newRoot = newNode;
        while (newRoot.parent != null) {
            newRoot = newRoot.parent;
        }
        return newRoot;
    }
}
