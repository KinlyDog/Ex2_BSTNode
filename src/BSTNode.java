public class BSTNode<T> {
    public int nodeKey;
    public T nodeValue;
    public BSTNode<T> parent;
    public BSTNode<T> leftChild;
    public BSTNode<T> rightChild;

    public BSTNode(int key, T val, BSTNode<T> parent) {
        nodeKey = key;
        nodeValue = val;
        this.parent = parent;
        leftChild = null;
        rightChild = null;
    }
}

class BSTFind<T> {
    public BSTNode<T> node;
    public boolean nodeHasKey;
    public boolean toLeft;

    public BSTFind() {
        node = null;
    }
}

class BST<T> {
    BSTNode<T> root;
    int count;

    public BST(BSTNode<T> node) {
        root = node;
        count = 1;
    }

    public BSTFind<T> FindNodeByKey(int key) {
        BSTFind<T> BSTF = new BSTFind<>();

        if (root == null) {
            return BSTF;
        }

        FindNodeByKeyRec(root, key, BSTF);

        return BSTF;
    }

    private void FindNodeByKeyRec(BSTNode<T> node, int key, BSTFind<T> BSTF) {
        BSTF.node = node;

        if (node.nodeKey == key) {
            BSTF.nodeHasKey = true;
            return;
        }

        if (key < node.nodeKey && node.leftChild == null) {
            BSTF.toLeft = true;
            return;
        }

        if (key > node.nodeKey && node.rightChild == null) {
            return;
        }

        if (key < node.nodeKey) {
            FindNodeByKeyRec(node.leftChild, key, BSTF);
        } else {
            FindNodeByKeyRec(node.rightChild, key, BSTF);
        }
    }

    public boolean AddKeyValue(int key, T val) {
        BSTFind<T> BSTFNode = FindNodeByKey(key);

        if (BSTFNode.nodeHasKey) {
            return false;
        }

        BSTNode<T> node = new BSTNode<>(key, val, BSTFNode.node);

        if (BSTFNode.toLeft) {
            BSTFNode.node.leftChild = node;
        } else {
            BSTFNode.node.rightChild = node;
        }

        count++;

        return true;
    }

    public BSTNode<T> FinMinMax(BSTNode<T> fromNode, boolean findMax) {
        if (findMax) {
            return findMax(fromNode);
        }

        return findMin(fromNode);
    }

    private BSTNode<T> findMax(BSTNode<T> fromNode) {
        if (fromNode.rightChild == null) {
            return fromNode;
        }

        return findMax(fromNode.rightChild);
    }

    private BSTNode<T> findMin(BSTNode<T> fromNode) {
        if (fromNode.leftChild == null) {
            return fromNode;
        }

        return findMin(fromNode.leftChild);
    }

    public boolean DeleteNodeByKey(int key) {
        BSTFind<T> BSTF = FindNodeByKey(key);
        if (!BSTF.nodeHasKey) {
            return false;
        }

        boolean parentRightChild = BSTF.node.parent.rightChild == BSTF.node;
        boolean leftNull = BSTF.node.leftChild == null;
        boolean rightNull = BSTF.node.rightChild == null;

        if (leftNull && rightNull) {
            DeleteFirst(BSTF, parentRightChild);
        } else if (leftNull || rightNull) {
            DeleteSecond(BSTF, parentRightChild, leftNull, rightNull);
        } else if (!leftNull && !rightNull) {
            DeleteThird(BSTF, parentRightChild);
        }

        DeleteThird(BSTF, parentRightChild);
        count--;

        return true;
    }

    public void DeleteFirst(BSTFind<T> BSTF, boolean parentRightChild) {
        if (parentRightChild) {
            BSTF.node.parent.rightChild = null;
        } else {
            BSTF.node.parent.leftChild = null;
        }

        BSTF.node.parent = null;
    }

    public void DeleteSecond(BSTFind<T> BSTF, boolean parentRightChild, boolean leftNull, boolean rightNull) {
        if (leftNull) {
            if (parentRightChild) {
                BSTF.node.parent.rightChild = BSTF.node.rightChild;
            } else {
                BSTF.node.parent.leftChild = BSTF.node.rightChild;
            }

            BSTF.node.rightChild.parent = BSTF.node.parent;
            BSTF.node.rightChild = null;
        }

        if (rightNull) {
            if (parentRightChild) {
                BSTF.node.parent.rightChild = BSTF.node.leftChild;
            } else {
                BSTF.node.parent.leftChild = BSTF.node.leftChild;
            }

            BSTF.node.leftChild.parent = BSTF.node.parent;
            BSTF.node.leftChild = null;
        }

        BSTF.node.parent = null;
    }

    public void DeleteThird(BSTFind<T> BSTF, boolean parentRightChild) {
        BSTNode<T> node = DeleteThirdRec(BSTF.node.rightChild);

        if (parentRightChild) {
            BSTF.node.parent.rightChild = node;
        } else {
            BSTF.node.parent.leftChild = node;
        }

        node.parent.leftChild = null;
        node.leftChild = BSTF.node.leftChild;
        node.rightChild = BSTF.node.rightChild;
        node.parent = BSTF.node.parent;
        node.rightChild.parent = node;
        node.leftChild.parent = node;
    }

    public BSTNode<T> DeleteThirdRec(BSTNode<T> node) {
        if (node.leftChild == null) {
            return node;
        }

        return DeleteThirdRec(node.leftChild);
    }

    public int Count() {
        if (root == null) {
            return 0;
        }

        return count;
    }
}
