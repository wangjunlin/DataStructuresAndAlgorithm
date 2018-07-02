import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * 不固定层级的跳跃表
 * */
public class SkipList<T> {
    private SkipListNode<T> head, tail;
    private int totalNodes; // 节点总数
    private int listLevel; // 层数
    private Random random; // 随机发生器，用于决定节点是否上升层次
    private static final double PROBABILITY = 0.5; // 节点上升层次的概率

    public SkipList() {
        random = new Random();
        clear();
    }

    /*
     * 清空表
     * */
    public void clear() {
        // 空的跳跃表头尾相连的双向链表
        head = new SkipListNode<>(SkipListNode.HEAD_KEY, null);
        tail = new SkipListNode<>(SkipListNode.TAIL_KEY, null);
        horizontalLink(head, tail);
        listLevel = 0;
        totalNodes = 0;
    }

    public boolean isEmpty() {
        return totalNodes == 0;
    }

    public int size() {
        return totalNodes;
    }

    /*
     * 查找是否该key存在，存在则返回节点，不存在返回null
     * */
    public SkipListNode get(int key) {
        SkipListNode p = findNode(key);
        if (key == p.getKey()) return p;
        else return null;
    }

    /*
     * 添加key-value
     * */
    public void put(int k, T v) {
        // 首先查找是否存在待插入值
        SkipListNode<T> p = findNode(k);
        // 如果存在KEY相同的值，则只更新值
        if (k == p.getKey()) {
            p.values = v;
            return;
        }
        // 新建一个节点赋值，并插入到前值后
        SkipListNode<T> q = new SkipListNode<>(k, v);
        backLink(p, q);
        int currentLevel = 0;//当前层级为0
        // 抛硬币决定
        while (random.nextDouble() < PROBABILITY) {
            // 如果超出了高低，需重新建一个顶层
            if (currentLevel >= listLevel) {
                listLevel++;
                SkipListNode<T> p1 = new SkipListNode<>(SkipListNode.HEAD_KEY, null);
                SkipListNode<T> p2 = new SkipListNode<>(SkipListNode.TAIL_KEY, null);
                horizontalLink(p1, p2);
                verticalLink(p1, head);
                verticalLink(p2, tail);
                head = p1;
                tail = p2;
            }
            // p移动到上层
            while (p.up == null) {
                p = p.left;
            }
            p = p.up;

            SkipListNode<T> e = new SkipListNode<>(k, null); // 只保存Key值
            backLink(p, e);// 将e插到p后面
            verticalLink(e, q);
            q = e;
            currentLevel++;
        }
        totalNodes++;

    }

    /*
     * node1后面插入node2
     * */
    private void backLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node2.left = node1;
        node2.right = node1.right;
        node1.right.left = node2;
        node1.right = node2;
    }

    /*
     * 在最下层，找到key的位置
     * */
    private SkipListNode<T> findNode(int key) {
        SkipListNode<T> p = head;
        while (true) {
            while (p.right.key != SkipListNode.TAIL_KEY && p.right.key <= key) p = p.right;
            if (p.down != null) p = p.down;
            else break;
        }
        return p;
    }

    /*
     * 水平双向链接
     * */
    private void horizontalLink(SkipListNode node1, SkipListNode node2) {
        node1.right = node2;
        node2.left = node1;
    }

    /*
     * 垂直双向链接
     * */
    private void verticalLink(SkipListNode node1, SkipListNode node2) {
        node2.up = node1;
        node1.down = node2;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "table is empty!";
        }
        StringBuilder builder = new StringBuilder();
        SkipListNode<T> p = head;
        while (p.down != null) p = p.down;
        while (p.left != null) p = p.left;
        if (p.right != null) p = p.right;
        while (p.right != null) {
            builder.append(p);
            builder.append("\n");
            p = p.right;
        }
        return builder.toString();
    }
}
