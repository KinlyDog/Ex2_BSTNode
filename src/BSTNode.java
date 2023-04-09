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

    public ArrayList<BSTNode> WideAllNodes() {
        ArrayList<BSTNode> list = new ArrayList<>();

        if (Root == null) {
            return list;
        }

        Queue<BSTNode> queue = new LinkedList<>();
        queue.add(Root);

        while (!queue.isEmpty()) {
            BSTNode node = queue.remove();
            list.add(node);

            if (node.LeftChild != null) {
                queue.add(node.LeftChild);
            }

            if (node.RightChild != null) {
                queue.add(node.RightChild);
            }
        }

        return list;
    }

    public void DeepHeloAllNodesRec(BSTNode<T> node, ArrayList<BSTNode> list) {
        if (node.LeftChild == null && node.RightChild == null) {
            return;
        }

        if (node.LeftChild != null) {
            list.add(node.LeftChild);
        }

        if (node.RightChild != null) {
            list.add(node.RightChild);
        }

        if (node.LeftChild != null) {
            DeepHeloAllNodesRec(node.LeftChild, list);
        }

        if (node.RightChild != null) {
            DeepHeloAllNodesRec(node.RightChild, list);
        }
    }

    private void DeepAllNodesRec(BSTNode<T> node, ArrayList<BSTNode> list) {
        if (node.LeftChild == null && node.RightChild == null) {
            return;
        }

        if (node.LeftChild != null) {
            list.add(node.LeftChild);

            DeepHeloAllNodesRec(node.LeftChild, list);
        }

        if (node.RightChild != null) {
            list.add(node.RightChild);

            DeepHeloAllNodesRec(node.RightChild, list);
        }
    }

    public ArrayList<BSTNode> DeepAllNodes(int i) {
        ArrayList<BSTNode> list = new ArrayList<>();

        if (Root == null) {
            return list;
        }

        if (i == 0) {
            inOrder(Root, list);
        } else if (i == 1) {
            postOrder(Root, list);
        } else {
            preOrder(Root, list);
        }


        return list;
    }

    private void inOrder(BSTNode<T> node, ArrayList<BSTNode> list) {
        if (node.LeftChild != null) {
            list.add(node.LeftChild);

            DeepAllNodesRec(node.LeftChild, list);
        }

        list.add(node);

        if (node.RightChild != null) {
            list.add(node.RightChild);

            DeepAllNodesRec(node.RightChild, list);
        }
    }

    private void postOrder(BSTNode<T> node, ArrayList<BSTNode> list) {
        if (node.LeftChild != null) {
            list.add(node.LeftChild);

            DeepAllNodesRec(node.LeftChild, list);
        }

        if (node.RightChild != null) {
            list.add(node.RightChild);

            DeepAllNodesRec(node.RightChild, list);
        }

        list.add(node);
    }

    private void preOrder(BSTNode<T> node, ArrayList<BSTNode> list) {
        list.add(node);

        if (node.LeftChild != null) {
            list.add(node.LeftChild);

            DeepAllNodesRec(node.LeftChild, list);
        }

        if (node.RightChild != null) {
            list.add(node.RightChild);

            DeepAllNodesRec(node.RightChild, list);
        }
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

        count--;

        BSTNode<T> node = BSTF.Node;

        boolean leftNull = node.LeftChild == null;
        boolean rightNull = node.RightChild == null;

        if (leftNull && rightNull) {
            return nodeExchange(node, null);
        }

        if (!leftNull && !rightNull) {
            return deleteIfNotNull(node);
        }

        return deleteIfOneNull(node, leftNull);
    }

    private boolean nodeExchange(BSTNode<T> node, BSTNode<T> nodeExchange) {
        if (node.Parent.RightChild == node) {
            node.Parent.RightChild = nodeExchange;

        } else {
            node.Parent.LeftChild = nodeExchange;
        }

        return true;
    }

    private boolean deleteIfNotNull(BSTNode<T> node) {
        BSTNode<T> lastNode = deleteIfNotNullRec(node.RightChild);

        nodeExchange(node, lastNode);

        lastNode.LeftChild = node.LeftChild;
        lastNode.LeftChild.Parent = lastNode;

        lastNode.Parent.LeftChild = null;
        lastNode.Parent = node.Parent;

        if (lastNode != node.RightChild) {
            lastNode.RightChild = node.RightChild;
            lastNode.RightChild.Parent = lastNode;
        }

        return true;
    }

    private BSTNode<T> deleteIfNotNullRec(BSTNode<T> Node) {
        if (Node.LeftChild == null) {
            return Node;
        }

        return deleteIfNotNullRec(Node.LeftChild);
    }

    private boolean deleteIfOneNull(BSTNode<T> node, boolean leftNull) {
        if (leftNull) {
            nodeExchange(node, node.RightChild);
            node.RightChild.Parent = node.Parent;

        } else {
            nodeExchange(node, node.LeftChild);
            node.LeftChild.Parent = node.Parent;
        }

        return true;
    }

    public int Count() {
        if (Root == null) {
            return 0;
        }

        return count;
    }
}

