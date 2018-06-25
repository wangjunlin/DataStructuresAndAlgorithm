public class CircleArrayQueue {

    private Object[] queue;
    private int front; // 队首引用，若队列不为空，指向队首元素
    private int back; // 队尾

    public CircleArrayQueue(int size) {
        front = back = 0;
        queue = new Object[size];
    }

    public void clear() {
        front = back = 0;
    }

    public Object peek() {
        if (front == back) return null;
        return queue[front];
    }

    public boolean isEmpty() {
        return front == back;
    }

    public int length() {
        return (back - front + queue.length) % queue.length;
    }

    public void enqueue(Object x) throws Exception {
        // 通过队尾指针+1对数组长度求余判断是否满队
        if ((back + 1) % queue.length == front) throw new Exception("队列满");
        queue[back] = x;
        back = (back + 1) % queue.length;
    }

    public Object dequeue() {
        if (front == back) return null;
        Object y = queue[front];
        front = (front + 1) % queue.length;
        return y;
    }
}
