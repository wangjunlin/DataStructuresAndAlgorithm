import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * 实现一个独立的ArrayList泛型类，不提供MyCollection和MyList
 * */
public class MyArrayList<AnyType> implements Iterable<AnyType> {

    private static final int DEFAULT_CAPACITY = 10; // 定义初始数组容量

    private int mSize; // 定义数组大小变量
    private AnyType[] mItems = (AnyType[]) new Object[DEFAULT_CAPACITY]; // 数组

    public MyArrayList() {
        doClear();
    }

    public void clear() {
        doClear();
    }

    // 执行清空表操作，并将表容量初始化
    private void doClear() {
        mSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public int size() {
        return mSize;
    }

    public boolean isEmpty() {
        return mSize == 0;
    }

    // 将表容量调整至当前数组尺寸大小
    public void trimToSize() {
        ensureCapacity(size());
    }

    public AnyType get(int index) {
        if (index < 0 || index >= size()) throw new ArrayIndexOutOfBoundsException();
        return mItems[index];
    }

    public AnyType set(int index, AnyType newValue) {
        if (index < 0 || index >= size()) throw new ArrayIndexOutOfBoundsException();
        AnyType oldValue = mItems[index];
        mItems[index] = newValue;
        return oldValue;
    }

    // 确定数组容量
    public void ensureCapacity(int newCapacity) {
        if (newCapacity < mSize) return;
        AnyType[] old = mItems;
        mItems = (AnyType[]) new Object[newCapacity]; // 创建一个新的尺寸的数组
        // 遍历原有数组元素，加入新的数组中
        System.arraycopy(old, 0, mItems, 0, size());
    }

    // 添加元素，未指定位置时，默认添加至数组末尾
    public boolean add(AnyType x) {
        add(size(), x);
        return true;
    }

    public void add(int index, AnyType x) {
        // 当现有数组大小等于当前表大小，则做一次数组容量扩充
        if (mItems.length == size()) ensureCapacity(size() * 2 + 1);
        System.arraycopy(mItems, index, mItems, index + 1, mSize - index);
        mItems[index] = x;
        mSize++;
    }

    public AnyType remove(int index) {
        AnyType removedItem = mItems[index];
        // 大于删除位置的元素前移一位
        System.arraycopy(mItems, index + 1, mItems, index, size() - 1 - index);
        mSize--;
        return removedItem;
    }

    @Override
    public Iterator<AnyType> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<AnyType> {
        private int current = 0;

        public boolean hasNext() {
            return current < size();
        }

        public AnyType next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return mItems[current++];
        }

        public void remove() {
            MyArrayList.this.remove(--current);
        }
    }
}
