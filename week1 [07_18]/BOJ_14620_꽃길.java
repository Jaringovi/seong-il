import java.util.Scanner;

public class BOJ_14620_꽃길 {
    static int n;
    static int[][] whole;
    static int[][] visit;
    static int min_cost = 4000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        whole = new int[n][n];
        visit = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                whole[i][j] = scanner.nextInt();
            }
        }

        down(0, 0, 0, 0, new int[3][2]);
        System.out.println(min_cost);
    }

    static boolean flag_check(int row, int col) {
        int[][] items = { {row, col}, {row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1} };
        for (int[] k : items) {
            int krow = k[0];
            int kcol = k[1];
            if (visit[krow][kcol] == 1) {
                return false;
            }
        }
        return true;
    }

    static void flag_do(int row, int col) {
        int[][] items = { {row, col}, {row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1} };
        for (int[] k : items) {
            int krow = k[0];
            int kcol = k[1];
            visit[krow][kcol] = 1;
        }
    }

    static void flag_undo(int row, int col) {
        int[][] items = { {row, col}, {row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1} };
        for (int[] k : items) {
            int krow = k[0];
            int kcol = k[1];
            visit[krow][kcol] = 0;
        }
    }

    static int flag_sum(int row, int col) {
        int[][] items = { {row, col}, {row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1} };
        int sum = 0;
        for (int[] k : items) {
            int krow = k[0];
            int kcol = k[1];
            sum += whole[krow][kcol];
        }
        return sum;
    }

    static void down(int row, int col, int depth, int cur_sum, int[][] flowers) {
        for (row = 1; row < n - 1; row++) {
            for (col = 1; col < n - 1; col++) {
                if (flag_check(row, col)) {
                    // do
                    flag_do(row, col);
                    flowers[depth][0] = row;
                    flowers[depth][1] = col;
                    cur_sum += flag_sum(row, col);

                    // down
                    if (cur_sum < min_cost) {
                        if (depth == 2) {
                            min_cost = cur_sum;
                            flag_undo(row, col);
                            return;
                        }
                        down(n, n, depth + 1, cur_sum, flowers);
                    }

                    // undo
                    flag_undo(row, col);
                    flowers[depth][0] = 0;
                    flowers[depth][1] = 0;
                    cur_sum -= flag_sum(row, col);
                }
            }
        }
    }
}
