public class BSTNode<T> {
    public int nodeKey; // ключ узла
    public T nodeValue; // значение в узле
    public BSTNode<T> parent; // родитель или null для корня
    public BSTNode<T> leftChild; // левый потомок
    public BSTNode<T> rightChild; // правый потомок

    public BSTNode(int key, T val, BSTNode<T> parent) {
        nodeKey = key;
        nodeValue = val;
        this.parent = parent;
        leftChild = null;
        rightChild = null;
    }
}

class BSTFind<T> {
    // null, если в дереве вообще нету узлов
    public BSTNode<T> node;

    // true, если узел найден
    public boolean nodeHasKey;

    // true, если родительскому узлу надо добавить новый левым
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

class Test {
    public static void main(String[] args) {
        BSTNode<Integer> root = new BSTNode<>(8, 0, null);
        BST<Integer> tree = new BST<>(root);

//        for (int i = 1; i < 16; i++) {
//            tree.AddKeyValue(i , i);
//        }

        System.out.println("123");


//        System.out.println(tree.AddKeyValue(, 0));
//        System.out.println(tree.AddKeyValue(2, 0));

//        BSTNode<Integer> testNode = tree.FinMinMax(root, true);

        tree.AddKeyValue(4 , 4);
        tree.AddKeyValue(12 , 4);
        tree.AddKeyValue(2 , 4);
        tree.AddKeyValue(6 , 4);
        tree.AddKeyValue(10 , 4);
        tree.AddKeyValue(14 , 4);
        tree.AddKeyValue(1 , 4);
        tree.AddKeyValue(3 , 4);
        tree.AddKeyValue(5 , 4);
        tree.AddKeyValue(7 , 4);
        tree.AddKeyValue(9 , 4);
        tree.AddKeyValue(11 , 4);
        tree.AddKeyValue(13 , 4);
        tree.AddKeyValue(15 , 4);

        System.out.println(tree.DeleteNodeByKey(12));



//        System.out.println(tree.DeleteNodeByKey(2));


    }

}