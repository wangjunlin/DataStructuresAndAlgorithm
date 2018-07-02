/*
 * N皇后条件（N*N的棋盘上摆放N个皇后）
 * 1：不在同一行上
 * 2：不在同一列
 * 3：不在同一斜线
 * 4：不在同一反斜线
 * */

public class Queen8 {
    private int count; // 计算结果

    private int[][] chess;
    private int queen = 0;

    public Queen8(int queen) {
        this.queen = queen;
        chess = new int[queen][queen];
        // 初始化棋盘为0
        for (int i = 0; i < queen; i++) for (int j = 0; j < queen; j++) chess[i][j] = 0;
        putQueenAtRow(chess,0);
    }

    /*
     * 决定将皇后放在棋盘的哪一行
     * */
    private void putQueenAtRow(int[][] chess, int row) {
        /*
         * 递归终止条件:当row == N 即8行已经成功摆放8个行后，输出结果，结束递归
         * */
        if (row == queen) {
            count++;
            System.out.println("第 "+ count +" 种解：");
            for (int i = 0; i < queen; i++) {
                for (int j = 0; j < queen; j++) {
                    System.out.print(chess[i][j] + " ");
                }
                System.out.println();
            }
            return;
        }

        int[][] tmpChess = chess.clone();

        /*
         * 对row这一行的每个列尝试放置皇后
         * 检测是否符合规则，如果皇后安全则继续摆放下一个皇后
         * */
        for (int i = 0; i < queen; i++) {
            // 在摆放前，先还原棋盘，相当于走错一步后进行回溯
            for (int j = 0; j < queen; j++) tmpChess[row][j] = 0;
            tmpChess[row][i] = 1;

            // 只有符合条件，才会执行下一层的摆放
            if (isSafety(tmpChess, row, i)) putQueenAtRow(tmpChess, row + 1);
        }
    }

    private boolean isSafety(int[][] chess, int row, int column) {
        int step = 1;
        // 当row为0（即第一行）时，不考虑冲突
        while (row - step >= 0) {
            // 每次同过判断上一行的中上左上右上
            if (chess[row - step][column] == 1) return false;
            if (column - step >= 0 && chess[row - step][column - step] == 1) return false;
            if (column + step < queen && chess[row - step][column + step] == 1) return false;
            step++; // 与前面的层数比较，使之不能在同一列和斜线
        }
        return true;
    }

    public static void main(String args[]){
        Queen8 queen8 = new Queen8(8);
    }
}
