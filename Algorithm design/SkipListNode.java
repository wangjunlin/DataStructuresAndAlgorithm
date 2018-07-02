/*
 * 跳跃表的节点结构
 * */
public class SkipListNode<T> {
    public int key;
    public T values;
    public SkipListNode<T> up, down, left, right; // 四个方向节点

    public static final int HEAD_KEY = Integer.MIN_VALUE;
    public static final int TAIL_KEY = Integer.MAX_VALUE;

    public SkipListNode(int key, T values) {
        this.key = key;
        this.values = values;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public T getValues() {
        return values;
    }

    public void setValues(T values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "key-values:" + key + "-" + values;
    }
}
