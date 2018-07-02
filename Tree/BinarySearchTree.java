/*
 * 二叉查找树
 * */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    private BinaryNode<AnyType> root; // 根节点

    public BinarySearchTree() {
        root = null;
    }

    public void empty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    public AnyType findMin() {
        if (isEmpty()) return null;
        return findMin(root).element;
    }

    public AnyType findMax() {
        if (isEmpty()) return null;
        return findMax(root).element;
    }

    public void insert(AnyType x) {
        root = insert(x, root);
    }

    public void remove(AnyType x) {
        root = remove(x, root);
    }

    public void printTree() {
        if (isEmpty()) System.out.println("Empty tree");
        printTree(root);
    }

    /*
     * 在搜索是否包含某一项时，从根节点开始递归调用，如果找到相应的值返回true
     * */
    private boolean contains(AnyType x, BinaryNode<AnyType> node) {
        // 首先对空树进行判断
        if (node == null) return false;

        int compareResult = x.compareTo(node.element);
        if (compareResult < 0) return contains(x, node.left);
        else if (compareResult > 0) return contains(x, node.right);
        else return true;
    }

    /*
     * 最小值只需要遍历左儿子到终止就是最小元素
     * */
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> node) {
        if (node == null) return null;
        else if (node.left == null) return node;
        return findMin(node.left);
    }

    /*
     * 同findMin，遍历右儿子至终止
     * */
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> node) {
        if (node == null) return null;
        else if (node.right == null) return node;
        return findMax(node.right);
    }

    /*
     * insert操作和contains的操作一样
     * */
    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> node) {
        if (node == null) return new BinaryNode<>(x, null, null); // 如果当前为空树，则新建一棵树

        int compareResult = x.compareTo(node.element);
        if (compareResult < 0) node.left = insert(x, node.left);
        else if (compareResult > 0) node.right = insert(x, node.right);
        return node;
    }

    /*
     * 删除有一下3种情况：
     * 1：如果被删除的节点是一片树叶，那就直接删除
     * 2：如果节点有一个儿子，那么该节点父亲调整链绕过该节点
     * 3：如果节点有两个儿子，策略是用该节点右子树的最小数据代替该节点数据，再递归删除右子树最小的节点
     * */
    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> node) {
        if (node == null) return node;

        int compareResult = x.compareTo(node.element);
        if (compareResult < 0) node.left = remove(x, node.left);
        else if (compareResult > 0) node.right = remove(x, node.right);
        else if (node.left != null && node.right != null) {
            node.element = (AnyType) findMin(node.right).element;
            node.right = remove(node.element, node.right);
        } else { // 只有1个节点情况
            node = (node.left != null) ? node.left : node.right;
        }
        return node;
    }

    private void printTree(BinaryNode<AnyType> node) {
        if (node != null) {
            printTree(node.left);
            System.out.println(node.element);
            printTree(node.right);
        }
    }

    // 二叉树节点构造
    private static class BinaryNode<AnyType> {
        AnyType element; // 节点值
        BinaryNode left; // 左节点
        BinaryNode right;

        BinaryNode(AnyType element) {
            this(element, null, null);
        }

        BinaryNode(AnyType element, BinaryNode left, BinaryNode right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }
}
