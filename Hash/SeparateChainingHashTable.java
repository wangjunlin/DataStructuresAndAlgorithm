
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.sqrt;

public class SeparateChainingHashTable<Anytype> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    private LinkedList[] theList;
    private int currentSize;

    /*
     *  分离链接法哈希表结构
     * */
    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    /*
     * 构建数组列表，每一个数组的值是一个线性列表
     * */
    public SeparateChainingHashTable(int size) {
        theList = new LinkedList[nextPrime(size)];
        for (int i = 0; i < theList.length; i++) {
            theList[i] = new LinkedList<>();
        }
    }

    public boolean contains(Anytype x) {
        LinkedList whichList = theList[myHash(x)];
        return whichList.contains(x);
    }

    public void insert(Anytype x) {
        List<Anytype> whichList = theList[myHash(x)];
        if (!whichList.contains(x)) {
            whichList.add(x);
            //
            if (++currentSize > theList.length) rehash();
        }
    }

    public void remove(Anytype x) {
        List<Anytype> whichList = theList[myHash(x)];
        if (whichList.contains(x)) {
            whichList.remove(x);
            currentSize--;
        }
    }

    public void makeEmpty() {
        for (LinkedList aTheList : theList) aTheList.clear();
        currentSize = 0;
    }

    /*
     * 散列表函数
     * */
    private int myHash(Anytype x) {
        int hashVal = x.hashCode();
        hashVal %= theList.length;
        if (hashVal < 0) {
            hashVal += theList.length;
        }
        return hashVal;
    }

    /*
     * 寻找下一个素数
     * */
    private static int nextPrime(int tableSize) {
        if (tableSize <= 2) {
            return 2;
        } else {
            while (!isPrime(tableSize)) {
                tableSize++;
            }
            return tableSize;
        }
    }

    /*
     * 判断是否为素数
     * */
    private static boolean isPrime(int tableSize) {
        for (int i = 2; i < sqrt(tableSize); i++) {
            if (tableSize % i == 0) {
                return false;
            }
        }
        return true;
    }

    /*
     * 再散列，扩大表大小
     * */
    private void rehash() {
        List<Anytype>[] oldList = theList;
        // 创建一个大小为原表2倍的新表，取大于此值的素数
        theList = (LinkedList[]) new List[nextPrime(2 * theList.length)];
        for (int j = 0; j < theList.length; j++) theList[j] = new LinkedList<>();
        // 将原表元素放入新表
        currentSize = 0;
        for (int i = 0; i < oldList.length; i++) for (Anytype item : oldList[i]) insert(item);
    }
}
