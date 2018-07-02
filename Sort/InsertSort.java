
import java.util.Arrays;

public class InsertSort {
    /*
     * 插入排序在随机情况下还是比简单排序和冒泡排序好，虽然时间复杂度都为O(N^2)
     * */
    public static void main(String args[]) {
        Integer[] array = {2, 4, 12, 7, 14, 3};
        insertSort(array);
        System.out.print(Arrays.toString(array));
    }

    public static <AnyType extends Comparable<? super AnyType>> void insertSort(AnyType[] array) {
        int j;
        for (int i = 1; i < array.length; i++) {
            AnyType tmp = array[i];
            for (j = i; j > 0 && tmp.compareTo(array[j - 1]) < 0; j--) array[j] = array[j - 1];
            array[j] = tmp;
        }
    }
}
