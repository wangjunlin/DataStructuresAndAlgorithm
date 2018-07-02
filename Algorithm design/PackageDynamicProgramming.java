/*
 * 动态规划背包问题
 * */
public class PackageDynamicProgramming {

    private int[] weight; // 物品重量
    private int[] values; // 物品价值
    private int capacity; // 背包容量
    int objectNum; // 物品个数

    public PackageDynamicProgramming(int[] weight, int[] values, int capacity) {
        this.weight = weight;
        this.values = values;
        this.capacity = capacity;
        objectNum = values.length;

    }

    /*
     * i / j | 0     1     2     3     4     5     6     7     8     9     10     11     12
     *   0   | 0     0     0     0     0     0     0     0     0     0     0      0      0
     *   1   | 0     0     0     4    ...
     *   2   | 0     x
     *   4   | 0     x
     *   5   | 0
     * */
    public int[][] backpackSolution() {
        // c[i][j]表示前i件物品放入一个j重量的背包可以获得的最大价值
        int[][] c = new  int[objectNum + 1][capacity + 1];
        // 初始化第一列和第一行为0
        for (int i = 0; i < objectNum + 1; i++) c[i][0] = 0;
        for (int i = 0; i < capacity + 1; i++) c[0][i] = 0;

        for (int i = 1; i < objectNum + 1; i++)
            for (int j = 1; j < capacity + 1; j++)
                /* 当物品为i件时重量为j，如果第i件重量w[i-1]小于重量j时，c[i][j]有两种情况:
                 * 1:物品i不放入背包，c[i][j]为c[i-1][j]值
                 * 2:放入背包，背包剩余重量j-w[i-1]，所以c[i][j]为c[i-1][j-w[i-1]]值加上当前物品i价值
                 * */
                if (weight[i - 1] <= j)  // 如果当前物品重量小于j
                    if (c[i - 1][j] < c[i - 1][j - weight[i - 1]] + values[i - 1]) // 放入背包
                        c[i][j] = c[i - 1][j - weight[i - 1]] + values[i - 1];
                    else c[i][j] = c[i - 1][j];  // 如果不放入就是上一个物品的价值
                else c[i][j] = c[i - 1][j];
        return c;
    }

    public void printPack(int[][] c, int r1, int r2) {
        for (int i = 1; i <= r1; i++)
            for (int j = 1; j <= r2; j++) {
                System.out.print(c[i][j] + "\t");
                if (j == r2) System.out.println();
            }
    }

    public static void main(String args[]) {
        int[] weight = {3, 5, 2, 6, 4};
        int[] val = {4, 4, 3, 5, 3};
        int m = 12;
        PackageDynamicProgramming packageDynamicProgramming = new PackageDynamicProgramming(weight, val, m);
        packageDynamicProgramming.printPack(packageDynamicProgramming.backpackSolution(), val.length, m);
    }
}
