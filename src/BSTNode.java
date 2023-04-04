import java.io.*;
import java.util.*;

class BSTNode<T> {
    public int NodeKey;
    public T NodeValue;
    public BSTNode<T> Parent;
    public BSTNode<T> LeftChild;
    public BSTNode<T> RightChild;

    public BSTNode(int key, T val, BSTNode<T> parent) {
        NodeKey = key;
        NodeValue = val;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

class BSTFind<T> {
    public BSTNode<T> Node;
    public boolean NodeHasKey;
    public boolean ToLeft;

    public BSTFind() {
        Node = null;
    }
}

class BST<T> {
    BSTNode<T> Root;
    int count;

    public BST(BSTNode<T> node) {
        Root = node;
        count = 1;
    }

    public BSTFind<T> FindNodeByKey(int key) {
        BSTFind<T> BSTF = new BSTFind<>();

        if (Root != null) {
            FindNodeByKeyRec(Root, key, BSTF);
        }

        return BSTF;
    }

    private void FindNodeByKeyRec(BSTNode<T> node, int key, BSTFind<T> BSTF) {
        BSTF.Node = node;

        if (node.NodeKey == key) {
            BSTF.NodeHasKey = true;
            return;
        }

        if (key < node.NodeKey && node.LeftChild == null) {
            BSTF.ToLeft = true;
            return;
        }

        if (key > node.NodeKey && node.RightChild == null) {
            return;
        }

        if (key < node.NodeKey) {
            FindNodeByKeyRec(node.LeftChild, key, BSTF);
        } else {
            FindNodeByKeyRec(node.RightChild, key, BSTF);
        }
    }

    public boolean AddKeyValue(int key, T val) {
        if (Root == null) {
            Root = new BSTNode<>(key, val, null);
            return true;
        }

        BSTFind<T> BSTFNode = FindNodeByKey(key);

        if (BSTFNode.NodeHasKey) {
            return false;
        }

        BSTNode<T> node = new BSTNode<>(key, val, BSTFNode.Node);

        if (BSTFNode.ToLeft) {
            BSTFNode.Node.LeftChild = node;
        } else {
            BSTFNode.Node.RightChild = node;
        }

        count++;

        return true;
    }

    public BSTNode<T> FinMinMax(BSTNode<T> fromNode, boolean findMax) {
        if (fromNode == null) {
            return null;
        }

        if (findMax) {
            return findMax(fromNode);
        }

        return findMin(fromNode);
    }

    private BSTNode<T> findMax(BSTNode<T> fromNode) {
        if (fromNode.RightChild == null) {
            return fromNode;
        }

        return findMax(fromNode.RightChild);
    }

    private BSTNode<T> findMin(BSTNode<T> fromNode) {
        if (fromNode.LeftChild == null) {
            return fromNode;
        }

        return findMin(fromNode.LeftChild);
    }

    public boolean DeleteNodeByKey(int key) {
        BSTFind<T> BSTF = FindNodeByKey(key);
        if (Root == null || !BSTF.NodeHasKey) {
            return false;
        }

        boolean leftNull = BSTF.Node.LeftChild == null;
        boolean rightNull = BSTF.Node.RightChild == null;

        // написать код удаления узла Root
        if (BSTF.Node == Root && leftNull && rightNull) {
            Root = null;
            return true;
        }

        if (leftNull && rightNull) {
            DeleteFirst(BSTF);
        } else if (leftNull || rightNull) {
            DeleteSecond(BSTF, leftNull, rightNull);
        } else {
            DeleteThird(BSTF);
        }

//        DeleteThird(BSTF, parentRightChild);
        count--;

        return true;
    }

    public void DeleteFirst(BSTFind<T> BSTF) {
        boolean parentRightChild = BSTF.Node.Parent.RightChild == BSTF.Node;

        if (parentRightChild) {
            BSTF.Node.Parent.RightChild = null;
        } else {
            BSTF.Node.Parent.LeftChild = null;
        }

        BSTF.Node.Parent = null;
    }

    public void DeleteSecond(BSTFind<T> BSTF, boolean leftNull, boolean rightNull) {
        boolean parentRightChild = BSTF.Node.Parent.RightChild == BSTF.Node;

        if (leftNull) {
            if (parentRightChild) {
                BSTF.Node.Parent.RightChild = BSTF.Node.RightChild;
            } else {
                BSTF.Node.Parent.LeftChild = BSTF.Node.RightChild;
            }

            BSTF.Node.RightChild.Parent = BSTF.Node.Parent;
            BSTF.Node.RightChild = null;
        }

        if (rightNull) {
            if (parentRightChild) {
                BSTF.Node.Parent.RightChild = BSTF.Node.LeftChild;
            } else {
                BSTF.Node.Parent.LeftChild = BSTF.Node.LeftChild;
            }

            BSTF.Node.LeftChild.Parent = BSTF.Node.Parent;
            BSTF.Node.LeftChild = null;
        }

        BSTF.Node.Parent = null;
    }

    public void DeleteThird(BSTFind<T> BSTF) {
        boolean parentRightChild = BSTF.Node.Parent.RightChild == BSTF.Node;

        BSTNode<T> node = DeleteThirdRec(BSTF.Node.RightChild);

        if (parentRightChild) {
            BSTF.Node.Parent.RightChild = node;
        } else {
            BSTF.Node.Parent.LeftChild = node;
        }

        if (BSTF.Node.RightChild.LeftChild != null) {
            node.Parent.LeftChild = null;
            node.LeftChild = BSTF.Node.LeftChild;
            node.RightChild = BSTF.Node.RightChild;
            node.Parent = BSTF.Node.Parent;

            if (node.RightChild != null) {
                node.RightChild.Parent = node;
            }

            if (node.LeftChild != null) {
                node.LeftChild.Parent = node;
            }
        } else {
            node.Parent = BSTF.Node.Parent;

            node.LeftChild = BSTF.Node.LeftChild;
            node.LeftChild.Parent = node;
        }
    }

    public BSTNode<T> DeleteThirdRec(BSTNode<T> Node) {
        if (Node.LeftChild == null) {
            return Node;
        }

        return DeleteThirdRec(Node.LeftChild);
    }

    public int Count() {
        if (Root == null) {
            return 0;
        }

        return count;
    }
}