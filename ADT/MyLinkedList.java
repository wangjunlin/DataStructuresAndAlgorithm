import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * MyLinkedList实现的泛型类，将作为双联表来实现
 * */
public class MyLinkedList<AnyType> implements Iterable {
    private int mSize; // 链表大小
    private int mCount; // 从构造以来对链表所做改变的次数，每次对add remove的调用都更新该值
    /*
     * 两端的链
     * */
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;

    /*
     * MyLinkedList类的嵌套Node类
     * 是一个私有嵌套累，一个节点包含数据和前后节点的链
     * */
    private static class Node<AnyType> {
        public AnyType data;
        public Node<AnyType> previous;
        public Node<AnyType> next;

        public Node(AnyType data, Node<AnyType> previous, Node<AnyType> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }

    public MyLinkedList() {
        doClear();
    }

    public void clear() {
        doClear();
    }

    /*
     * 当清空链表时，两个空节点相连
     * */
    private void doClear() {
        beginMarker = new Node<>(null, null, null);
        endMarker = new Node<>(null, beginMarker, null);
        beginMarker.next = endMarker;
    }

    public int size() {
        return mSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(AnyType data) {
        add(size(), data);
        return true;
    }

    public void add(int index, AnyType data) {
        addBefore(getNode(index, 0, size()), data);
    }

    public AnyType get(int index) {
        return getNode(index).data;
    }

    public AnyType set(int index, AnyType newValue) {
        Node<AnyType> target = getNode(index);
        AnyType oldValue = target.data;
        target.data = newValue;
        return oldValue;
    }

    public AnyType remove(int index) {
        return remove(getNode(index));
    }

    /*
     * 如果target引用正在倍删除的节点，该节点被断开2个链接
     * 1. target.prev.next = target.next
     * 2. target.next.prev = target.prev
     * */
    private AnyType remove(Node<AnyType> target) {
        target.next.previous = target.previous; // 目标节点的下一个节点的prev指向目标节点的前一个节点
        target.previous.next = target.next; // 目标节点的前一个节点的next指向目标节点的下一个节点
        mSize--;
        mCount++;
        return target.data;
    }

    /*
     * 获取指定位置的节点，范围在0~size()-1间
     * */
    private Node<AnyType> getNode(int index) {
        return getNode(index, 0, size() - 1);
    }

    /*
     * 获取指定位置节点，如果索引在前半部分，以向后的方向遍历，否则从后向前
     * */
    private Node<AnyType> getNode(int index, int lower, int upper) {
        Node<AnyType> target;

        if (index < lower || index > upper) throw new IndexOutOfBoundsException();

        if (index < size() / 2) {
            target = beginMarker.next;
            for (int i = 0; i < index; i++) target = target.next;
        } else {
            target = endMarker;
            for (int i = size(); i > index; i--) target = target.previous;
        }
        return target;
    }

    /*
     * 添加节点至指定位置前面
     * 1.新建新节点，并把新节点的previous指向指定节点的前一个节点，next指向指定节点
     * 2.指定节点的原previous节点的next指向新节点
     * 3.指定节点的previous指向新节点
     * 4.链表大小+1
     * */
    private void addBefore(Node<AnyType> target, AnyType data) {
        Node<AnyType> newNode = new Node<>(data, target.previous, target);
        target.previous.next = newNode;
        target.previous = newNode;
        mSize++;
        mCount++;
    }

    @Override
    public Iterator iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<AnyType> {
        private Node<AnyType> current = beginMarker.next; // 保存一个当前位置
        private int exceptedModCount = mCount; // 存储迭代器被构造时的链表的mCount，检测迭代期间集合被修改的情况
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            return current != endMarker;
        }

        @Override
        public AnyType next() {
            if (mCount != exceptedModCount) throw new ConcurrentModificationException();
            if (!hasNext()) throw new NoSuchElementException();
            AnyType nextItem = current.data;
            okToRemove = true;
            return nextItem;
        }

        @Override
        public void remove() {
            if (mCount != exceptedModCount) throw new ConcurrentModificationException();
            if (!okToRemove) throw new IllegalStateException();
            MyLinkedList.this.remove(current.previous);
            exceptedModCount++;
            okToRemove = false;
        }
    }
}
