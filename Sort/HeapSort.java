import java.util.Arrays;
/*
* （max）堆排序
* */
public class HeapSort {
    public static void main(String args[]) {
        Integer[] sortArray = {2, 4, 12, 7, 14, 3, 6, 1, 5, 10, 13,3};
        heapSort(sortArray);
        System.out.print(Arrays.toString(sortArray));
    }

    private static int leftChild(int i) {
        return 2 * i + 1;
    }

    /*
     *  下滤，将最大值放到数组末尾
     * */
    private static <AnyType extends Comparable<? super AnyType>> void percDown(AnyType[] heap, int position, int arrayLength) {
        int child;
        AnyType tmp;
        for (tmp = heap[position]; leftChild(position) < arrayLength; position = child) {
            child = leftChild(position);
            if (child != arrayLength - 1 && heap[child].compareTo(heap[child + 1]) < 0) {
                child++;
            }
            if (tmp.compareTo(heap[child]) < 0) {
                heap[position] = heap[child];
            } else break;
        }
        heap[position] = tmp;
    }

    private static <AnyType extends Comparable<? super AnyType>> void swapReferences(AnyType[] a, int i, int j) {
        AnyType temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static <AnyType extends Comparable<? super AnyType>> void heapSort(AnyType[] a) {
        // 构建最大堆
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            percDown(a, i, a.length);
        }
        // 执行排序，删除最大值
        for (int i = a.length - 1; i > 0; i--) {
            swapReferences(a, 0, i);
            percDown(a, 0, i);
        }
    }
}
