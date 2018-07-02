import java.util.Arrays;

public class QuickSort {
    public static void main(String args[]) {
        Double[] array = new Double[100];//= {2, 4, 12, 7, 14, 3, 6, 5, 10, 13, 7, 42, 14};
        for (int i = 0; i < 100; i++) array[i] = 100 * Math.random();
        quickSort(array);
        System.out.print(Arrays.toString(array));
    }

    private static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType[] a) {
        quickSort(a, 0, a.length - 1);
    }

    /*
     * 1：得到中值
     * 2：从left+1 right-2 开始移动指针比较，如果i<j且符合条件，则交换元素位置，否则终止循环
     * 3：交换i与枢纽元的位置
     * 4：递归进行排序，区间为[left,i-1] [i+1,right]
     * */
    private static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType[] a, int left, int right) {
        if (left < right) {
            AnyType pivot = median3(a, left, right);

            // begin partitioning
            // 因为a[left]已知比枢纽元小，初始化i,j从left+1和right-2，防止i,j超出范围
            int i = left + 1, j = right - 2;
            for (; ; ) {
                // i右移小于枢纽元的位置
                while (a[i].compareTo(pivot) < 0) i++;
                while (j > left && a[j].compareTo(pivot) > 0) j--;
                // 当i<j时，切i值大于pivot，j值小于pivot，交换i,j值
                if (i < j) {
                    swapReferences(a, i, j);
                } else {
                    break;
                }
            }
            // 从新把枢纽元与i当前大于枢纽元的值互换
            if (i < right) {
                swapReferences(a, i, right - 1);
            }
            // 对2个区间进行排序，重复以上步骤
            quickSort(a, left, i - 1);
            quickSort(a, i + 1, right);
        }
    }

    // 执行三数中值分割法
    // 返回值是枢纽元
    private static <AnyType extends Comparable<? super AnyType>> AnyType median3(AnyType[] a, int left, int right) {
        int center = (left + right) / 2;
        // 比枢纽元值小的放到左边
        if (a[center].compareTo(a[left]) < 0) swapReferences(a, left, center);
        if (a[right].compareTo(a[left]) < 0) swapReferences(a, left, right);
        if (a[right].compareTo(a[center]) < 0) swapReferences(a, center, right);
        swapReferences(a, center, right - 1); // 将枢纽元放在数组倒数第二
        return a[right - 1];
    }

    private static <AnyType extends Comparable<? super AnyType>> void swapReferences(AnyType[] a, int i, int j) {
        AnyType temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
