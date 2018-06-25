import java.util.NoSuchElementException;

public class ArrayStack {
    private int top = -1; // 栈顶指针，对于空栈为-1
    private int stackSize = 0; // 栈的大小
    private Object[] arrayStack = null;

    public ArrayStack(int size) {
        stackSize = size;
        arrayStack = new Object[size];
    }

    /*
     * 入栈操作:top先+1后置arrayStack[top]=data
     * */
    public void push(Object object) {
        if (top != stackSize - 1) {
            arrayStack[top + 1] = object;
            top++;
        } else throw new ArrayIndexOutOfBoundsException();
    }

    /*
     * 置返回值为arrayStack[top]，再top-1
     * */
    public Object pop() {
        if (!isEmpty()) {
            Object tmp = arrayStack[top];
            top--;
            return tmp;
        } else throw new NoSuchElementException();
    }

    public Object peek(){
        return arrayStack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
